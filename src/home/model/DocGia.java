package home.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DocGia {
    private SimpleIntegerProperty maDocGia;
    private SimpleStringProperty hoDocGia;
    private SimpleStringProperty tenDocGia;
    private SimpleStringProperty sdt;
    private SimpleStringProperty email;

    public DocGia(){

    }

    public DocGia(int maDocGia, String hoDocGia, String tenDocGia, String sdt, String email) {
        this.maDocGia = new SimpleIntegerProperty(maDocGia);
        this.hoDocGia = new SimpleStringProperty(hoDocGia);
        this.tenDocGia = new SimpleStringProperty(tenDocGia) ;
        this.sdt =  new SimpleStringProperty(sdt);
        this.email = new SimpleStringProperty(email);
    }

    public DocGia(String hoDocGia, String tenDocGia, String sdt, String email) {
        this.hoDocGia = new SimpleStringProperty(hoDocGia);
        this.tenDocGia = new SimpleStringProperty(tenDocGia) ;
        this.sdt =  new SimpleStringProperty(sdt);
        this.email = new SimpleStringProperty(email);
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

    public String getHoDocGia() {
        return hoDocGia.get();
    }

    public SimpleStringProperty hoDocGiaProperty() {
        return hoDocGia;
    }

    public void setHoDocGia(String hoDocGia) {
        this.hoDocGia.set(hoDocGia);
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

    public String getSdt() {
        return sdt.get();
    }

    public SimpleStringProperty sdtProperty() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt.set(sdt);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
}
