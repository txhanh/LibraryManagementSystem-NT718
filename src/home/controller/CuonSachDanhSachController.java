package home.controller;

import home.Main;
import home.model.TuaSach;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CuonSachDanhSachController {

    Main window = new Main();



    @FXML
    private GridPane paneDocGia;

    @FXML
    private TextField tfSearchCuonSach;

    @FXML
    private Button btnThemCuonSach;

    @FXML
    private Button btnHome;

    @FXML
    private TableView<TuaSach> tableCuonSach;

    @FXML
    private TableColumn<TuaSach, Integer> maCuonSachColumn;

    @FXML
    private TableColumn<TuaSach, Integer> maTuaSachColumn;

    @FXML
    private TableColumn<TuaSach, String> tenTuaSachColumn;

    @FXML
    private TableColumn<TuaSach, String> tenTheLoaiColumn;

    @FXML
    private TableColumn<TuaSach, String> tacGiaColumn;

    @FXML
    private TableColumn<TuaSach, Integer> trangThaiColumn;

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

}
