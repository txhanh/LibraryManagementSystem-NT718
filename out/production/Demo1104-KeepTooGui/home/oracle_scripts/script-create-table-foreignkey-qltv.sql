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

