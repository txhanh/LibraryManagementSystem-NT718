package home.dao;

import home.controller.PhieuMuonSachDanhSachController;
import home.model.PhieuMuonSach;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static home.controller.CuonSachThemController.trangThai0;
import static home.controller.CuonSachThemController.trangThai1;

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
                "       NGAYDUKIENTRA,\n" +
                "       TRANGTHAIPMS\n" +
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
                String trangThaiPMS = rs.getString(9);

                danhSachPhieuMuon.add(new PhieuMuonSach(maPhieuMuon, maDocGia, tenDocGia, maCuonSach, tenTuaSach,
                        tenTheLoai, ngayMuon, ngayDuKienTra, trangThaiPMS));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return danhSachPhieuMuon;
    }

    public boolean themPhieuMuonSach(PhieuMuonSach phieuMuonSach) {
        Connection connection = JDBCConnection.getJDBCConnection();

        try {
            CallableStatement cstmt = connection.prepareCall("{call PROC_THEMPHIEUMUONSACH(?,?,?,?,?)}");
            cstmt.setInt(1, phieuMuonSach.getMaDocGia());
            cstmt.setInt(2, phieuMuonSach.getMaCuonSach());
            cstmt.setDate(3, (java.sql.Date) phieuMuonSach.getNgayMuon());
            cstmt.setDate(4, (java.sql.Date) phieuMuonSach.getNgayDuKienTra());
            cstmt.setString(5, phieuMuonSach.getTrangThaiPMS());

            int rs = cstmt.executeUpdate();
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
        String sql = "BEGIN\n" +
                "    DELETE FROM PHIEUMUONSACH WHERE MAPHIEUMUON = ?;\n" +
                "\n" +
                "    UPDATE CUONSACH\n" +
                "    SET TRANGTHAI = 'Chưa mượn'\n" +
                "    WHERE MACUONSACH = ?;\n" +
                "\n" +
                "    COMMIT ;\n" +
                "EXCEPTION\n" +
                "    WHEN OTHERS THEN\n" +
                "        DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_STACK());\n" +
                "        DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE());\n" +
                "        ROLLBACK;\n" +
                "        RAISE;\n" +
                "end;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, phieuMuonSach.getMaPhieuMuon());
            preparedStatement.setInt(2, phieuMuonSach.getMaCuonSach());
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
        String sql = "DECLARE\n" +
                "    v_macuonsachOld int;\n" +
                "    v_macuonsachNew int;\n" +
                "BEGIN\n" +
                "\n" +
                "    v_macuonsachOld :=?;\n" +
                "    v_macuonsachNew :=?;\n" +
                "\n" +
                "    UPDATE PHIEUMUONSACH\n" +
                "    SET MADOCGIA      = ?,\n" +
                "        MACUONSACH    = ?,\n" +
                "        NGAYMUONSACH  = ?,\n" +
                "        NGAYDUKIENTRA = ?,\n" +
                "        TRANGTHAIPMS = ?\n" +
                "    WHERE MAPHIEUMUON = ?;\n" +
                "\n" +
                "    UPDATE CUONSACH\n" +
                "    SET TRANGTHAI = 'Chưa mượn'\n" +
                "    WHERE MACUONSACH = v_macuonsachOld;\n" +
                "\n" +
                "    UPDATE CUONSACH\n" +
                "    SET TRANGTHAI = 'Đã mượn'\n" +
                "    WHERE MACUONSACH = v_macuonsachNew;\n" +
                "\n" +
                "    COMMIT;\n" +
                "EXCEPTION\n" +
                "    WHEN OTHERS THEN\n" +
                "        DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_STACK());\n" +
                "        DBMS_OUTPUT.PUT_LINE(DBMS_UTILITY.FORMAT_ERROR_BACKTRACE());\n" +
                "        ROLLBACK;\n" +
                "        RAISE;\n" +
                "END;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, PhieuMuonSachDanhSachController.v_maCuonSach);
            preparedStatement.setInt(2,phieuMuonSach.getMaCuonSach());
            preparedStatement.setInt(3, phieuMuonSach.getMaDocGia());
            preparedStatement.setInt(4, phieuMuonSach.getMaCuonSach());
            preparedStatement.setDate(5, (java.sql.Date) phieuMuonSach.getNgayMuon());
            preparedStatement.setDate(6, (java.sql.Date) phieuMuonSach.getNgayDuKienTra());
            preparedStatement.setString(7, phieuMuonSach.getTrangThaiPMS());
            preparedStatement.setInt(8, phieuMuonSach.getMaPhieuMuon());

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
