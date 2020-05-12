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
        String sql = "SELECT MAPHIEUMUON, DG.MADOCGIA, DG.TENDOCGIA, CS.MACUONSACH, TS.TENTUASACH, TS.TENTHELOAI," +
                "NGAYMUONSACH, NGAYDUKIENTRA\n" +
                "FROM ((PHIEUMUONSACH PMS JOIN DOCGIA DG ON PMS.MADOCGIA = DG.MADOCGIA)\n" +
                "        JOIN CUONSACH CS ON PMS.MACUONSACH = CS.MACUONSACH)\n" +
                "         JOIN TUASACH TS ON CS.MATUASACH = TS.MATUASACH";
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
}
