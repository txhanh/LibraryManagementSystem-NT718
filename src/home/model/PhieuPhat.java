package home.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class PhieuPhat {
    private SimpleIntegerProperty maPhieuPhat;
    private SimpleIntegerProperty maDocGia;
    private SimpleStringProperty tenDocGia;
    private SimpleIntegerProperty maPhieuTra;
    private SimpleStringProperty tenTuaSach;
    private SimpleLongProperty tienPhat;

    public PhieuPhat(int maDocGia, int maPhieuTra, long tienPhat) {
        this.maDocGia = new SimpleIntegerProperty(maDocGia);
        this.maPhieuTra = new SimpleIntegerProperty(maPhieuTra);
        this.tienPhat = new SimpleLongProperty(tienPhat);
    }


    public PhieuPhat(int maDocGia,
                     String tenDocGia, int maPhieuTra,
                     String tenTuaSach, long tienPhat) {
        this.maDocGia = new SimpleIntegerProperty(maDocGia);
        this.tenDocGia = new SimpleStringProperty(tenDocGia);
        this.maPhieuTra = new SimpleIntegerProperty(maPhieuTra);
        this.tenTuaSach = new SimpleStringProperty(tenTuaSach);
        this.tienPhat = new SimpleLongProperty(tienPhat);
    }

    public PhieuPhat(int maPhieuPhat, int maDocGia,
                     String tenDocGia, int maPhieuTra,
                     String tenTuaSach, long tienPhat) {
        this.maPhieuPhat = new SimpleIntegerProperty(maPhieuPhat);
        this.maDocGia = new SimpleIntegerProperty(maDocGia);
        this.tenDocGia = new SimpleStringProperty(tenDocGia);
        this.maPhieuTra = new SimpleIntegerProperty(maPhieuTra);
        this.tenTuaSach = new SimpleStringProperty(tenTuaSach);
        this.tienPhat = new SimpleLongProperty(tienPhat);
    }

    public int getMaPhieuPhat() {
        return maPhieuPhat.get();
    }

    public SimpleIntegerProperty maPhieuPhatProperty() {
        return maPhieuPhat;
    }

    public void setMaPhieuPhat(int maPhieuPhat) {
        this.maPhieuPhat.set(maPhieuPhat);
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

    public String getTenDocGia() {
        return tenDocGia.get();
    }

    public SimpleStringProperty tenDocGiaProperty() {
        return tenDocGia;
    }

    public void setTenDocGia(String tenDocGia) {
        this.tenDocGia.set(tenDocGia);
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

    public String getTenTuaSach() {
        return tenTuaSach.get();
    }

    public SimpleStringProperty tenTuaSachProperty() {
        return tenTuaSach;
    }

    public void setTenTuaSach(String tenTuaSach) {
        this.tenTuaSach.set(tenTuaSach);
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
