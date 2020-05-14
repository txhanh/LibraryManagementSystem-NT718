package home.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class PhieuTraSach {
    private SimpleIntegerProperty maPhieuTra;
    private SimpleIntegerProperty maPhieuMuon;
    private SimpleStringProperty tenSachMuon;
    private SimpleStringProperty tenDocGia;
    private SimpleObjectProperty<Date> ngayMuonSach;
    private SimpleObjectProperty<Date> ngayTraSach;
    private SimpleIntegerProperty soNgayMuon;
    private SimpleIntegerProperty soNgayTraTre;
    private SimpleLongProperty tienPhat;

    public PhieuTraSach(int maPhieuMuon,
                        String tenSachMuon, String tenDocGia,
                        Date ngayMuonSach, Date ngayTraSach,
                        int soNgayMuon, int soNgayTraTre,
                        long tienPhat) {
        this.maPhieuMuon = new SimpleIntegerProperty(maPhieuMuon);
        this.tenSachMuon = new SimpleStringProperty(tenSachMuon);
        this.tenDocGia = new SimpleStringProperty(tenDocGia);
        this.ngayMuonSach = new SimpleObjectProperty<>(ngayMuonSach);
        this.ngayTraSach = new SimpleObjectProperty<>(ngayTraSach);
        this.soNgayMuon = new SimpleIntegerProperty(soNgayMuon);
        this.soNgayTraTre = new SimpleIntegerProperty(soNgayTraTre);
        this.tienPhat = new SimpleLongProperty(tienPhat);
    }

    public PhieuTraSach(int maPhieuTra, int maPhieuMuon,
                        String tenSachMuon, String tenDocGia,
                        Date ngayMuonSach, Date ngayTraSach,
                        int soNgayMuon, int soNgayTraTre,
                        long tienPhat) {
        this.maPhieuTra = new SimpleIntegerProperty(maPhieuTra);
        this.maPhieuMuon = new SimpleIntegerProperty(maPhieuMuon);
        this.tenSachMuon = new SimpleStringProperty(tenSachMuon);
        this.tenDocGia = new SimpleStringProperty(tenDocGia);
        this.ngayMuonSach = new SimpleObjectProperty<>(ngayMuonSach);
        this.ngayTraSach = new SimpleObjectProperty<>(ngayTraSach);
        this.soNgayMuon = new SimpleIntegerProperty(soNgayMuon);
        this.soNgayTraTre = new SimpleIntegerProperty(soNgayTraTre);
        this.tienPhat = new SimpleLongProperty(tienPhat);
    }

    public int getMaPhieuTra() {
        return maPhieuTra.get();
    }

    public SimpleIntegerProperty maPhieuTraProperty() {
        return maPhieuTra;
    }

    public void setMaPhieuTra(int maPhieuTra) {
        this.maPhieuTra.set(maPhieuTra);
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

    public String getTenSachMuon() {
        return tenSachMuon.get();
    }

    public SimpleStringProperty tenSachMuonProperty() {
        return tenSachMuon;
    }

    public void setTenSachMuon(String tenSachMuon) {
        this.tenSachMuon.set(tenSachMuon);
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

    public Date getNgayMuonSach() {
        return ngayMuonSach.get();
    }

    public SimpleObjectProperty<Date> ngayMuonSachProperty() {
        return ngayMuonSach;
    }

    public void setNgayMuonSach(Date ngayMuonSach) {
        this.ngayMuonSach.set(ngayMuonSach);
    }

    public Date getNgayTraSach() {
        return ngayTraSach.get();
    }

    public SimpleObjectProperty<Date> ngayTraSachProperty() {
        return ngayTraSach;
    }

    public void setNgayTraSach(Date ngayTraSach) {
        this.ngayTraSach.set(ngayTraSach);
    }

    public int getSoNgayMuon() {
        return soNgayMuon.get();
    }

    public SimpleIntegerProperty soNgayMuonProperty() {
        return soNgayMuon;
    }

    public void setSoNgayMuon(int soNgayMuon) {
        this.soNgayMuon.set(soNgayMuon);
    }

    public int getSoNgayTraTre() {
        return soNgayTraTre.get();
    }

    public SimpleIntegerProperty soNgayTraTreProperty() {
        return soNgayTraTre;
    }

    public void setSoNgayTraTre(int soNgayTraTre) {
        this.soNgayTraTre.set(soNgayTraTre);
    }

    public long getTienPhat() {
        return tienPhat.get();
    }

    public SimpleLongProperty tienPhatProperty() {
        return tienPhat;
    }

    public void setTienPhat(long tienPhat) {
        this.tienPhat.set(tienPhat);
    }
}
