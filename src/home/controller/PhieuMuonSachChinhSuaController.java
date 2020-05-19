package home.controller;

import home.Main;
import home.dao.CuonSachDao;
import home.dao.DocGiaDao;
import home.dao.PhieuMuonSachDao;
import home.model.CuonSach;
import home.model.DocGia;
import home.model.PhieuMuonSach;
import home.model.QuyDinh;
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

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;

import static home.controller.CuonSachThemController.trangThai1;
import static home.controller.PhieuMuonSachDanhSachController.patternDay;
import static home.controller.PhieuMuonSachThemController.trangThaiPMS0;
import static home.controller.PhieuMuonSachThemController.trangThaiPMS1;

public class PhieuMuonSachChinhSuaController implements Initializable {

    DocGiaDao docGiaDao = new DocGiaDao();
    CuonSachDao cuonSachDao = new CuonSachDao();
    PhieuMuonSachDao phieuMuonSachDao = new PhieuMuonSachDao();

    ObservableList<DocGia> tenDocGiaList =
            FXCollections.observableArrayList(docGiaDao.getAllMember());

    ObservableList<CuonSach> cuonSachList =
            FXCollections.observableArrayList(cuonSachDao.lietKeCuonSach());



    ObservableList<String> trangThaiPMSArray = FXCollections.observableArrayList(trangThaiPMS0, trangThaiPMS1);

    Main window = new Main();

    @FXML
    private Button btnCapNhatPhieuMuon;

    @FXML
    private Button btnCancel;

    @FXML
    private ComboBox<DocGia> comboboxDocGia;

    @FXML
    private ComboBox<CuonSach> comboboxCuonSach;

    @FXML
    private DatePicker datepickerNgayMuonSach;

    @FXML
    private DatePicker datepickerNgayDuKienTra;

    @FXML
    private ComboBox<String> comboboxTrangThaiPMS;

    @FXML
    void cancelAction(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        window.loadAnotherWindow("/home/fxml/PhieuMuonSachDanhSach.fxml");
    }

    @FXML
    void capNhatPhieuMuonSachAction(ActionEvent event) {
        int maDocGia = comboboxDocGia.getValue().getMaDocGia();
        String tenDocGia = comboboxDocGia.getValue().getTenDocGia();
        int maCuonSach = comboboxCuonSach.getValue().getMaCuonSach();
        String tenTuaSach = comboboxCuonSach.getValue().getTenTuaSach();
        String tenTheLoai = comboboxCuonSach.getValue().getTenTheLoai();
        //convert datepicker ==> Date
        //convert datepick ==> localdate ==> *.getValue()
        java.util.Date ngayMuonSach = java.sql.Date.valueOf(datepickerNgayMuonSach.getValue());
        java.util.Date ngayDuKienTra = java.sql.Date.valueOf(datepickerNgayDuKienTra.getValue());
        String trangThaiPMS = comboboxTrangThaiPMS.getValue();

        PhieuMuonSach phieuMuonSach = new PhieuMuonSach(PhieuMuonSachDanhSachController.v_maPhieuMuon, maDocGia,
                tenDocGia, maCuonSach, tenTuaSach, tenTheLoai, ngayMuonSach, ngayDuKienTra, trangThaiPMS);

        boolean flag = phieuMuonSachDao.capNhatPhieuMuonSach(phieuMuonSach);
        if(flag){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật phiếu mượn sách thành công");
            alert.showAndWait();
            cancelAction(event);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Không thể cập nhật phiếu mượn sách. Vui lòng kiểm tra lại lỗi !!!");
            alert.showAndWait();
            cancelAction(event);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

        // So sánh thông tin độc giả
        // Selected ở TableView với Độc giả Object lấy từ ObservableList tenDocGiaList phía trên
        DocGia selectedDocGia = null;
        for (DocGia docGiaObject : tenDocGiaList) {
            if (PhieuMuonSachDanhSachController.v_maDocGia == docGiaObject.getMaDocGia()) {
                int id = docGiaObject.getMaDocGia();
                String ho = docGiaObject.getHoDocGia();
                String ten = docGiaObject.getTenDocGia();
                String sdt = docGiaObject.getSdt();
                String email = docGiaObject.getEmail();
                selectedDocGia = new DocGia(id, ho, ten, sdt, email);
            }
        }
        comboboxDocGia.setValue(selectedDocGia);




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

        // So sánh thông tin độc giả
        // Selected ở TableView với Độc giả Object lấy từ ObservableList tenDocGiaList phía trên
        CuonSach selectedCuonSach = null;
        for (CuonSach cuonSachObject : cuonSachList) {
            if (PhieuMuonSachDanhSachController.v_maCuonSach == cuonSachObject.getMaCuonSach()) {

                int macuonsach = cuonSachObject.getMaCuonSach();
                int matuasach = cuonSachObject.getMaTuaSach();
                String tenTuaSach = cuonSachObject.getTenTuaSach();
                String theLoai = cuonSachObject.getTenTheLoai();
                String tacGia = cuonSachObject.getTacGia();
                String trangThai = cuonSachObject.getTrangThai();

                selectedCuonSach = new CuonSach(macuonsach, matuasach, tenTuaSach, theLoai, tacGia, trangThai);
            }
        }
        comboboxCuonSach.setValue(selectedCuonSach);

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
        if(cuonSachList.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Hiện tại thư viện không còn cuốn sách nào cả!");
            alert.showAndWait();
//            comboboxCuonSach.setValue(null);
            comboboxDocGia.setDisable(true);
            btnCapNhatPhieuMuon.setDisable(true);
            datepickerNgayMuonSach.setDisable(true);
            datepickerNgayDuKienTra.setDisable(true);
            return;
        }

        // Lấy Date từ Phiếu Mượn Sách danh sách Controller qua
        // Ép về kiểu LocalDate để thêm vào lại DatePicker
        LocalDate ngayMuonSach = new java.sql.Date(PhieuMuonSachDanhSachController.v_ngayMuon.getTime()).toLocalDate();
        LocalDate ngayDuKienTra =
                new java.sql.Date(PhieuMuonSachDanhSachController.v_ngayDuKienTra.getTime()).toLocalDate();

        QuyDinh quyDinh = new QuyDinh();

        datepickerNgayMuonSach.setValue(ngayMuonSach);
        //Format date picker có dạng "dd-MM-yyyy";
//        String pattern = "dd - MM - yyyy";

        //format về kiểu dd/MM/yyyy
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

        datepickerNgayDuKienTra.setValue(ngayDuKienTra);

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


        // add Listener kiểm tra lỗi khi vừa chọn xong ngày mượn sách
        // nếu phù hợp thì tự tăng 5 ngày để tạo thành ngày dự kiến trả sách
        datepickerNgayMuonSach.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue,
                                LocalDate newValue) {
                if (datepickerNgayMuonSach.getValue().isBefore(LocalDate.now())) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("Ngày mượn sách không được TRƯỚC ngày hôm nay");
                    alert.showAndWait();
                    return;
                }

                datepickerNgayDuKienTra.setValue(newValue.plusDays(quyDinh.getSoNgayMuonToiDa()));
            }
        });
        datepickerNgayDuKienTra.setDisable(true);


        //set giá trị cho combobox trạng thái phiếu mượn sách
        comboboxTrangThaiPMS.setItems(trangThaiPMSArray);
        comboboxTrangThaiPMS.getSelectionModel().selectFirst();
    }
}
