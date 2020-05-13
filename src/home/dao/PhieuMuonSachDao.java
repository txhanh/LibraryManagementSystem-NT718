package home.dao;

import home.model.PhieuMuonSach;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhieuMuonSachDao {
    public List<PhieuMuonSach> lietKePhieuMuonSach() {

        List<PhieuMuonSach> danhSachPhieuMuon = new ArrayList<>();

        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "SELECT MAPHIEUMUON,\n" +
                "       DG.MADOCGIA,\n" +
                "       DG.TENDOCGIA,\n" +
                "       CS.MACUONSACH,\n" +
                "       TS.TENTUASACH,\n" +
                "       TS.TENTHELOAI,\n" +
                "       NGAYMUONSACH,\n" +
                "       NGAYDUKIENTRA\n" +
                "FROM ((PHIEUMUONSACH PMS JOIN DOCGIA DG ON PMS.MADOCGIA = DG.MADOCGIA)\n" +
                "    JOIN CUONSACH CS ON PMS.MACUONSACH = CS.MACUONSACH)\n" +
                "         JOIN TUASACH TS ON CS.MATUASACH = TS.MATUASACH\n" +
                "ORDER BY MAPHIEUMUON";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int maPhieuMuon = rs.getInt(1);
                int maDocGia = rs.getInt(2);
                String tenDocGia = rs.getString(3);
                int maCuonSach = rs.getInt(4);
                String tenTuaSach = rs.getString(5);
                String tenTheLoai = rs.getString(6);
                Date ngayMuon = rs.getTimestamp(7);
                Date ngayDuKienTra = rs.getTimestamp(8);

                danhSachPhieuMuon.add(new PhieuMuonSach(maPhieuMuon, maDocGia, tenDocGia, maCuonSach, tenTuaSach,
                        tenTheLoai, ngayMuon, ngayDuKienTra));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return danhSachPhieuMuon;
    }

    public boolean themPhieuMuonSach(PhieuMuonSach phieuMuonSach) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "INSERT INTO PHIEUMUONSACH(MADOCGIA,MACUONSACH,NGAYMUONSACH,NGAYDUKIENTRA)\n" +
                "VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, phieuMuonSach.getMaDocGia());
            preparedStatement.setInt(2, phieuMuonSach.getMaCuonSach());
            preparedStatement.setDate(3, (java.sql.Date) phieuMuonSach.getNgayMuon());
            preparedStatement.setDate(4, (java.sql.Date) phieuMuonSach.getNgayDuKienTra());

            int rs = preparedStatement.executeUpdate();
            if (rs > 0) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean xoaPhieuMuonSach(PhieuMuonSach phieuMuonSach) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "DELETE FROM PHIEUMUONSACH WHERE MAPHIEUMUON = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, phieuMuonSach.getMaPhieuMuon());
            int rs = preparedStatement.executeUpdate();
            if (rs > 0) {
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean capNhatPhieuMuonSach(PhieuMuonSach phieuMuonSach) {
        Connection connection = JDBCConnection.getJDBCConnection();
        String sql = "UPDATE PHIEUMUONSACH\n" +
                "SET MADOCGIA = ?,MACUONSACH = ?, NGAYMUONSACH = ?, NGAYDUKIENTRA = ?\n" +
                "WHERE MAPHIEUMUON = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, phieuMuonSach.getMaDocGia());
            preparedStatement.setInt(2, phieuMuonSach.getMaCuonSach());
            preparedStatement.setDate(3, (java.sql.Date) phieuMuonSach.getNgayMuon());
            preparedStatement.setDate(4, (java.sql.Date) phieuMuonSach.getNgayDuKienTra());
            preparedStatement.setInt(5,phieuMuonSach.getMaPhieuMuon());

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
