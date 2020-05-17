package home.dao;

import home.model.PhieuPhat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhieuPhatDao {
    public List<PhieuPhat> lietKePhieuPhat() {
        List<PhieuPhat> danhSachPhieuPhat = new ArrayList<>();
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT PP.MAPHIEUPHAT,  DG.MADOCGIA, DG.TENDOCGIA, PTS.MAPHIEUTRA, TS.TENTUASACH, PTS" +
                ".TIENPHAT\n" +
                "FROM ((((PHIEUPHAT PP JOIN DOCGIA DG ON PP.MADOCGIA = DG.MADOCGIA) JOIN PHIEUTRASACH PTS ON PP" +
                ".MAPHIEUTRA = PTS.MAPHIEUTRA)\n" +
                "    JOIN PHIEUMUONSACH PMS ON PMS.MAPHIEUMUON = PTS.MAPHIEUMUON) JOIN CUONSACH CS ON CS.MACUONSACH =" +
                " PMS.MACUONSACH)\n" +
                "         JOIN TUASACH TS ON TS.MATUASACH = CS.MATUASACH\n" +
                "ORDER BY PP.MAPHIEUPHAT";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int maPhieuPhat = rs.getInt(1);
                int maDocGia = rs.getInt(2);
                String tenDocGia = rs.getString(3);
                int maPhieuTra = rs.getInt(4);
                String tenTuaSach = rs.getString(5);
                long tienPhat = rs.getLong(6);

                PhieuPhat phieuPhat = new PhieuPhat(maPhieuPhat, maDocGia, tenDocGia, maPhieuTra, tenTuaSach, tienPhat);
                danhSachPhieuPhat.add(phieuPhat);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return danhSachPhieuPhat;
    }

    public boolean xoaPhieuPhat(PhieuPhat phieuPhat) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "DELETE FROM PHIEUPHAT WHERE MAPHIEUPHAT = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, phieuPhat.getMaPhieuPhat());
            int rs = preparedStatement.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return false;
    }

    public boolean themPhieuPhat(PhieuPhat phieuPhat) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "INSERT INTO PHIEUPHAT(MADOCGIA, MAPHIEUTRA, TIENPHAT) " +
                "VALUES(?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, phieuPhat.getMaDocGia());
            preparedStatement.setInt(2, phieuPhat.getMaPhieuTra());
            preparedStatement.setLong(3, phieuPhat.getTienPhat());

            int rs = preparedStatement.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
}
