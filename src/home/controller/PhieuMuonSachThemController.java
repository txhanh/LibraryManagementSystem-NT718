package home.controller;

import home.Main;
import home.dao.CuonSachDao;
import home.dao.DocGiaDao;
import home.dao.PhieuMuonSachDao;
import home.dao.TuaSachDao;
import home.model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import javax.print.Doc;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;

import static home.controller.CuonSachThemController.trangThai1;
import static home.controller.PhieuMuonSachDanhSachController.patternDay;

public class PhieuMuonSachThemController implements Initializable {

    Main window = new Main();

    DocGiaDao docGiaDao = new DocGiaDao();
    CuonSachDao cuonSachDao = new CuonSachDao();
    PhieuMuonSachDao phieuMuonSachDao = new PhieuMuonSachDao();

    ObservableList<DocGia> tenDocGiaList =
            FXCollections.observableArrayList(docGiaDao.getAllMember());

    ObservableList<CuonSach> cuonSachList =
            FXCollections.observableArrayList(cuonSachDao.lietKeCuonSach());


    @FXML
    private ComboBox<DocGia> comboboxDocGia;

    @FXML
    private ComboBox<CuonSach> comboboxCuonSach;

    @FXML
    private Button btnThemPhieuMuon;

    @FXML
    private Button btnCancel;

    @FXML
    private DatePicker datepickerNgayMuonSach;

    @FXML
    private DatePicker datepickerNgayDuKienTra;

    LocalDate ngayMuonSachInit;
    LocalDate ngayDuKienTraInit;

    @FXML
    void cancelAction(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        window.loadAnotherWindow("/home/fxml/PhieuMuonSachDanhSach.fxml");
    }

    @FXML
    void themPhieuMuonSachAction(ActionEvent event) {
        int maDocGia = comboboxDocGia.getValue().getMaDocGia();
        int maCuonSach = comboboxCuonSach.getValue().getMaCuonSach();


        if (datepickerNgayMuonSach.getValue() == null || datepickerNgayDuKienTra.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Bạn chưa chọn đủ ngày");
            alert.showAndWait();
            cancelAction(event);
            return;
        }

        java.util.Date ngayMuonSach = java.sql.Date.valueOf(ngayMuonSachInit);
        java.util.Date ngayDuKienTra = java.sql.Date.valueOf(ngayDuKienTraInit);

        PhieuMuonSach phieuMuonSach = new PhieuMuonSach(maDocGia, maCuonSach, ngayMuonSach, ngayDuKienTra);
        boolean flag = phieuMuonSachDao.themPhieuMuonSach(phieuMuonSach);
        if (flag) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Thêm phiếu mượn sách thành công");
            alert.showAndWait();
            cancelAction(event);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Không thêm được phiếu mượn sách");
            alert.showAndWait();
            cancelAction(event);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // show mã độc giả + họ tên độc giả
        comboboxDocGia.setItems(tenDocGiaList);
        comboboxDocGia.setConverter(new StringConverter<DocGia>() {

            @Override
            public String toString(DocGia object) {
                return object.getMaDocGia() + " - " + object.getHoDocGia() + " " + object.getTenDocGia();
            }

            @Override
            public DocGia fromString(String string) {
                return comboboxDocGia.getItems().stream().filter(ap ->
                        ap.getTenDocGia().equals(string)).findFirst().orElse(null);
            }
        });
        comboboxDocGia.getSelectionModel().selectFirst();


        /*
        Code để loại bỏ những cuốn sách đã được mượn khỏi combobox Phiếu mượn sách
         */

//      Khởi tạo biến đếm trong cuonSachList (tính tổng số phần tử trong mảng)
        Iterator<CuonSach> i = cuonSachList.iterator();
        while (i.hasNext()) {
            CuonSach sach = i.next(); // must be called before you can call i.remove()
            if (sach.getTrangThai().equals(trangThai1)) {
                i.remove();
            }
        }

        // show mã cuốn sách + tên tựa sách
        comboboxCuonSach.setItems(cuonSachList);

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
        comboboxCuonSach.getSelectionModel().selectFirst();
        if(cuonSachList.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Hiện tại thư viện không còn cuốn sách nào cả!");
            alert.showAndWait();

            comboboxDocGia.setDisable(true);
            btnThemPhieuMuon.setDisable(true);
            datepickerNgayMuonSach.setDisable(true);
            datepickerNgayDuKienTra.setDisable(true);
            return;
        }

        //Lấy quy định số ngày mượn tối đa
        QuyDinh quyDinh = new QuyDinh();

        //Khởi tạo ngày mượn sách mặc định: now()
        ngayMuonSachInit = LocalDate.now();
        ngayDuKienTraInit = ngayMuonSachInit.plusDays(quyDinh.getSoNgayMuonToiDa());

//        String pattern = "dd - MM - yyyy";
        datepickerNgayMuonSach.setValue(ngayMuonSachInit);

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


        //Ngay Du Kien tra

        datepickerNgayDuKienTra.setValue(ngayDuKienTraInit);

        datepickerNgayDuKienTra.setConverter(new StringConverter<LocalDate>() {
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


        datepickerNgayDuKienTra.setDisable(true);

    }
}
