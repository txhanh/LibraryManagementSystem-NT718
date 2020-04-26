package home.dao;

import home.model.CuonSach;
import home.model.TuaSach;
import oracle.jdbc.proxy.annotation.Pre;
import sun.dc.pr.PRError;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CuonSachDao {
    public List<CuonSach> lietKeCuonSach(){
        List<CuonSach> danhSachCuonSach = new ArrayList<>();
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT MACUONSACH, T.MATUASACH, TENTUASACH, TENTHELOAI, TACGIA, TRANGTHAI\n" +
                "FROM CUONSACH C JOIN TUASACH T on C.MATUASACH = T.MATUASACH";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                int macuonsach = rs.getInt(1);
                int matuasach = rs.getInt(2);
                String tenTuaSach = rs.getString(3);
                String theLoai = rs.getString(4);
                String tacGia = rs.getString(5);
                int trangThai = rs.getInt(6);

                CuonSach cuonSach = new CuonSach(macuonsach, matuasach, tenTuaSach, theLoai, tacGia, trangThai);
                danhSachCuonSach.add(cuonSach);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return danhSachCuonSach;
    }

    public boolean themCuonSach(CuonSach cuonSach){
        Connection connection = JDBCConnection.getJDBCConnection();
        String sqlSelect = "DECLARE\n" +
                "    v_matuasach TUASACH.MATUASACH%TYPE;\n" +
                "BEGIN\n" +
                "    SELECT MATUASACH into v_matuasach\n" +
                "    FROM TUASACH\n" +
                "    WHERE TENTUASACH = ?;\n" +
                "\n" +
                "    INSERT INTO CUONSACH(MATUASACH,TRANGTHAI)\n" +
                "        VALUES (v_matuasach,?);\n" +
                "\n" +
                "    COMMIT;\n" +
                "\n" +
                "    EXCEPTION\n" +
                "    WHEN OTHERS THEN\n" +
                "    DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_STACK());\n" +
                "    DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE());\n" +
                "    ROLLBACK ;\n" +
                "    RAISE ;\n" +
                "\n" +
                "END;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect);


            preparedStatement.setString(1,cuonSach.getTenTuaSach());
            preparedStatement.setInt(2,cuonSach.getTrangThai());

            int rs = preparedStatement.executeUpdate();

            if(rs > 0){
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;
    }
}
