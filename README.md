Phần mềm quản lý thư viện
Công cụ và môi trường lập trình được sử dụng
1. IDE: IntelliJ IDEA bản Ultimate (dùng thử 30 ngày hoặc free 1 năm cho mail có đuôi "edu")
2. JDK 8
3. GUI: sử dụng JavaFX
4. Tools build GUI: Scene Builder, download tại https://gluonhq.com/products/scene-builder/
5. DBMS: Oracle 12c, port 1521
6. Tích hợp Docker trong IDE và chạy Oracle DB trong IDE (tùy chọn, không bắt buộc)
7. Sử dụng phần mềm iReport để tạo báo cáo, thống kê. Sử dụng một số thư viện trong thư mục home/libs.

    Chỉ cần import tất cả thư viện trong thư mục libs này là chạy được project
8. Các file report *.jrxml được tạo sẵn để trong home/report
9. File script tạo Oracle Databse trong home/oracle_scripts

HƯỚNG DẪN CLONE CODE VỀ VÀ CHẠY
1. Cài đặt git: https://git-scm.com/
2. Mở bash/terminal và gõ git clone https://github.com/TranXuanHanh/LibraryManagementSystem-NT718.git
3. Sau khi tải clone về, mở NetBean hoặc IntelliJ lên và Open project vừa tải về
4. Vào Oracle và tạo 1 user tên là qltv0804 với mật khẩu là admin
5. Mở thư mục oracle_scripts trong project và copy code trong file *.sql
6. Mở Oracle Developer lên paste code ở bước 5 vào và run
7. Vào thư mục libs trong project để lấy tất cả các thư viện có đuôi *.jar và thực hiện Add Jar File trong NetBeans hoặc IntelliJ
8. Build và Run Application
9. Giao diện xuất hiện nhưng chưa có dữ liệu trong DB
10. Thực hiện Insert thủ công vào các Bảng