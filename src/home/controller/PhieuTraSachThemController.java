package home.controller;

import home.Main;
import home.dao.CuonSachDao;
import home.dao.DocGiaDao;
import home.dao.PhieuMuonSachDao;
import home.dao.PhieuTraSachDao;
import home.model.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;

import static home.controller.CuonSachThemController.trangThai1;
import static home.controller.PhieuMuonSachDanhSachController.patternDay;
import static home.controller.PhieuMuonSachThemController.trangThaiPMS1;

public class PhieuTraSachThemController implements Initializable {



    DocGiaDao docGiaDao = new DocGiaDao();
    ObservableList<DocGia> docGiaList;


    CuonSachDao cuonSachDao = new CuonSachDao();
    ObservableList<CuonSach> cuonSachList;


    PhieuMuonSachDao phieuMuonSachDao = new PhieuMuonSachDao();
    ObservableList<PhieuMuonSach> phieuMuonSachList =
            FXCollections.observableArrayList(phieuMuonSachDao.lietKePhieuMuonSach());

    QuyDinh quyDinh = new QuyDinh();
    PhieuTraSachDao phieuTraSachDao = new PhieuTraSachDao();
    Main window = new Main();

    @FXML
    private Button btnThemPhieuTra;

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
    private TextField tfSoNgayMuon;

    @FXML
    private TextField tfSoNgayTraTre;

    @FXML
    private TextField tfTienPhat;

    @FXML
    void cancelAction(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        window.loadAnotherWindow("/home/fxml/PhieuTraSachDanhSach.fxml");
    }

    @FXML
    void themPhieuTraSachAction(ActionEvent event) {
        int maPhieuMuon = comboboxMaPhieuMuon.getValue().getMaPhieuMuon();
        int maDocGia = comboboxDocGia.getValue().getMaDocGia();
        int maCuonSach = comboboxCuonSach.getValue().getMaCuonSach();
        java.util.Date ngayTraSach = java.sql.Date.valueOf(datepickerNgayTraSach.getValue());
        int soNgayMuon = Integer.parseInt(tfSoNgayMuon.getText());
        int soNgayTraTre = Integer.parseInt(tfSoNgayTraTre.getText());
        long tienPhat = Long.parseLong(tfTienPhat.getText());

        PhieuTraSach phieuTraSach = new PhieuTraSach(maPhieuMuon, maDocGia, maCuonSach, ngayTraSach, soNgayMuon,
                soNgayTraTre, tienPhat);

        boolean flag = phieuTraSachDao.themPhieuTraSach(phieuTraSach);

        if (flag) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Thêm phiếu trả sách thành công");
            alert.showAndWait();
            cancelAction(event);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Không thêm được phiếu trả sách");
            alert.showAndWait();
            cancelAction(event);
        }
    }

    // Xử lý code auto show content khi combobox mã phiếu mượn được chọn
    @FXML
    void autoShowContent(ActionEvent event) {
        int masoPhieuMuonTemp = comboboxMaPhieuMuon.getValue().getMaPhieuMuon();
        for (PhieuMuonSach elementPhieuMuon : phieuMuonSachList) {
            if (masoPhieuMuonTemp == elementPhieuMuon.getMaPhieuMuon()) {
                setComboboxCuonSachInfo(elementPhieuMuon.getMaCuonSach());
                setComboboxDocGiaInfo(elementPhieuMuon.getMaDocGia());
                setNgayMuon(elementPhieuMuon.getNgayMuon());
                setNgayTra();
                int soNgayMuon = setSoNgayMuon(elementPhieuMuon.getNgayMuon());
                if (soNgayMuon > quyDinh.getSoNgayMuonToiDa()) {
                    int soNgayTraTre = tinhSoNgayTraTre(soNgayMuon);
                    tinhTienPhat(soNgayTraTre);
                } else {
                    tfSoNgayTraTre.setText("0");
                    tfTienPhat.setText("0");

                    tfSoNgayTraTre.setDisable(true);
                    tfTienPhat.setDisable(true);
                }
            }
        }
    }

    private void tinhTienPhat(int soNgayTraTre) {
        long tienPhat = soNgayTraTre * quyDinh.getSoTienPhat();
        tfTienPhat.setText(String.valueOf(tienPhat));
        tfTienPhat.setDisable(true);
    }

    private int tinhSoNgayTraTre(int soNgayMuon) {
        int soNgayTraTre = soNgayMuon - quyDinh.getSoNgayMuonToiDa();
        tfSoNgayTraTre.setText(String.valueOf(soNgayTraTre));
        tfSoNgayTraTre.setDisable(true);
        return soNgayTraTre;
    }

    private int setSoNgayMuon(Date ngayMuon) {
        LocalDate localDateNgayMuon = new java.sql.Date(ngayMuon.getTime()).toLocalDate();
        LocalDate ngayTra = LocalDate.now();
        Period period = Period.between(localDateNgayMuon, ngayTra);
        Integer soNgayMuon = period.getDays();
        tfSoNgayMuon.setText(String.valueOf(soNgayMuon));
        tfSoNgayMuon.setDisable(true);
        return soNgayMuon;
    }

    private void setNgayTra() {
        // Lấy thời gian bây giờ
        LocalDate ngayTraSach = LocalDate.now();
        // set giá trị cho DatePicker ngày trả sách
        datepickerNgayTraSach.setValue(ngayTraSach);
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
        datepickerNgayTraSach.setDisable(true);
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
                return object.getMaCuonSach() + " - " + object.getTenTuaSach() + " (MS: " + object.getMaTuaSach() + ")";
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
        Code để loại bỏ những mã phiếu mượn đã được trả khỏi combobox mã phiếu mượn trong phần THÊM PHIẾU TRẢ SÁCH
        */

//      Khởi tạo biến đếm trong cuonSachList (tính tổng số phần tử trong mảng)
        Iterator<PhieuMuonSach> i = phieuMuonSachList.iterator();
        while (i.hasNext()) {
            PhieuMuonSach phieuMuonSach = i.next(); // must be called before you can call i.remove()
            if (phieuMuonSach.getTrangThaiPMS().equals(trangThaiPMS1)) {
                i.remove();
            }
        }
        comboboxMaPhieuMuon.setItems(phieuMuonSachList);
        comboboxMaPhieuMuon.setConverter(new StringConverter<PhieuMuonSach>() {

            @Override
            public String toString(PhieuMuonSach object) {
                return "Phiếu mượn số: " + object.getMaPhieuMuon() + " - Độc giả: " + object.getTenDocGia();
            }

            @Override
            public PhieuMuonSach fromString(String string) {
                return comboboxMaPhieuMuon.getItems().stream().filter(ap ->
                        ap.getTenDocGia().equals(string)).findFirst().orElse(null);
            }
        });
//        comboboxMaPhieuMuon.getSelectionModel().selectFirst();
    }
}
