package home.controller;

import home.Main;
import home.dao.CuonSachDao;
import home.dao.DocGiaDao;
import home.dao.PhieuTraSachDao;
import home.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;


import static home.controller.PhieuMuonSachDanhSachController.patternDay;
//import static home.controller.PhieuTraSachDanhSachController.v_maPhieuTraSach;

public class PhieuTraSachChinhSuaController implements Initializable {


    DocGiaDao docGiaDao = new DocGiaDao();
    ObservableList<DocGia> docGiaList;

    CuonSachDao cuonSachDao = new CuonSachDao();
    ObservableList<CuonSach> cuonSachList;

    QuyDinh quyDinh = new QuyDinh();

    Main window = new Main();

    CuonSachDao cuonSacDao = new CuonSachDao();
    ObservableList<CuonSach> danhSachCuonSach;

    PhieuTraSachDao phieuTraSachDao = new PhieuTraSachDao();
    ObservableList<PhieuTraSach> danhSachPhieuTraSach;

    @FXML
    private Button btnCapNhatPhieuTra;

    @FXML
    private Button btnCancel;

    @FXML
    private ComboBox<DocGia> comboboxDocGia;

    @FXML
    private ComboBox<CuonSach> comboboxCuonSach;

    @FXML
    private DatePicker datepickerNgayMuonSach;

    @FXML
    private DatePicker datepickerNgayTraSach;

    @FXML
    private ComboBox<PhieuTraSach> comboboxMaPhieuMuon;

    @FXML
    private TextField tfSoNgayMuon;

    @FXML
    private TextField tfSoNgayTraTre;

    @FXML
    private TextField tfTienPhat;

    @FXML
    void autoShowContent(ActionEvent event) {

    }


    @FXML
    void cancelAction(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        window.loadAnotherWindow("/home/fxml/PhieuTraSachDanhSach.fxml");
    }

    @FXML
    void capNhatPhieuTraSachAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        danhSachPhieuTraSach = FXCollections.observableArrayList(phieuTraSachDao.lietKePhieuTraSach());
//        danhSachCuonSach = FXCollections.observableArrayList(cuonSacDao.lietKeCuonSach());
        comboboxMaPhieuMuon.setItems(danhSachPhieuTraSach);
        comboboxMaPhieuMuon.setConverter(new StringConverter<PhieuTraSach>() {

            @Override
            public String toString(PhieuTraSach object) {
                return "Phiếu mượn số: " + object.getMaPhieuMuon();
            }

            @Override
            public PhieuTraSach fromString(String string) {
                return comboboxMaPhieuMuon.getItems().stream().filter(ap ->
                        ap.getTenDocGia().equals(string)).findFirst().orElse(null);
            }
        });

        // Tìm mã phiếu mượn sách tương ứng đc truyền từ bên kia vào

        for (PhieuTraSach elementPhieuTraSach : danhSachPhieuTraSach) {
            if (PhieuTraSachDanhSachController.getSelectedForUpdate().getMaPhieuTra() == elementPhieuTraSach.getMaPhieuTra()) {
                comboboxMaPhieuMuon.getSelectionModel().select(elementPhieuTraSach);
                comboboxMaPhieuMuon.setDisable(true);
                setComboboxCuonSachInfo(PhieuTraSachDanhSachController.getSelectedForUpdate().getMaCuonSach());
                setComboboxDocGiaInfo(PhieuTraSachDanhSachController.getSelectedForUpdate().getMaDocGia());
                setNgayMuon(PhieuTraSachDanhSachController.getSelectedForUpdate().getNgayMuonSach());
                setNgayTra(PhieuTraSachDanhSachController.getSelectedForUpdate().getNgayTraSach());
                tfSoNgayMuon.setText(String.valueOf(PhieuTraSachDanhSachController.getSelectedForUpdate().getSoNgayMuon()));
                tfSoNgayTraTre.setText(String.valueOf(PhieuTraSachDanhSachController.getSelectedForUpdate().getSoNgayTraTre()));
                tfTienPhat.setText(String.valueOf(PhieuTraSachDanhSachController.getSelectedForUpdate().getTienPhat()));
                tfSoNgayMuon.setDisable(true);
                tfSoNgayTraTre.setDisable(true);
                tfTienPhat.setDisable(true);
                break;
            }
        }


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

    private void setNgayMuon(Date ngayMuon) {
        // convert Date ==> LocalDate
        LocalDate localDateNgayMuon = new java.sql.Date(ngayMuon.getTime()).toLocalDate();
        // set value cho datePickerNgayMuonSach
        datepickerNgayMuonSach.setValue(localDateNgayMuon);
        // convert ve custom format dd - MM - yyyy
        datepickerNgayMuonSach.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return DateTimeFormatter.ofPattern(patternDay).format(date);
                }
                return "";
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, DateTimeFormatter.ofPattern(patternDay));
                }
                return null;
            }
        });
        datepickerNgayMuonSach.setDisable(true);
    }

    private void setNgayTra(Date ngayTraOld) {
        // convert Date ==> LocalDate
        LocalDate localDateNgayTraOld = new java.sql.Date(ngayTraOld.getTime()).toLocalDate();
        // set value cho datePickerNgayMuonSach
        datepickerNgayTraSach.setValue(localDateNgayTraOld);
        // convert datePicker về custom format dd - MM - yyyy
        datepickerNgayTraSach.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return DateTimeFormatter.ofPattern(patternDay).format(date);
                }
                return "";
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, DateTimeFormatter.ofPattern(patternDay));
                }
                return null;
            }
        });

    }


}

