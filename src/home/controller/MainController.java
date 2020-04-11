package home.controller;

import home.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainController {

    Main window = new Main();

    @FXML
    private Button btnHome;

    @FXML
    private Button btnDocGia;

    @FXML
    private Button btnTuaSach;

    @FXML
    private Button btnCuonSach;

    @FXML
    private Button btnPhieuTra;

    @FXML
    private Button btnPhieuMuon;

    @FXML
    private BorderPane borderPane;


    @FXML
    void openHomeAction(ActionEvent event) {
        loadAnotherWindow("/com/javafx/lib/fxml/Home.fxml", "Trang chủ");
    }

    @FXML
    void quanLiDocGia(ActionEvent event) {
        loadAnotherWindow("/com/javafx/lib/fxml/DocGiaDanhSach.fxml", "Danh sách độc giả");
    }

    @FXML
    void quanLiTuaSach(ActionEvent event) {
loadAnotherWindow("/com/javafx/lib/fxml/TuaSachDanhSach.fxml", "Danh sách Tựa sách");
    }

    private void loadAnotherWindow(String location, String title) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
//            stage.setScene(new Scene(parent));
//            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        borderPane.setCenter(parent);
    }

}
