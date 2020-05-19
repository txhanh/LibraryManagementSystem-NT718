package home.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.DatePicker;

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
    private SimpleObjectProperty<Date> ngayMuon;
    private SimpleObjectProperty<Date> ngayDuKienTra;
    private SimpleStringProperty trangThaiPMS;


    public PhieuMuonSach(int maDocGia, String tenDocGia, int maCuonSach, String tenTuaSach,
                         String tenTheLoai, Date ngayMuon, Date ngayDuKienTra, String trangThaiPMS) {
        this.maDocGia = new SimpleIntegerProperty(maDocGia);
        this.tenDocGia = new SimpleStringProperty(tenDocGia);
        this.maCuonSach = new SimpleIntegerProperty(maCuonSach);
        this.tenTuaSach = new SimpleStringProperty(tenTuaSach);
        this.tenTheLoai = new SimpleStringProperty(tenTheLoai);
        this.ngayMuon = new SimpleObjectProperty<>(ngayMuon);
        this.ngayDuKienTra = new SimpleObjectProperty<>(ngayDuKienTra);
        this.trangThaiPMS = new SimpleStringProperty(trangThaiPMS);

    }


    public PhieuMuonSach(int maPhieuMuon, int maDocGia, String tenDocGia, int maCuonSach, String tenTuaSach,
                         String tenTheLoai, Date ngayMuon, Date ngayDuKienTra, String trangThaiPMS) {
        this.maPhieuMuon = new SimpleIntegerProperty(maPhieuMuon);
        this.maDocGia = new SimpleIntegerProperty(maDocGia);
        this.tenDocGia = new SimpleStringProperty(tenDocGia);
        this.maCuonSach = new SimpleIntegerProperty(maCuonSach);
        this.tenTuaSach = new SimpleStringProperty(tenTuaSach);
        this.tenTheLoai = new SimpleStringProperty(tenTheLoai);
        this.ngayMuon = new SimpleObjectProperty<>(ngayMuon);
        this.ngayDuKienTra = new SimpleObjectProperty<>(ngayDuKienTra);
        this.trangThaiPMS = new SimpleStringProperty(trangThaiPMS);
    }

    public PhieuMuonSach(int maDocGia, int maCuonSach, Date ngayMuon, Date ngayDuKienTra, String trangThaiPMS) {
        this.maDocGia = new SimpleIntegerProperty(maDocGia);
        this.maCuonSach = new SimpleIntegerProperty(maCuonSach);
        this.ngayMuon = new SimpleObjectProperty<>(ngayMuon);
        this.ngayDuKienTra = new SimpleObjectProperty<>(ngayDuKienTra);
        this.trangThaiPMS = new SimpleStringProperty(trangThaiPMS);
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

    public Date getNgayMuon() {
        return ngayMuon.get();
    }

    public SimpleObjectProperty<Date> ngayMuonProperty() {
        return ngayMuon;
    }

    public void setNgayMuon(Date ngayMuon) {
        this.ngayMuon.set(ngayMuon);
    }

    public Date getNgayDuKienTra() {
        return ngayDuKienTra.get();
    }

    public SimpleObjectProperty<Date> ngayDuKienTraProperty() {
        return ngayDuKienTra;
    }

    public void setNgayDuKienTra(Date ngayDuKienTra) {
        this.ngayDuKienTra.set(ngayDuKienTra);
    }

    public String getTrangThaiPMS() {
        return trangThaiPMS.get();
    }

    public SimpleStringProperty trangThaiPMSProperty() {
        return trangThaiPMS;
    }

    public void setTrangThaiPMS(String trangThaiPMS) {
        this.trangThaiPMS.set(trangThaiPMS);
    }
}
