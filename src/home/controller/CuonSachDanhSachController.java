package home.controller;

import home.Main;
import home.dao.CuonSachDao;
import home.model.CuonSach;
import home.model.TuaSach;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CuonSachDanhSachController implements Initializable {

    Main window = new Main();

    CuonSachDao cuonSachDao = new CuonSachDao();
    ObservableList<CuonSach> data = FXCollections.observableArrayList(cuonSachDao.lietKeCuonSach());

    @FXML
    private GridPane paneDocGia;

    @FXML
    private TextField tfSearchCuonSach;

    @FXML
    private Button btnThemCuonSach;

    @FXML
    private Button btnHome;

    @FXML
    private TableView<CuonSach> tableCuonSach;

    @FXML
    private TableColumn<CuonSach, Integer> maCuonSachColumn;

    @FXML
    private TableColumn<CuonSach, Integer> maTuaSachColumn;

    @FXML
    private TableColumn<CuonSach, String> tenTuaSachColumn;

    @FXML
    private TableColumn<CuonSach, String> tenTheLoaiColumn;

    @FXML
    private TableColumn<CuonSach, String> tacGiaColumn;

    @FXML
    private TableColumn<CuonSach, Integer> trangThaiColumn;

    @FXML
    void openCapNhatCuonSachAction(ActionEvent event) {

    }

    @FXML
    void openHomeWindow(ActionEvent event) {
        window.loadAnotherWindow("/home/fxml/MainGUI.fxml");
        cancelAction(event);
    }

    @FXML
    void openThemCuonSach(ActionEvent event) {
        window.loadAnotherWindow("/home/fxml/CuonSachThem.fxml");
        cancelAction(event);
    }

    @FXML
    void xoaCuonSachAction(ActionEvent event) {

    }

    @FXML
    void cancelAction(ActionEvent event) {
        Stage stage = (Stage) btnThemCuonSach.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initcolumn();
        tableCuonSach.setItems(data);
        tableCuonSach.getSortOrder().add(maCuonSachColumn);
    }

    private void initcolumn() {
        maCuonSachColumn.setCellValueFactory(new PropertyValueFactory<>("maCuonSach"));
        maTuaSachColumn.setCellValueFactory(new PropertyValueFactory<>("maTuaSach"));
        tenTuaSachColumn.setCellValueFactory(new PropertyValueFactory<>("tenTuaSach"));
        tenTheLoaiColumn.setCellValueFactory(new PropertyValueFactory<>("tenTheLoai"));
        tacGiaColumn.setCellValueFactory(new PropertyValueFactory<>("tacGia"));
        trangThaiColumn.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
    }
}
