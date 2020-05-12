package home.model;

import javafx.beans.property.SimpleIntegerProperty;

public class QuyDinh {

    private SimpleIntegerProperty soTienPhat;
    private SimpleIntegerProperty soSachMuonToiDa;
    private SimpleIntegerProperty soNgayMuonToiDa;

    public QuyDinh(int soTienPhat, int soSachMuonToiDa, int soNgayMuonToiDa) {
        this.soTienPhat = new SimpleIntegerProperty(soTienPhat);
        this.soSachMuonToiDa = new SimpleIntegerProperty(soSachMuonToiDa);
        this.soNgayMuonToiDa = new SimpleIntegerProperty(soNgayMuonToiDa);
    }

    public int getSoTienPhat() {
        return soTienPhat.get();
    }

    public SimpleIntegerProperty soTienPhatProperty() {
        return soTienPhat;
    }

    public void setSoTienPhat(int soTienPhat) {
        this.soTienPhat.set(soTienPhat);
    }

    public int getSoSachMuonToiDa() {
        return soSachMuonToiDa.get();
    }

    public SimpleIntegerProperty soSachMuonToiDaProperty() {
        return soSachMuonToiDa;
    }

    public void setSoSachMuonToiDa(int soSachMuonToiDa) {
        this.soSachMuonToiDa.set(soSachMuonToiDa);
    }

    public int getSoNgayMuonToiDa() {
        return soNgayMuonToiDa.get();
    }

    public SimpleIntegerProperty soNgayMuonToiDaProperty() {
        return soNgayMuonToiDa;
    }

    public void setSoNgayMuonToiDa(int soNgayMuonToiDa) {
        this.soNgayMuonToiDa.set(soNgayMuonToiDa);
    }
}
