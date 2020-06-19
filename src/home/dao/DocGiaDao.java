package home.dao;

import home.model.DocGia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
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
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "UPDATE DOCGIA SET HODOCGIA = ?, TENDOCGIA = ?, SDT = ?, EMAIL = ? " +
                "WHERE MADOCGIA = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,member.getHoDocGia());
            preparedStatement.setString(2,member.getTenDocGia());
            preparedStatement.setString(3,member.getSdt());
            preparedStatement.setString(4,member.getEmail());
            preparedStatement.setInt(5,member.getMaDocGia());

            int rs = preparedStatement.executeUpdate();
            if(rs > 0){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
