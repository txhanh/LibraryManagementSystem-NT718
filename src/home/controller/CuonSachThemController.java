package home.controller;

import home.Main;
import home.dao.CuonSachDao;
import home.dao.TuaSachDao;
import home.model.CuonSach;
import home.model.DocGia;
import home.model.TuaSach;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class CuonSachThemController implements Initializable {

    Main window = new Main();

    TuaSachDao tuaSachDao = new TuaSachDao();

    public static String trangThai0 = "Chưa mượn";
    public static String trangThai1 = "Đã mượn";


    ObservableList<String> trangThaiList = FXCollections.observableArrayList(trangThai0,trangThai1);


    ObservableList<TuaSach> tenTuaSachList =
            FXCollections.observableArrayList(tuaSachDao.lietKeTuaSach());

    CuonSachDao cuonSachDao = new CuonSachDao();

    @FXML
    private ComboBox<TuaSach> comboboxTenTuaSach;

    @FXML
    private Button btnThemCuonSach;

    @FXML
    private Button btnCancel;

    @FXML
    private ComboBox<String> comboboxTrangThai;

    @FXML
    void cancelAction(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        window.loadAnotherWindow("/home/fxml/CuonSachDanhSach.fxml");
    }

    @FXML
    void themCuonSachAction(ActionEvent event) {
        String tenTuaSach = comboboxTenTuaSach.getValue().getTenSach();
        String trangThai = comboboxTrangThai.getValue();

        CuonSach cuonSach = new CuonSach(tenTuaSach, trangThai);
        boolean flag = cuonSachDao.themCuonSach(cuonSach);
        if (flag) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Thêm cuốn sách thành công");
            alert.showAndWait();
            cancelAction(event);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Không thêm được cuốn sách");
            alert.showAndWait();
            cancelAction(event);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboboxTenTuaSach.setItems(tenTuaSachList);
        comboboxTenTuaSach.setConverter(new StringConverter<TuaSach>() {

            @Override
            public String toString(TuaSach object) {
                return "MS: " + object.getMaTuaSach() + " - " + object.getTenSach();
            }

            @Override
            public TuaSach fromString(String string) {
                return comboboxTenTuaSach.getItems().stream().filter(ap ->
                        ap.getTenSach().equals(string)).findFirst().orElse(null);
            }
        });
        comboboxTenTuaSach.getSelectionModel().selectFirst();


        comboboxTrangThai.setItems(trangThaiList);
        comboboxTrangThai.getSelectionModel().selectFirst();
        comboboxTrangThai.setDisable(true);
    }
}
