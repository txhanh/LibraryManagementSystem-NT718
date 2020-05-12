package home.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.util.Date;

public class PhieuMuonSach {
    private SimpleIntegerProperty maPhieuMuon;
    private SimpleIntegerProperty maDocGia;
    private SimpleStringProperty tenDocGia;
    private SimpleIntegerProperty maCuonSach;
    private SimpleStringProperty tenTuaSach;
    private SimpleStringProperty tenTheLoai;

    private Date ngayMuon;
    private Date ngayDuKienTra;


    public PhieuMuonSach(int maDocGia, String tenDocGia, int maCuonSach, String tenTuaSach,
                         String tenTheLoai, Date ngayMuon, Date ngayDuKienTra) {
        this.maPhieuMuon = new SimpleIntegerProperty(maDocGia);
        this.tenDocGia = new SimpleStringProperty(tenDocGia);
        this.maCuonSach = new SimpleIntegerProperty(maCuonSach);
        this.tenTuaSach = new SimpleStringProperty(tenTuaSach);
        this.tenTheLoai = new SimpleStringProperty(tenTheLoai);

        this.ngayMuon = ngayMuon;
        this.ngayDuKienTra = ngayDuKienTra;
    }

    public Date getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(Date ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public Date getNgayDuKienTra() {
        return ngayDuKienTra;
    }

    public void setNgayDuKienTra(Date ngayDuKienTra) {
        this.ngayDuKienTra = ngayDuKienTra;
    }

    public PhieuMuonSach(int maPhieuMuon, int maDocGia, String tenDocGia, int maCuonSach, String tenTuaSach,
                         String tenTheLoai, Date ngayMuon, Date ngayDuKienTra) {
        this.maPhieuMuon = new SimpleIntegerProperty(maPhieuMuon);
        this.maDocGia = new SimpleIntegerProperty(maDocGia);
        this.tenDocGia = new SimpleStringProperty(tenDocGia);
        this.maCuonSach = new SimpleIntegerProperty(maCuonSach);
        this.tenTuaSach = new SimpleStringProperty(tenTuaSach);
        this.tenTheLoai = new SimpleStringProperty(tenTheLoai);
        this.ngayMuon = ngayMuon;
        this.ngayDuKienTra = ngayDuKienTra;
    }

    public int getMaDocGia() {
        return maDocGia.get();
    }

    public SimpleIntegerProperty maDocGiaProperty() {
        return maDocGia;
    }

    public void setMaDocGia(int maDocGia) {
        this.maDocGia.set(maDocGia);
    }

    public int getMaPhieuMuon() {
        return maPhieuMuon.get();
    }

    public SimpleIntegerProperty maPhieuMuonProperty() {
        return maPhieuMuon;
    }

    public void setMaPhieuMuon(int maPhieuMuon) {
        this.maPhieuMuon.set(maPhieuMuon);
    }

    public String getTenDocGia() {
        return tenDocGia.get();
    }

    public SimpleStringProperty tenDocGiaProperty() {
        return tenDocGia;
    }

    public void setTenDocGia(String tenDocGia) {
        this.tenDocGia.set(tenDocGia);
    }

    public int getMaCuonSach() {
        return maCuonSach.get();
    }

    public SimpleIntegerProperty maCuonSachProperty() {
        return maCuonSach;
    }

    public void setMaCuonSach(int maCuonSach) {
        this.maCuonSach.set(maCuonSach);
    }

    public String getTenTuaSach() {
        return tenTuaSach.get();
    }

    public SimpleStringProperty tenTuaSachProperty() {
        return tenTuaSach;
    }

    public void setTenTuaSach(String tenTuaSach) {
        this.tenTuaSach.set(tenTuaSach);
    }

    public String getTenTheLoai() {
        return tenTheLoai.get();
    }

    public SimpleStringProperty tenTheLoaiProperty() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai.set(tenTheLoai);
    }

//    public SimpleDateFormat getNgayMuon() {
//        return ngayMuon;
//    }
//
//    public void setNgayMuon(SimpleDateFormat ngayMuon) {
//        this.ngayMuon = ngayMuon;
//    }
//
//    public SimpleDateFormat getNgayDuKienTra() {
//        return ngayDuKienTra;
//    }
//
//    public void setNgayDuKienTra(SimpleDateFormat ngayDuKienTra) {
//        this.ngayDuKienTra = ngayDuKienTra;
//    }
}
