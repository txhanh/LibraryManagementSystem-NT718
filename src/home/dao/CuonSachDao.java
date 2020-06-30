package home.dao;

import home.controller.CuonSachDanhSachController;
import home.controller.TuaSachDanhSachController;
import home.model.CuonSach;
import home.model.TuaSach;
import oracle.jdbc.proxy.annotation.Pre;
import sun.dc.pr.PRError;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuonSachDao {
    public List<CuonSach> lietKeCuonSach() {
        List<CuonSach> danhSachCuonSach = new ArrayList<>();
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT MACUONSACH, T.MATUASACH, TENTUASACH, TENTHELOAI, TACGIA, TRANGTHAI\n" +
                "FROM CUONSACH C\n" +
                "         JOIN TUASACH T on C.MATUASACH = T.MATUASACH\n" +
                "ORDER BY T.MATUASACH, C.MACUONSACH";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int macuonsach = rs.getInt(1);
                int matuasach = rs.getInt(2);
                String tenTuaSach = rs.getString(3);
                String theLoai = rs.getString(4);
                String tacGia = rs.getString(5);
                String trangThai = rs.getString(6);

                CuonSach cuonSach = new CuonSach(macuonsach, matuasach, tenTuaSach, theLoai, tacGia, trangThai);
                danhSachCuonSach.add(cuonSach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return danhSachCuonSach;
    }

    public boolean themCuonSach(CuonSach cuonSach) {
        Connection connection = JDBCConnection.getJDBCConnection();

        try {
            CallableStatement cstmt = connection.prepareCall("{call PROC_THEMCUONSACH(?,?)}");


            cstmt.setString(1, cuonSach.getTenTuaSach());
            cstmt.setString(2, cuonSach.getTrangThai());

            int rs = cstmt.executeUpdate();

            if (rs > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;
    }

    public boolean XoaCuonSach(CuonSach cuonSach) {

        Connection connection = JDBCConnection.getJDBCConnection();
        try {
            CallableStatement cstmt = connection.prepareCall("{call PROC_XOACUONSACH(?)}");

            cstmt.setInt(1, cuonSach.getMaCuonSach());

            int rs = cstmt.executeUpdate();

            if (rs > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean capNhatCuonSach(CuonSach cuonSach) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "UPDATE CUONSACH\n" +
                "SET TRANGTHAI = ?\n" +
                "WHERE MACUONSACH = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);


            preparedStatement.setString(1, cuonSach.getTrangThai());
            preparedStatement.setInt(2, CuonSachDanhSachController.v_macuonsach);

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
