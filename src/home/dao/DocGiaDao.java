package home.dao;

import home.model.DocGia;
import home.util.InputUtil;
import oracle.jdbc.OracleTypes;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocGiaDao {
    public List<DocGia> getAllMember() {
        List<DocGia> memberList = new ArrayList<DocGia>();
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT * FROM DOCGIA ORDER BY MADOCGIA";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String ho = rs.getString(2);
                String ten = rs.getString(3);
                String sdt = rs.getString(4);
                String email = rs.getString(5);


                memberList.add(new DocGia(id, ho, ten, sdt, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memberList;
    }

    public boolean addMember(DocGia member) throws SQLException {

        Connection connection = JDBCConnection.getJDBCConnection();

        String sql = "INSERT INTO DOCGIA(hodocgia,tendocgia,sdt,email) VALUES (?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, member.getHoDocGia());
            preparedStatement.setString(2, member.getTenDocGia());
            preparedStatement.setString(3, member.getSdt());
            preparedStatement.setString(4, member.getEmail());
            int rs = preparedStatement.executeUpdate();
//            System.out.println(rs);
            if (rs > 0) {
                String sql2 = "SELECT MADOCGIA, HODOCGIA, TENDOCGIA FROM DOCGIA ORDER BY MADOCGIA";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                ResultSet resultSet =  preparedStatement2.executeQuery();
                while (resultSet.next()){
                    int madocgia = resultSet.getInt(1);
                    String hodocgia = resultSet.getString(2);
                    String tendocgia = resultSet.getString(3);
                    System.out.println("Mã độc giả " + madocgia + " Họ: " + hodocgia + " Tên: " + tendocgia);
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        connection.close();
        return false;
    }

    public boolean updateMember(DocGia member) {
        Connection conn = null;
        conn = JDBCConnection.getJDBCConnection();

        int madocgia = member.getMaDocGia();
        long rowChecksum = 0;
        try {
            rowChecksum = _displayEmpDetails(conn, madocgia);
            InputUtil.waitTillUserHitsEnter("Bạn đã click nút cập nhật, những dữ liệu chưa bị khóa.");
            boolean flag = _updateEmpInfo(conn, madocgia, member.getHoDocGia(), member.getTenDocGia(), member.getSdt(),member.getEmail(), rowChecksum);
            if(flag){
                return true;
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }

        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    private static long _displayEmpDetails(Connection conn, int madocgia)
            throws SQLException {
        CallableStatement cstmt = null;
        long rowChecksum = 0;

        String ho = null;
        String ten = null;
        String sdt = null;
        String email = null;

        cstmt = conn.prepareCall(
                "{call opt_lock_chksum_demo.get_docgia_details(?, ?, ?, ?, ?, ?)}");
        cstmt.setInt(1, madocgia);
        cstmt.registerOutParameter(2, OracleTypes.VARCHAR);
        cstmt.registerOutParameter(3, OracleTypes.VARCHAR);
        cstmt.registerOutParameter(4, OracleTypes.VARCHAR);
        cstmt.registerOutParameter(5, OracleTypes.VARCHAR);
        cstmt.registerOutParameter(6, OracleTypes.NUMBER);

        cstmt.execute();

        ho = cstmt.getString(2);
        ten = cstmt.getString(3);
        sdt = cstmt.getString(4);
        email = cstmt.getString(5);
        rowChecksum = cstmt.getLong(6);
        System.out.println("Dữ liệu độc giả trước khi cập nhật:");
        System.out.println("Mã độc giả: " + madocgia + ", Họ: " + ho + ", Tên: "
                + ten + ", SĐT: " + sdt + ", Email: " + email + ", Checksum: " + rowChecksum);

        return rowChecksum;
    }

    private static boolean _updateEmpInfo(Connection conn, int madocgia,
                                       String new_ho, String new_ten, String new_sdt, String new_email, long rowChecksum)
            throws SQLException {

        CallableStatement cstmt = null;

        cstmt = conn.prepareCall(
                "{call opt_lock_chksum_demo.update_docgia_info(?, ?, ?, ?, ?, ?, ?)}");
        cstmt.setInt(1, madocgia);
        cstmt.setString(2, new_ho);
        cstmt.setString(3, new_ten);
        cstmt.setString(4, new_sdt);
        cstmt.setString(5, new_email);
        cstmt.setLong(6, rowChecksum);
        cstmt.registerOutParameter(7, OracleTypes.NUMBER);
        cstmt.execute();
        int numOfRowsUpdated = cstmt.getInt(7);
        if (numOfRowsUpdated <= 0) {
            System.out.println("Sorry. Có ai đó đang chỉnh sửa dữ liệu mà bạn cũng đang chỉnh sửa. Thử lại sau!" +
                    ".\" ");
            return false;
        } else {
            System.out.println("Bạn cập nhật thông tin độc giả thành công.");
            return true;
        }


    }

    public boolean deleteMember(DocGia member) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "DELETE FROM DOCGIA WHERE MADOCGIA = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, member.getMaDocGia());
            int rs = preparedStatement.executeUpdate();
//            System.out.println(rs);
            if (rs > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
