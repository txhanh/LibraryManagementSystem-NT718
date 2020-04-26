package home.controller;

import home.Main;
import home.dao.TuaSachDao;
import home.model.TuaSach;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CuonSachThemController implements Initializable {

    Main window = new Main();

    TuaSachDao tuaSachDao = new TuaSachDao();

    ObservableList<String> tenTuaSachList =
            FXCollections.observableArrayList(tuaSachDao.lietKeTenTuaSach());


    @FXML
    private ComboBox<String> comboboxTenTuaSach;

    @FXML
    private Button btnThemCuonSach;

    @FXML
    private Button btnCancel;

    @FXML
    private Spinner<Integer> tinhTrangSpinner;

    @FXML
    void cancelAction(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        window.loadAnotherWindow("/home/fxml/CuonSachDanhSach.fxml");
    }

    @FXML
    void themCuonSachAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboboxTenTuaSach.setItems(tenTuaSachList);
        comboboxTenTuaSach.getSelectionModel().selectFirst();
        tinhTrangSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1));
        tinhTrangSpinner.getValueFactory().setValue(1);
        tinhTrangSpinner.setEditable(true);
    }
}
