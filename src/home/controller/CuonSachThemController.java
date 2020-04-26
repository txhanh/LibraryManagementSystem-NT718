package home.controller;

import home.Main;
import home.dao.CuonSachDao;
import home.dao.TuaSachDao;
import home.model.CuonSach;
import home.model.TuaSach;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CuonSachThemController implements Initializable {

    Main window = new Main();

    TuaSachDao tuaSachDao = new TuaSachDao();


    ObservableList<String> tenTuaSachList =
            FXCollections.observableArrayList(tuaSachDao.lietKeTenTuaSach());

    CuonSachDao cuonSachDao = new CuonSachDao();

    @FXML
    private ComboBox<String> comboboxTenTuaSach;

    @FXML
    private Button btnThemCuonSach;

    @FXML
    private Button btnCancel;

    @FXML
    private Spinner<Integer> trangThaiSpinner;

    @FXML
    void cancelAction(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        window.loadAnotherWindow("/home/fxml/CuonSachDanhSach.fxml");
    }

    @FXML
    void themCuonSachAction(ActionEvent event) {
        String tenTuaSach = comboboxTenTuaSach.getValue();
        int trangThai = trangThaiSpinner.getValue();

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
        comboboxTenTuaSach.getSelectionModel().selectFirst();
        trangThaiSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1));
        trangThaiSpinner.getValueFactory().setValue(1);
    }
}
