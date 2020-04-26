package home.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TuaSach {
    private SimpleIntegerProperty maTuaSach;
    private SimpleStringProperty tenSach;
    private SimpleStringProperty theLoai;
    private SimpleStringProperty tacGia;
    private SimpleStringProperty NXB;
    private SimpleIntegerProperty soLuong;

    public TuaSach(){

    }


    public TuaSach( String tenSach, String theLoai, String tacGia, String NXB, int soLuong) {
        this.tenSach = new SimpleStringProperty(tenSach);
        this.theLoai = new SimpleStringProperty(theLoai);
        this.tacGia = new SimpleStringProperty(tacGia);
        this.NXB = new SimpleStringProperty(NXB);
        this.soLuong = new SimpleIntegerProperty(soLuong);
    }

    public TuaSach(int maTuaSach, String tenSach, String theLoai, String tacGia, String NXB, int soLuong) {
        this.maTuaSach = new SimpleIntegerProperty(maTuaSach);
        this.tenSach = new SimpleStringProperty(tenSach);
        this.theLoai = new SimpleStringProperty(theLoai);
        this.tacGia = new SimpleStringProperty(tacGia);
        this.NXB = new SimpleStringProperty(NXB);
        this.soLuong = new SimpleIntegerProperty(soLuong);
    }
    
    

    public int getMaTuaSach() {
        return maTuaSach.get();
    }

    public SimpleIntegerProperty maTuaSachProperty() {
        return maTuaSach;
    }

    public void setMaTuaSach(int maTuaSach) {
        this.maTuaSach.set(maTuaSach);
    }

    public String getTenSach() {
        return tenSach.get();
    }

    public SimpleStringProperty tenSachProperty() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach.set(tenSach);
    }

    public String getTheLoai() {
        return theLoai.get();
    }

    public SimpleStringProperty theLoaiProperty() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai.set(theLoai);
    }

    public String getTacGia() {
        return tacGia.get();
    }

    public SimpleStringProperty tacGiaProperty() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia.set(tacGia);
    }

    public String getNXB() {
        return NXB.get();
    }

    public SimpleStringProperty NXBProperty() {
        return NXB;
    }

    public void setNXB(String NXB) {
        this.NXB.set(NXB);
    }

    public int getSoLuong() {
        return soLuong.get();
    }

    public SimpleIntegerProperty soLuongProperty() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong.set(soLuong);
    }
}
