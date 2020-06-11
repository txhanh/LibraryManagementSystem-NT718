package home.controller;

import home.Main;
import home.dao.TuaSachDao;
import home.model.TuaSach;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TuaSachChinhSuaController implements Initializable {

    TuaSachDao tuaSachDao = new TuaSachDao();
    Main window = new Main();

    ObservableList<String> TheLoaiList = FXCollections.observableArrayList("Giáo trình",
            "Tài liệu tham khảo", "Luận văn", "Tạp chí Khoa Học", "Khác");
    ObservableList<String> NXBList = FXCollections.observableArrayList("ĐHQG TPHCM", "Trẻ",
            "Giáo dục", "ĐHQG Hà Nội", "Tổng hợp TPHCM", "Kim Đồng",
            "Nước Ngoài", "Chính trị Quốc gia");

    @FXML
    private TextField tfTenTuaSach;

    @FXML
    private ComboBox<String> comboboxTheLoai;

    @FXML
    private TextField tfTacGia;

    @FXML
    private ComboBox<String> comboboxNXB;

    @FXML
    private Spinner<Integer> soLuongSpinner;

    @FXML
    private Button btnCapNhatTuaSach;

    @FXML
    private Button btnCancel;

    @FXML
    void cancelAction(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        window.loadAnotherWindow("/home/fxml/TuaSachDanhSach.fxml");
    }

    @FXML
    void capNhatTuaSachAction(ActionEvent event) {
        String tentuasach = tfTenTuaSach.getText();
        String theloai = comboboxTheLoai.getValue();
        String tacgia = tfTacGia.getText();
        String nxb = comboboxNXB.getValue();
        int soluong = soLuongSpinner.getValue();

        if (tentuasach.isEmpty() || theloai.isEmpty() || tacgia.isEmpty()
                || nxb.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Bạn phải nhập đầy đủ thông tin!!!");
            alert.showAndWait();
            return;
        }

        TuaSach tuaSach = new TuaSach(TuaSachDanhSachController.v_matuasach,
                tentuasach,theloai,tacgia,nxb,soluong);

        boolean flag = tuaSachDao.capNhatTuaSach(tuaSach);
        if(flag){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật tựa sách thành công");
            alert.showAndWait();
            cancelAction(event);
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Không thể cập nhật tựa sách. Vui lòng kiểm tra lại lỗi !!!");
            alert.showAndWait();
            cancelAction(event);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboboxTheLoai.setItems(TheLoaiList);

        comboboxNXB.setItems(NXBList);

        soLuongSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20));

        tfTenTuaSach.setText(TuaSachDanhSachController.v_tentuasach);
        comboboxTheLoai.setValue(TuaSachDanhSachController.v_theloai);
        tfTacGia.setText(TuaSachDanhSachController.v_tacgia);
        comboboxNXB.setValue(TuaSachDanhSachController.v_nxb);
        soLuongSpinner.getValueFactory().setValue(TuaSachDanhSachController.v_soluong);
        soLuongSpinner.setDisable(true);
    }
}
