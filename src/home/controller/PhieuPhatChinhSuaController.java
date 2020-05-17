package home.controller;

import home.Main;
import home.dao.CuonSachDao;
import home.dao.DocGiaDao;
import home.dao.PhieuPhatDao;
import home.dao.PhieuTraSachDao;
import home.model.CuonSach;
import home.model.DocGia;
import home.model.PhieuPhat;
import home.model.PhieuTraSach;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class PhieuPhatChinhSuaController implements Initializable {

    Main window = new Main();

//    PhieuPhatDanhSachController phieuPhatDanhSachController = new PhieuPhatDanhSachController();
//    PhieuPhat selectedRow = phieuPhatDanhSachController.getSelectedPhieuPhat();

    PhieuPhatDao phieuPhatDao = new PhieuPhatDao();
    ObservableList<PhieuPhat> danhSachPhieuPhat;

    DocGiaDao docGiaDao = new DocGiaDao();
    ObservableList<DocGia> docGiaList;

    PhieuTraSachDao phieuTraSachDao = new PhieuTraSachDao();
    ObservableList<PhieuTraSach> phieuTraSachList;

    CuonSachDao cuonSachDao = new CuonSachDao();
    ObservableList<CuonSach> cuonSachList;

    @FXML
    private Button btnCapNhatPhieuPhat;

    @FXML
    private Button btnCancel;

    @FXML
    private ComboBox<DocGia> comboboxDocGia;

    @FXML
    private ComboBox<CuonSach> comboboxCuonSach;

    @FXML
    private ComboBox<PhieuTraSach> comboboxMaPhieuTraTre;

    @FXML
    private TextField tfTienPhat;

    @FXML
    private ComboBox<PhieuPhat> comboboxMaPhieuPhat;


    @FXML
    void cancelAction(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        window.loadAnotherWindow("/home/fxml/PhieuPhatDanhSach.fxml");
    }

    @FXML
    void capNhatPhieuPhatAction(ActionEvent event) {
        int maPhieuPhat = comboboxMaPhieuPhat.getValue().getMaPhieuPhat();
        int maDocGia = comboboxDocGia.getValue().getMaDocGia();
        int maPhieuTra = comboboxMaPhieuTraTre.getValue().getMaPhieuTra();
        long tienPhat = Long.parseLong(tfTienPhat.getText());
        int maCuonSach = comboboxCuonSach.getValue().getMaCuonSach();

        PhieuPhat phieuPhat = new PhieuPhat(maPhieuPhat, maDocGia, maPhieuTra, tienPhat, maCuonSach);
        PhieuPhatDao phieuPhatDao = new PhieuPhatDao();
        boolean flag = phieuPhatDao.capNhatPhieuPhat(phieuPhat);
        if(flag){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật phiếu phạt thành công");
            alert.showAndWait();
            cancelAction(event);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Không cập nhật được phiếu phạt");
            alert.showAndWait();
            cancelAction(event);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        danhSachPhieuPhat = FXCollections.observableArrayList(phieuPhatDao.lietKePhieuPhat());
        comboboxMaPhieuPhat.setItems(danhSachPhieuPhat);
        comboboxMaPhieuPhat.setConverter(new StringConverter<PhieuPhat>() {

            @Override
            public String toString(PhieuPhat object) {
                return "Phiếu phạt số: " + object.getMaPhieuPhat() + " - Độc giả: " + object.getTenDocGia();
            }

            @Override
            public PhieuPhat fromString(String string) {
                return comboboxMaPhieuPhat.getItems().stream().filter(ap ->
                        ap.getTenDocGia().equals(string)).findFirst().orElse(null);
            }
        });

        for (PhieuPhat elementPhieuPhat : danhSachPhieuPhat) {
            if (PhieuPhatDanhSachController.getSelectedPhieuPhat().getMaPhieuPhat() == elementPhieuPhat.getMaPhieuPhat()) {
                comboboxMaPhieuPhat.getSelectionModel().select(elementPhieuPhat);
                comboboxMaPhieuPhat.setDisable(true);
                setComboboxPhieuTraSachInfo(PhieuPhatDanhSachController.getSelectedPhieuPhat().getMaPhieuTra());
                setComboboxCuonSachInfo(PhieuPhatDanhSachController.getSelectedPhieuPhat().getMaCuonSach());
                setComboboxDocGiaInfo(PhieuPhatDanhSachController.getSelectedPhieuPhat().getMaDocGia());
                tfTienPhat.setText(String.valueOf(PhieuPhatDanhSachController.getSelectedPhieuPhat().getTienPhat()));
                tfTienPhat.setDisable(true);
                break;
            }
        }
    }

    private void setComboboxDocGiaInfo(int maDocGia) {
        docGiaList = FXCollections.observableArrayList(docGiaDao.getAllMember());
        Iterator<DocGia> i = docGiaList.iterator();
        while (i.hasNext()) {
            DocGia docGia = i.next(); // must be called before you can call i.remove()
            if (maDocGia != docGia.getMaDocGia()) {
                i.remove();
            }
        }

        comboboxDocGia.setConverter(new StringConverter<DocGia>() {

            @Override
            public String toString(DocGia object) {
                return object.getMaDocGia() + " - " + object.getTenDocGia();
            }

            @Override
            public DocGia fromString(String string) {
                return comboboxDocGia.getItems().stream().filter(ap ->
                        ap.getTenDocGia().equals(string)).findFirst().orElse(null);
            }
        });
        comboboxDocGia.setItems(docGiaList);
        comboboxDocGia.getSelectionModel().selectFirst();
    }

    private void setComboboxCuonSachInfo(int masoCuonSach) {
        cuonSachList = FXCollections.observableArrayList(cuonSachDao.lietKeCuonSach());
        Iterator<CuonSach> i = cuonSachList.iterator();
        while (i.hasNext()) {
            CuonSach sach = i.next(); // must be called before you can call i.remove()
            if (masoCuonSach != sach.getMaCuonSach()) {
                i.remove();
            }
        }

        comboboxCuonSach.setConverter(new StringConverter<CuonSach>() {

            @Override
            public String toString(CuonSach object) {
                return object.getMaCuonSach() + " - " + object.getTenTuaSach() + " (MS: " + object.getMaTuaSach() +
                        ")";
            }

            @Override
            public CuonSach fromString(String string) {
                return comboboxCuonSach.getItems().stream().filter(ap ->
                        ap.getTenTuaSach().equals(string)).findFirst().orElse(null);
            }
        });
        comboboxCuonSach.setItems(cuonSachList);
        comboboxCuonSach.getSelectionModel().selectFirst();
    }

    private void setComboboxPhieuTraSachInfo(int maPhieuTra) {
        phieuTraSachList = FXCollections.observableArrayList(phieuTraSachDao.lietKePhieuTraSach());
        Iterator<PhieuTraSach> i = phieuTraSachList.iterator();
        while (i.hasNext()) {
            PhieuTraSach phieuTraSach = i.next(); // must be called before you can call i.remove()
            if (maPhieuTra != phieuTraSach.getMaPhieuTra()) {
                i.remove();
            }
        }

        comboboxMaPhieuTraTre.setConverter(new StringConverter<PhieuTraSach>() {

            @Override
            public String toString(PhieuTraSach object) {
                return "Phiếu trả số: " + object.getMaPhieuTra() + " - " + object.getTenDocGia();
            }

            @Override
            public PhieuTraSach fromString(String string) {
                return comboboxMaPhieuTraTre.getItems().stream().filter(ap ->
                        ap.getTenDocGia().equals(string)).findFirst().orElse(null);
            }
        });
        comboboxMaPhieuTraTre.setItems(phieuTraSachList);
        comboboxMaPhieuTraTre.getSelectionModel().selectFirst();
    }
}
