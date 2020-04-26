package home.dao;

import home.model.CuonSach;
import home.model.TuaSach;

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
}
