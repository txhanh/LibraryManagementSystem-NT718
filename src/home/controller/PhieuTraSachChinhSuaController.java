package home.controller;

import home.Main;
import home.dao.CuonSachDao;
import home.dao.DocGiaDao;
import home.dao.PhieuMuonSachDao;
import home.dao.PhieuTraSachDao;
import home.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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

    PhieuMuonSachDao phieuMuonSachDao = new PhieuMuonSachDao();
    ObservableList<PhieuMuonSach> danhSachPhieuMuonSach;

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
    private ComboBox<PhieuMuonSach> comboboxMaPhieuMuon;

    @FXML
    private ComboBox<PhieuTraSach> comboboxMaPhieuTra;

    @FXML
    private TextField tfSoNgayMuon;

    @FXML
    private TextField tfSoNgayTraTre;

    @FXML
    private TextField tfTienPhat;

    int soNgayMuon;
    int soNgayTraTre;
    long soTienPhat;


    @FXML
    void cancelAction(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        window.loadAnotherWindow("/home/fxml/PhieuTraSachDanhSach.fxml");
    }

    @FXML
    void capNhatPhieuTraSachAction(ActionEvent event) {
        int maPhieuTra = comboboxMaPhieuTra.getValue().getMaPhieuTra();
        int maPhieuMuon = comboboxMaPhieuMuon.getValue().getMaPhieuMuon();
        int maDocGia = comboboxDocGia.getValue().getMaDocGia();
        int maCuonSach = comboboxCuonSach.getValue().getMaCuonSach();
        java.util.Date ngayTraSach = java.sql.Date.valueOf(datepickerNgayTraSach.getValue());
        soNgayMuon = Integer.parseInt(tfSoNgayMuon.getText());
        soNgayTraTre = Integer.parseInt(tfSoNgayTraTre.getText());
        soTienPhat = Long.parseLong(tfTienPhat.getText());

        PhieuTraSach phieuTraSach = new PhieuTraSach(maPhieuTra, maPhieuMuon, maDocGia, maCuonSach, ngayTraSach,
                soNgayMuon,
                soNgayTraTre, soTienPhat);

        boolean flag = phieuTraSachDao.capNhatPhieuTraSach(phieuTraSach);

        if (flag) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật phiếu trả sách thành công");
            alert.showAndWait();
            cancelAction(event);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Không cập nhật được phiếu trả sách");
            alert.showAndWait();
            cancelAction(event);
        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        danhSachPhieuTraSach = FXCollections.observableArrayList(phieuTraSachDao.lietKePhieuTraSach());
        comboboxMaPhieuTra.setItems(danhSachPhieuTraSach);
        comboboxMaPhieuTra.setConverter(new StringConverter<PhieuTraSach>() {

            @Override
            public String toString(PhieuTraSach object) {
                return "Phiếu trả sách số: " + object.getMaPhieuTra() + " - Độc giả: " + object.getTenDocGia();
            }

            @Override
            public PhieuTraSach fromString(String string) {
                return comboboxMaPhieuTra.getItems().stream().filter(ap ->
                        ap.getTenDocGia().equals(string)).findFirst().orElse(null);
            }
        });

        // Tìm mã phiếu mượn sách tương ứng đc truyền từ bên kia vào

        for (PhieuTraSach elementPhieuTraSach : danhSachPhieuTraSach) {
            if (PhieuTraSachDanhSachController.getSelectedForUpdate().getMaPhieuTra() == elementPhieuTraSach.getMaPhieuTra()) {
                comboboxMaPhieuTra.getSelectionModel().select(elementPhieuTraSach);
                comboboxMaPhieuTra.setDisable(true);
                setComboboxPhieuMuonSachInfo(PhieuTraSachDanhSachController.getSelectedForUpdate().getMaPhieuMuon());
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

        /*
         listener cho ngày trả sách
         kiểm tra nếu ngày trả sách trước ngày hôm nay thì báo lỗi
         */
        datepickerNgayTraSach.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
                                LocalDate newValue) {
                if (datepickerNgayTraSach.getValue().isBefore(LocalDate.now())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("Ngày trả sách không được TRƯỚC ngày hôm nay");
                    alert.showAndWait();
                    return;
                }


                // Báo lỗi nếu chọn ngày trả trước ngày hôm nay

                if (datepickerNgayTraSach.getValue().isBefore(LocalDate.now())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("Ngày trả sách không được TRƯỚC ngày hôm nay");
                    alert.showAndWait();
                    return;
                }

                // tính số ngày mượn sách, và set giá trị cho text field
                LocalDate localDateNgayMuonSach = datepickerNgayMuonSach.getValue();
                LocalDate localDateNgayTraSach = datepickerNgayTraSach.getValue();

                Period period = Period.between(localDateNgayMuonSach, localDateNgayTraSach);
                soNgayMuon = period.getDays();
                tfSoNgayMuon.setText(String.valueOf(soNgayMuon));

                // tính số ngày trả trễ;
                if (soNgayMuon > quyDinh.getSoNgayMuonToiDa()) {
                    soNgayTraTre = soNgayMuon - quyDinh.getSoNgayMuonToiDa();
                    tfSoNgayTraTre.setText(String.valueOf(soNgayTraTre));

                    soTienPhat = soNgayTraTre * quyDinh.getSoTienPhat();
                    tfTienPhat.setText(String.valueOf(soTienPhat));
                } else {
                    tfSoNgayTraTre.setText("0");
                    tfTienPhat.setText("0");
                }

            }
        });

    }

    private void setComboboxPhieuMuonSachInfo(int maPhieuMuon) {
        danhSachPhieuMuonSach = FXCollections.observableArrayList(phieuMuonSachDao.lietKePhieuMuonSach());
        Iterator<PhieuMuonSach> i = danhSachPhieuMuonSach.iterator();
        while (i.hasNext()) {
            PhieuMuonSach sach = i.next(); // must be called before you can call i.remove()
            if (maPhieuMuon != sach.getMaPhieuMuon()) {
                i.remove();
            }
        }

        comboboxMaPhieuMuon.setConverter(new StringConverter<PhieuMuonSach>() {

            @Override
            public String toString(PhieuMuonSach object) {
                return "Phiếu mượn sách số: " + object.getMaPhieuMuon();
            }

            @Override
            public PhieuMuonSach fromString(String string) {
                return comboboxMaPhieuMuon.getItems().stream().filter(ap ->
                        ap.getTenTuaSach().equals(string)).findFirst().orElse(null);
            }
        });
        comboboxMaPhieuMuon.setItems(danhSachPhieuMuonSach);
        comboboxMaPhieuMuon.getSelectionModel().selectFirst();
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


