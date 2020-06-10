-- CODE thực hiện việc tạo bảng + khóa ngoại (không cần ALTER TABLE)

-- auto-generated definition
create table DOCGIA
(
    MADOCGIA  NUMBER generated as identity
        constraint MEMBER_PK
            primary key,
    HODOCGIA  VARCHAR2(25),
    TENDOCGIA VARCHAR2(25),
    SDT       VARCHAR2(15),
    EMAIL     VARCHAR2(50)
)
/

-- auto-generated definition
create table TUASACH
(
    MATUASACH  NUMBER generated as identity
        constraint TUASACH_PK
            primary key,
    TENTUASACH VARCHAR2(100),
    TENTHELOAI VARCHAR2(50),
    TACGIA     VARCHAR2(50),
    NXB        VARCHAR2(50),
    SOLUONG    NUMBER
)
/

-- auto-generated definition
create table CUONSACH
(
    MACUONSACH NUMBER generated as identity
        constraint CUONSACH_PK
            primary key,
    MATUASACH  NUMBER
        constraint CUONSACH_TUASACH_FK  -- tạo khóa ngoại
            references TUASACH,
    TRANGTHAI  VARCHAR2(25)
)
/

-- auto-generated definition
create table PHIEUMUONSACH
(
    MAPHIEUMUON   NUMBER generated as identity
        constraint PHIEUMUONSACH_PK
            primary key,
    MADOCGIA      NUMBER
        constraint PHIEUMUONSACH_DOCGIA_FK -- tạo khóa ngoại
            references DOCGIA,
    MACUONSACH    NUMBER
        constraint PHIEUMUONSACH_CUONSACH_FK -- tạo khóa ngoại
            references CUONSACH,
    NGAYMUONSACH  DATE,
    NGAYDUKIENTRA DATE,
    TRANGTHAIPMS  VARCHAR2(25)
)
/

-- auto-generated definition
create table PHIEUTRASACH
(
    MAPHIEUTRA   NUMBER generated as identity
        constraint PHIEUTRASACH_PK
            primary key,
    MAPHIEUMUON  NUMBER
        constraint PHIEUTRASACH_PHIEUMUONSACH_FK --tạo khóa ngoại
            references PHIEUMUONSACH,
    MADOCGIA     NUMBER
        constraint PHIEUTRASACH_DOCGIA_FK -- tạo khóa ngoại
            references DOCGIA,
    MACUONSACH   NUMBER
        constraint PHIEUTRASACH_CUONSACH_FK
            references CUONSACH,
    NGAYTRASACH  DATE,
    SONGAYMUON   NUMBER,
    SONGAYTRATRE NUMBER,
    TIENPHAT     NUMBER(19)
)
/
    
-- auto-generated definition
create table PHIEUPHAT
(
    MAPHIEUPHAT NUMBER(5) generated as identity
        constraint PHIEUPHAT_PK
            primary key,
    MADOCGIA    NUMBER(5)
        constraint PHIEUPHAT_DOCGIA_FK
            references DOCGIA,
    MAPHIEUTRA  NUMBER(5)
        constraint PHIEUPHAT_PHIEUTRASACH_FK
            references PHIEUTRASACH,
    TIENPHAT    NUMBER(19)
)
/

-- Câu lệnh Insert dữ liệu
--------------------------------------------------------
--  File created - Tuesday-June-09-2020
--------------------------------------------------------
REM INSERTING into QLTV0804.DOCGIA
SET DEFINE OFF;
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Phan Tiến ','Tùng','03151465','tientung@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Phan Tiến','Tùng','032156465','tientungca@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Phạm Đình','San','032165467','sanpham@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Đàm Quang','Thành','03165498','thanhdam@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Vũ Quốc','Anh','0321549878','quocanh@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Phạm Lê','Tuấn','0356987546','anhtuanbuonho@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Lê Đức Phương','Thảo','0314654879','danthoi@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Trần Trường','Sơn','032165456','son@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Nguyễn Quang','Hải Hải','0416584','quanghai97@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Nguyễn Ngọc','Tùng','0224679879','tungng@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Phan Minh','Quân Eayong','0965234873','quanphan@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Trần Xuân','Hạnh','0358844499','hanh@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Mai Xuân','Hoan','03264879','xuanhoan.xz@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Cristiano','Ronaldoooo','0156454587','cr7@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Đỗ Thị ','Quang Anh','024187987','quanganhbede2k@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Trần Nguyên','Chiến','893741283971','chien@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Giàng','Mí Khề','03156456','hovoi@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Nguyễn Huy Ngọc','Anh','03467987987','ngocanh@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Mai Đức','Thanh','031654','maiducthanh@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Trần Thị','Mỹ Uyên','032115645','uyen@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Nguyễn Thị Thúy','Quỳnh','0165484798','quynh@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Phan Đăng','Nhật Minh','054679878','nhatminh@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Trần Hải','Đăng','031156487','dang@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Phan Thị','Nguyệt','03165487','nguyet@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Bùi Tiến Tiến','Dũng','02315648','dungbui@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Hồ Xuân','Vương','032165465','vuong@gmail.com');
Insert into QLTV0804.DOCGIA (HODOCGIA,TENDOCGIA,SDT,EMAIL) values ('Đinh Thị Khánh','Tường','487239809','tuong@gmail.com');



REM INSERTING into QLTV0804.TUASACH
SET DEFINE OFF;
Insert into QLTV0804.TUASACH (TENTUASACH,TENTHELOAI,TACGIA,NXB,SOLUONG) values ('Nhập môn lập trình','Giáo trình','Nguyễn Tấn Trần Minh Khang','ĐHQG TPHCM',2);
Insert into QLTV0804.TUASACH (TENTUASACH,TENTHELOAI,TACGIA,NXB,SOLUONG) values ('Nhập môn công nghệ phần mềm','Giáo trình','Phạm Thi Vương','ĐGQG TPHCM',1);
Insert into QLTV0804.TUASACH (TENTUASACH,TENTHELOAI,TACGIA,NXB,SOLUONG) values ('Đô rê mon tập 11','Khác','Fujiko Ô Mô Tô Nô Nô','Kim Đồng',2);
Insert into QLTV0804.TUASACH (TENTUASACH,TENTHELOAI,TACGIA,NXB,SOLUONG) values ('Nghĩ đúng thì đừng làm sai','Khác','Tự bịa','Kim Đồng',3);
Insert into QLTV0804.TUASACH (TENTUASACH,TENTHELOAI,TACGIA,NXB,SOLUONG) values ('Deep learning và việc áp dụng thực tiễn','Luận văn','Hoàng Lâm','Giáo dục',2);
Insert into QLTV0804.TUASACH (TENTUASACH,TENTHELOAI,TACGIA,NXB,SOLUONG) values ('Những nguyên lí cơ bản của Chủ nghĩa Mác - Lênin','Giáo trình','Ban tuyên giáo','Chính trị Quốc gia',1);
Insert into QLTV0804.TUASACH (TENTUASACH,TENTHELOAI,TACGIA,NXB,SOLUONG) values ('Toán lớp 8 HK1','Tài liệu tham khảo','Tôn Thân','Giáo dục',0);
Insert into QLTV0804.TUASACH (TENTUASACH,TENTHELOAI,TACGIA,NXB,SOLUONG) values ('Ứng dụng công nghệ nhận diện khuôn mặt vào cuộc sống','Luận văn','Hồ Đắc Thanh Chương','Tổng hợp TPHCM',5);
Insert into QLTV0804.TUASACH (TENTUASACH,TENTHELOAI,TACGIA,NXB,SOLUONG) values ('Lập trình hướng đối tượng OOP','Giáo trình','Thái Hải Dương','Giáo dục',2);
Insert into QLTV0804.TUASACH (TENTUASACH,TENTHELOAI,TACGIA,NXB,SOLUONG) values ('Giới thiệu tổng quan về Hệ điều hành Linux','Tài liệu tham khảo','Trần Ngọc Sơn','Tổng hợp TPHCM',4);
Insert into QLTV0804.TUASACH (TENTUASACH,TENTHELOAI,TACGIA,NXB,SOLUONG) values ('Đắc nhân tâm - nghe nói hay lắm mà mình chưa đọc','Tạp chí Khoa Học','Ông nào nước Mĩ thì phải','Trẻ',0);
Insert into QLTV0804.TUASACH (TENTUASACH,TENTHELOAI,TACGIA,NXB,SOLUONG) values ('Những điều cần biết khi ở tuổi 20','Khác','Ngô Ngọc Nghĩa','Trẻ',1);
Insert into QLTV0804.TUASACH (TENTUASACH,TENTHELOAI,TACGIA,NXB,SOLUONG) values ('Nhập môn mạch số','Giáo trình','Lầu Phi Tường','ĐHQG TPHCM',1);
Insert into QLTV0804.TUASACH (TENTUASACH,TENTHELOAI,TACGIA,NXB,SOLUONG) values ('Tư tưởng Hồ Chí Minh','Giáo trình','Bộ Giáo dục và Đào tạo','Chính trị Quốc gia',1);
Insert into QLTV0804.TUASACH (TENTUASACH,TENTHELOAI,TACGIA,NXB,SOLUONG) values ('Dạy con làm giàu','Tài liệu tham khảo','James DDay','Giáo dục',2);
Insert into QLTV0804.TUASACH (TENTUASACH,TENTHELOAI,TACGIA,NXB,SOLUONG) values ('Đừng bao giờ đi ăn một mình ','Khác','Hà Duy Dũng','Trẻ',1);
Insert into QLTV0804.TUASACH (TENTUASACH,TENTHELOAI,TACGIA,NXB,SOLUONG) values ('Thuật toán Vote rank và xác định tập ảnh hưởng','Tạp chí Khoa Học','Hoàng Anh Kiệt','ĐHQG TPHCM',3);


----------------------------------------------------------------------------------------------------------------
REM INSERTING into QLTV0804.CUONSACH
SET DEFINE OFF;
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (83,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (6,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (1,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (2,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (5,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (26,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (82,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (82,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (21,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (21,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (28,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (7,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (1,'Chưa mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (4,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (83,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (81,'Chưa mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (29,'Chưa mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (24,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (3,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (29,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (4,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (29,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (5,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (83,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (81,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (5,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (26,'Chưa mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (5,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (81,'Đã mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (81,'Chưa mượn');
Insert into QLTV0804.CUONSACH (MATUASACH,TRANGTHAI) values (81,'Đã mượn');

REM INSERTING into QLTV0804.PHIEUMUONSACH
SET DEFINE OFF;
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (89,107,to_date('14-MAY-20','DD-MON-RR'),to_date('19-MAY-20','DD-MON-RR'),'Chưa trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (121,223,to_date('01-JUN-20','DD-MON-RR'),to_date('06-JUN-20','DD-MON-RR'),'Chưa trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (1,228,to_date('03-JUN-20','DD-MON-RR'),to_date('08-JUN-20','DD-MON-RR'),'Chưa trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (103,245,to_date('05-JUN-20','DD-MON-RR'),to_date('10-JUN-20','DD-MON-RR'),'Chưa trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (322,242,to_date('05-JUN-20','DD-MON-RR'),to_date('10-JUN-20','DD-MON-RR'),'Chưa trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (1,241,to_date('05-JUN-20','DD-MON-RR'),to_date('10-JUN-20','DD-MON-RR'),'Chưa trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (65,252,to_date('06-JUN-20','DD-MON-RR'),to_date('11-JUN-20','DD-MON-RR'),'Chưa trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (121,221,to_date('01-JUN-20','DD-MON-RR'),to_date('06-JUN-20','DD-MON-RR'),'Chưa trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (222,162,to_date('16-MAY-20','DD-MON-RR'),to_date('21-MAY-20','DD-MON-RR'),'Đã trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (90,247,to_date('05-JUN-20','DD-MON-RR'),to_date('10-JUN-20','DD-MON-RR'),'Chưa trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (89,249,to_date('06-JUN-20','DD-MON-RR'),to_date('11-JUN-20','DD-MON-RR'),'Đã trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (89,222,to_date('01-JUN-20','DD-MON-RR'),to_date('06-JUN-20','DD-MON-RR'),'Chưa trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (1,224,to_date('01-JUN-20','DD-MON-RR'),to_date('06-JUN-20','DD-MON-RR'),'Chưa trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (1,225,to_date('01-JUN-20','DD-MON-RR'),to_date('06-JUN-20','DD-MON-RR'),'Chưa trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (1,243,to_date('05-JUN-20','DD-MON-RR'),to_date('10-JUN-20','DD-MON-RR'),'Chưa trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (142,248,to_date('06-JUN-20','DD-MON-RR'),to_date('11-JUN-20','DD-MON-RR'),'Đã trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (221,161,to_date('17-MAY-20','DD-MON-RR'),to_date('22-MAY-20','DD-MON-RR'),'Đã trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (121,123,to_date('14-MAY-20','DD-MON-RR'),to_date('19-MAY-20','DD-MON-RR'),'Đã trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (63,181,to_date('19-MAY-20','DD-MON-RR'),to_date('24-MAY-20','DD-MON-RR'),'Đã trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (3,227,to_date('06-JUN-20','DD-MON-RR'),to_date('11-JUN-20','DD-MON-RR'),'Chưa trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (3,229,to_date('06-JUN-20','DD-MON-RR'),to_date('11-JUN-20','DD-MON-RR'),'Chưa trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (4,142,to_date('15-MAY-20','DD-MON-RR'),to_date('20-MAY-20','DD-MON-RR'),'Đã trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (221,182,to_date('01-JUN-20','DD-MON-RR'),to_date('06-JUN-20','DD-MON-RR'),'Chưa trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (321,250,to_date('05-JUN-20','DD-MON-RR'),to_date('10-JUN-20','DD-MON-RR'),'Chưa trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (47,246,to_date('05-JUN-20','DD-MON-RR'),to_date('10-JUN-20','DD-MON-RR'),'Đã trả');
Insert into QLTV0804.PHIEUMUONSACH (MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA,TRANGTHAIPMS) values (1,244,to_date('06-JUN-20','DD-MON-RR'),to_date('11-JUN-20','DD-MON-RR'),'Chưa trả');

REM INSERTING into QLTV0804.PHIEUTRASACH
SET DEFINE OFF;
Insert into QLTV0804.PHIEUTRASACH (MAPHIEUMUON,MADOCGIA,MACUONSACH,NGAYTRASACH,SONGAYMUON,SONGAYTRATRE,TIENPHAT) values (122,4,142,to_date('28-MAY-20','DD-MON-RR'),13,8,8000);
Insert into QLTV0804.PHIEUTRASACH (MAPHIEUMUON,MADOCGIA,MACUONSACH,NGAYTRASACH,SONGAYMUON,SONGAYTRATRE,TIENPHAT) values (118,121,123,to_date('19-MAY-20','DD-MON-RR'),5,0,0);
Insert into QLTV0804.PHIEUTRASACH (MAPHIEUMUON,MADOCGIA,MACUONSACH,NGAYTRASACH,SONGAYMUON,SONGAYTRATRE,TIENPHAT) values (241,47,246,to_date('08-JUN-20','DD-MON-RR'),3,0,0);
Insert into QLTV0804.PHIEUTRASACH (MAPHIEUMUON,MADOCGIA,MACUONSACH,NGAYTRASACH,SONGAYMUON,SONGAYTRATRE,TIENPHAT) values (249,142,248,to_date('15-JUN-20','DD-MON-RR'),9,4,4000);
Insert into QLTV0804.PHIEUTRASACH (MAPHIEUMUON,MADOCGIA,MACUONSACH,NGAYTRASACH,SONGAYMUON,SONGAYTRATRE,TIENPHAT) values (248,89,249,to_date('08-JUN-20','DD-MON-RR'),2,0,0);
Insert into QLTV0804.PHIEUTRASACH (MAPHIEUMUON,MADOCGIA,MACUONSACH,NGAYTRASACH,SONGAYMUON,SONGAYTRATRE,TIENPHAT) values (141,222,162,to_date('19-MAY-20','DD-MON-RR'),3,0,0);
Insert into QLTV0804.PHIEUTRASACH (MAPHIEUMUON,MADOCGIA,MACUONSACH,NGAYTRASACH,SONGAYMUON,SONGAYTRATRE,TIENPHAT) values (121,221,161,to_date('06-JUN-20','DD-MON-RR'),20,15,15000);

REM INSERTING into QLTV0804.PHIEUPHAT
SET DEFINE OFF;
Insert into QLTV0804.PHIEUPHAT (MADOCGIA,MAPHIEUTRA,TIENPHAT) values (4,104,8000);
Insert into QLTV0804.PHIEUPHAT (MADOCGIA,MAPHIEUTRA,TIENPHAT) values (221,105,15000);
Insert into QLTV0804.PHIEUPHAT (MADOCGIA,MAPHIEUTRA,TIENPHAT) values (142,141,4000);


