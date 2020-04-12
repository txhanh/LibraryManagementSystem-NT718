package home.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import home.Main;
import home.dao.DocGiaDao;
import home.model.DocGia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainGUIController implements Initializable {

    Main window = new Main();

    @FXML
    private Button btnDocGia;

    @FXML
    private Button btnTuaSach;

    @FXML
    private Button btnCuonSach;

    @FXML
    private Button btnPhieuMuon;

    @FXML
    private Button btnPhieuTra;


    @FXML
    private FontAwesomeIconView btnClose;


    @FXML
    private Button btnAddMember;


    @FXML
    public BorderPane borderPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    void closeWindowAction(MouseEvent event) {
        if (event.getSource() == btnClose) {
            System.exit(0);
        }
    }


    @FXML
    void handleClickAction(ActionEvent event) {
        if (event.getSource() == btnDocGia) {

            loadUI("/home/fxml/DocGiaDanhSach.fxml");

        } else if (event.getSource() == btnTuaSach) {

            loadUI("/home/fxml/TuaSachDanhSach.fxml");
        } else if (event.getSource() == btnCuonSach) {

        } else if (event.getSource() == btnPhieuMuon) {
//
        } else if (event.getSource() == btnPhieuTra) {
//
        }


    }

    public void loadUI(String nameUI) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(nameUI));
        } catch (IOException e) {
            e.printStackTrace();
        }
        borderPane.setCenter(root);
    }
}
