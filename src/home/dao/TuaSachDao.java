package home.dao;

import home.model.TuaSach;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TuaSachDao {

    public List<TuaSach> lietKeTuaSach() {
        List<TuaSach> tuaSachList = new ArrayList<>();
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT * FROM TUASACH ORDER BY MATUASACH";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                int matuasach = rs.getInt(1);
                String tenSach = rs.getString(2);
                String theLoai = rs.getString(3);
                String tacGia = rs.getString(4);
                String nxb = rs.getString(5);
                int soluong = rs.getInt(6);

                TuaSach tuaSach = new TuaSach(matuasach, tenSach, theLoai, tacGia, nxb, soluong);
                tuaSachList.add(tuaSach);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tuaSachList;
    }

    public List<String> lietKeTenTuaSach() {
        List<String> tenTuaSachList = new ArrayList<>();
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT TENTUASACH FROM TUASACH";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String tenSach = rs.getString("TENTUASACH");
                tenTuaSachList.add(tenSach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tenTuaSachList;
    }

    public boolean themTuaSach(TuaSach tuaSach) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "INSERT INTO TUASACH(TENTUASACH,TENTHELOAI,TACGIA,NXB,SOLUONG) VALUES" +
                "(?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, tuaSach.getTenSach());
            preparedStatement.setString(2, tuaSach.getTheLoai());
            preparedStatement.setString(3, tuaSach.getTacGia());
            preparedStatement.setString(4, tuaSach.getNXB());
            preparedStatement.setInt(5, tuaSach.getSoLuong());

            int rs = preparedStatement.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean xoaTuaSach(TuaSach tuaSach) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "DELETE FROM TUASACH WHERE MATUASACH = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tuaSach.getMaTuaSach());
            int rs = preparedStatement.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean capNhatTuaSach(TuaSach tuaSach) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "UPDATE TUASACH SET TENTUASACH = ?, TENTHELOAI = ?, TACGIA = ? ," +
                "NXB = ?, SOLUONG = ?" +
                "WHERE MATUASACH = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, tuaSach.getTenSach());
            preparedStatement.setString(2, tuaSach.getTheLoai());
            preparedStatement.setString(3, tuaSach.getTacGia());
            preparedStatement.setString(4, tuaSach.getNXB());
            preparedStatement.setInt(5, tuaSach.getSoLuong());
            preparedStatement.setInt(6, tuaSach.getMaTuaSach());

            int rs = preparedStatement.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;
    }
}
