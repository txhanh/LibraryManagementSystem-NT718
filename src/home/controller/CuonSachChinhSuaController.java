package home.controller;

import home.Main;
import home.dao.CuonSachDao;
import home.dao.TuaSachDao;
import home.model.CuonSach;
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

public class CuonSachChinhSuaController implements Initializable {

    Main window = new Main();
    TuaSachDao tuaSachDao = new TuaSachDao();
    CuonSachDao cuonSachDao = new CuonSachDao();


    ObservableList<String> tenTuaSachList =
            FXCollections.observableArrayList(tuaSachDao.lietKeTenTuaSach());

    @FXML
    private ComboBox<String> comboboxTenTuaSach;

    @FXML
    private Button btnCapNhatCuonSach;

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
    void capNhatCuonSachAction(ActionEvent event) {
        String tentuasach = comboboxTenTuaSach.getValue();
        int trangthai = trangThaiSpinner.getValue();


        CuonSach cuonSach = new CuonSach(tentuasach,trangthai);

        boolean flag = cuonSachDao.capNhatCuonSach(cuonSach);
        if(flag){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật cuốn sách thành công");
            alert.showAndWait();
            cancelAction(event);
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Không thể cập nhật cuốn sách. Vui lòng kiểm tra lại lỗi !!!");
            alert.showAndWait();
            cancelAction(event);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboboxTenTuaSach.setItems(tenTuaSachList);
        comboboxTenTuaSach.setValue(CuonSachDanhSachController.v_tentuasach);


        trangThaiSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1));
        trangThaiSpinner.getValueFactory().setValue(CuonSachDanhSachController.v_trangthai);
    }
}
