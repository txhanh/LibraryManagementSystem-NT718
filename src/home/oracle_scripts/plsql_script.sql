--PL/SQL

--TRIGGER
create trigger TRG_SO_SACH_MUON_TOI_DA
    before insert
    on PHIEUMUONSACH
    for each row
DECLARE
    SO_SACH_DA_MUON NUMBER;
BEGIN
    SELECT COUNT(*)
    INTO SO_SACH_DA_MUON
    FROM PHIEUMUONSACH
    WHERE PHIEUMUONSACH.MADOCGIA = :NEW.MADOCGIA;
    IF (SO_SACH_DA_MUON >= 3) THEN
        RAISE_APPLICATION_ERROR(-20000, 'Một độc giả chỉ được mượn tối đa 3 cuốn sách');

    END IF;
END;
/

--PROCEDURE
--Thêm cuốn sách
CREATE OR REPLACE PROCEDURE PROC_THEMCUONSACH(p_tentuasach IN varchar2,
                                              p_trangthai IN varchar2)
AS
    v_matuasach TUASACH.MATUASACH%TYPE;
    v_count     number;
BEGIN
    SELECT MATUASACH
    into v_matuasach
    FROM TUASACH
    WHERE TENTUASACH = p_tentuasach;

    INSERT INTO CUONSACH(MATUASACH, TRANGTHAI)
    VALUES (v_matuasach, p_trangthai);

    SELECT SOLUONG
    INTO v_count
    FROM TUASACH
    WHERE MATUASACH = v_matuasach;

    UPDATE TUASACH
    SET SOLUONG = v_count + 1
    WHERE MATUASACH = v_matuasach;

    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_STACK());
        DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE());
        ROLLBACK;
        RAISE;
END;
/

--Thêm phiếu mượn sách
create OR REPLACE PROCEDURE PROC_THEMPHIEUMUONSACH(p_madocgia IN number,
                                                   p_macuonsach IN number,
                                                   p_ngaymuonsach IN DATE,
                                                   p_ngaydukientra IN DATE,
                                                   p_trangthaipms IN varchar2)
AS
BEGIN
    INSERT INTO PHIEUMUONSACH(MADOCGIA, MACUONSACH, NGAYMUONSACH, NGAYDUKIENTRA, TRANGTHAIPMS)
    VALUES (p_madocgia, p_macuonsach, p_ngaymuonsach, p_ngaydukientra, p_trangthaipms);

    UPDATE CUONSACH
    SET TRANGTHAI = 'Đã mượn'
    WHERE MACUONSACH = p_macuonsach;

    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_STACK());
        DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE());
        ROLLBACK;
        RAISE;
END;
/

-- Thêm phiếu trả sách
create OR REPLACE PROCEDURE PROC_THEMPHIEUTRASACH(p_maphieumuon IN number,
                                                  p_madocgia IN number,
                                                  p_macuonsach IN number,
                                                  p_ngaytrasach IN DATE,
                                                  p_songaymuon IN number,
                                                  p_songaytratre IN number,
                                                  p_tienphat IN number)
AS
BEGIN
    INSERT INTO PHIEUTRASACH(MAPHIEUMUON, MADOCGIA, MACUONSACH, NGAYTRASACH, SONGAYMUON,
                             SONGAYTRATRE, TIENPHAT)
    VALUES (p_maphieumuon, p_madocgia, p_macuonsach, p_ngaytrasach, p_songaymuon, p_songaytratre, p_tienphat);

    UPDATE PHIEUMUONSACH
    SET TRANGTHAIPMS = 'Đã trả'
    WHERE MAPHIEUMUON = p_maphieumuon;

    UPDATE CUONSACH
    SET TRANGTHAI = 'Chưa mượn'
    WHERE MACUONSACH = p_macuonsach;

    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_STACK());
        DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE());
        ROLLBACK;
        RAISE;
end;
/

--Xóa cuốn sách
create OR REPLACE PROCEDURE PROC_XOACUONSACH(
    p_MACUONSACH IN number
)
AS
    v_matuasach TUASACH.MATUASACH%TYPE;
    v_count     number;
BEGIN
    SELECT MATUASACH
    INTO v_matuasach
    FROM CUONSACH
    WHERE MACUONSACH = p_MACUONSACH;

    DELETE FROM CUONSACH WHERE MACUONSACH = p_MACUONSACH;

    SELECT SOLUONG
    INTO v_count
    FROM TUASACH
    WHERE MATUASACH = v_matuasach;

    UPDATE TUASACH
    SET SOLUONG = v_count - 1
    WHERE MATUASACH = v_matuasach;

    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_STACK());
        DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE());
        ROLLBACK;
        RAISE;
END;
/

--Update Họ tên độc giả
create OR REPLACE PROCEDURE UPDATE_HOTENDOCGIA(p_madocgia IN docgia.madocgia%TYPE,
                                               p_old_checksum IN NUMBER,
                                               p_new_hodocgia IN docgia.HODOCGIA%TYPE,
                                               p_new_tendocgia IN docgia.TENDOCGIA%TYPE) AS
    v_rowid        ROWID;
    v_new_checksum NUMBER;
BEGIN
    SELECT rowid
    INTO v_rowid
    FROM DOCGIA
    WHERE MADOCGIA = p_madocgia
        FOR UPDATE;

    v_new_checksum := owa_opt_lock.checksum('qltv0804', 'docgia', v_rowid);

    IF v_new_checksum = p_old_checksum THEN
        UPDATE DOCGIA
        SET HODOCGIA  = p_new_hodocgia,
            TENDOCGIA = p_new_tendocgia
        WHERE rowid = v_rowid;
        -- COMMIT;
    ELSE
        ROLLBACK;
        RAISE_APPLICATION_ERROR(-20000, 'Dòng này vừa được cập nhật, kiểm tra lại để tránh Lost Update');
    END IF;
END;
/

--Update tên tựa sách
create OR REPLACE PROCEDURE UPDATE_TENTUASACH(p_matuasach IN tuasach.matuasach%TYPE,
                                              p_old_checksum IN NUMBER,
                                              p_new_tentuasach IN tuasach.tentuasach%TYPE) AS
    v_rowid        ROWID;
    v_new_checksum NUMBER;
BEGIN
    SELECT rowid
    INTO v_rowid
    FROM tuasach
    WHERE matuasach = p_matuasach
        FOR UPDATE;

    v_new_checksum := owa_opt_lock.checksum('qltv0804', 'tuasach', v_rowid);

    IF v_new_checksum = p_old_checksum THEN
        UPDATE tuasach
        SET tentuasach = p_new_tentuasach
        WHERE rowid = v_rowid;
        -- COMMIT;
    ELSE
        ROLLBACK;
        RAISE_APPLICATION_ERROR(-20000, 'Dòng này vừa được cập nhật, kiểm tra lại để tránh Lost Update');
    END IF;
END;
/

--Package opt_lock_chksum_demo
--spe
create package opt_lock_chksum_demo
as
    procedure get_docgia_details(p_madocgia in number,
                                 p_ho in out varchar2, p_ten in out varchar2, p_sdt in out varchar2,
                                 p_email in out varchar2,
                                 p_row_checksum in out number);
    procedure update_docgia_info(p_madocgia in number,
                                 p_new_ho in varchar2, p_new_ten in varchar2, p_new_sdt in varchar2,
                                 p_new_email in varchar2,
                                 p_checksum in number, p_num_of_rows_updated in out number);
    function calc_checksum(p_madocgia in number,
                           p_ho in varchar2, p_ten in varchar2, p_sdt in varchar2, p_email in varchar2) return number;
end;
/
--body
create or replace package body opt_lock_chksum_demo
as
    procedure get_docgia_details(p_madocgia in number,
                              p_ho in out varchar2, p_ten in out varchar2, p_sdt in out varchar2,
                              p_email in out varchar2,
                              p_row_checksum in out number)
    as
    begin
        select HODOCGIA, TENDOCGIA, SDT, EMAIL, calc_checksum(MADOCGIA, HODOCGIA, TENDOCGIA, SDT, EMAIL)
        into p_ho, p_ten, p_sdt, p_email, p_row_checksum
        from DOCGIA
        where MADOCGIA = p_madocgia;
    end;

    procedure update_docgia_info(p_madocgia in number,
                              p_new_ho in varchar2, p_new_ten in varchar2, p_new_sdt in varchar2,
                              p_new_email in varchar2,
                              p_checksum in number, p_num_of_rows_updated in out number)
        is
    begin
        p_num_of_rows_updated := 0;
        update DOCGIA
        set HODOCGIA  = p_new_ho,
            TENDOCGIA = p_new_ten,
            SDT       = p_new_sdt,
            EMAIL     = p_new_email
        where MADOCGIA = p_madocgia
          and p_checksum = calc_checksum(MADOCGIA, HODOCGIA, TENDOCGIA, SDT, EMAIL);
        p_num_of_rows_updated := sql%rowcount;
    end;

    function calc_checksum(p_madocgia in number,
                           p_ho in varchar2, p_ten in varchar2, p_sdt in varchar2, p_email in varchar2)
        return number
        is
    begin
        return owa_opt_lock.checksum(to_char(p_madocgia, '0009') || p_ho || p_ten || p_sdt || p_email);
    end;
end;
/