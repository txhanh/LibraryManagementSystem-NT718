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
//        String sqlSelect = "DECLARE\n" +
//                "    v_matuasach TUASACH.MATUASACH%TYPE;\n" +
//                "    v_count number;\n" +
//                "BEGIN\n" +
//                "    SELECT MATUASACH into v_matuasach\n" +
//                "    FROM TUASACH\n" +
//                "    WHERE TENTUASACH = ?;\n" +
//                "\n" +
//                "    INSERT INTO CUONSACH(MATUASACH, TRANGTHAI)\n" +
//                "    VALUES (v_matuasach, ?);\n" +
//                "    \n" +
//                "    SELECT SOLUONG INTO v_count\n" +
//                "    FROM TUASACH\n" +
//                "    WHERE MATUASACH = v_matuasach;\n" +
//                "    \n" +
//                "    UPDATE TUASACH\n" +
//                "    SET SOLUONG = v_count + 1\n" +
//                "    WHERE MATUASACH = v_matuasach;\n" +
//                "\n" +
//                "    COMMIT;\n" +
//                "\n" +
//                "EXCEPTION\n" +
//                "    WHEN OTHERS THEN\n" +
//                "        DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_STACK());\n" +
//                "        DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE());\n" +
//                "        ROLLBACK;\n" +
//                "        RAISE;\n" +
//                "\n" +
//                "END;";
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
        String sql = "DECLARE\n" +
                "    v_matuasach TUASACH.MATUASACH%TYPE;\n" +
                "    v_count number;\n" +
                "BEGIN\n" +
                "\n" +
                "    SELECT MATUASACH INTO v_matuasach\n" +
                "    FROM CUONSACH\n" +
                "    WHERE MACUONSACH = ?;\n" +
                "\n" +
                "    DELETE FROM CUONSACH WHERE MACUONSACH = ?;\n" +
                "\n" +
                "\n" +
                "    SELECT SOLUONG INTO v_count\n" +
                "    FROM TUASACH\n" +
                "    WHERE MATUASACH = v_matuasach;\n" +
                "\n" +
                "    UPDATE TUASACH\n" +
                "    SET SOLUONG = v_count - 1\n" +
                "    WHERE MATUASACH = v_matuasach;\n" +
                "\n" +
                "    COMMIT;\n" +
                "\n" +
                "EXCEPTION\n" +
                "    WHEN OTHERS THEN\n" +
                "        DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_STACK());\n" +
                "        DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE());\n" +
                "        ROLLBACK;\n" +
                "        RAISE;\n" +
                "\n" +
                "END;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, cuonSach.getMaCuonSach());
            preparedStatement.setInt(2, cuonSach.getMaCuonSach());

            int rs = preparedStatement.executeUpdate();

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
