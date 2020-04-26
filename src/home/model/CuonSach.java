package home.model;

import javafx.beans.property.SimpleIntegerProperty;

public class CuonSach {
    private SimpleIntegerProperty maCuonSach;
    private SimpleIntegerProperty tinhTrang; // 1 true, 0 false

    public CuonSach(){

    }

    public CuonSach(int maCuonSach, int tinhTrang) {
        this.maCuonSach = new SimpleIntegerProperty(maCuonSach) ;
        this.tinhTrang = new SimpleIntegerProperty(tinhTrang);
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

    public int getTinhTrang() {
        return tinhTrang.get();
    }

    public SimpleIntegerProperty tinhTrangProperty() {
        return tinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        this.tinhTrang.set(tinhTrang);
    }
}
