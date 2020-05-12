package home.controller;

import home.dao.PhieuMuonSachDao;
import home.model.PhieuMuonSach;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.ResourceBundle;

public class PhieuMuonSachDanhSachController implements Initializable {

    PhieuMuonSachDao phieuMuonSachDao = new PhieuMuonSachDao();
    ObservableList<PhieuMuonSach> dataPhieuMuonSach =
            FXCollections.observableArrayList(phieuMuonSachDao.lietKePhieuMuonSach());

    @FXML
    private GridPane paneDocGia;

    @FXML
    private TextField tfSearchPhieuMuonSach;

    @FXML
    private Button btnThemPhieuMuonSach;

    @FXML
    private Button btnHome;

    @FXML
    private TableView<PhieuMuonSach> tablePhieuMuonSach;

    @FXML
    private TableColumn<PhieuMuonSach, Integer> maPhieuMuonColumn;

    @FXML
    private TableColumn<PhieuMuonSach, Integer> maDocGiaColumn;

    @FXML
    private TableColumn<PhieuMuonSach, String> tenDocGiaColumn;

    @FXML
    private TableColumn<PhieuMuonSach, Integer> maCuonSachColumn;

    @FXML
    private TableColumn<PhieuMuonSach, String> tenTuaSachColumn;

    @FXML
    private TableColumn<PhieuMuonSach, String> tenTheLoaiColumn;

    @FXML
    private TableColumn<PhieuMuonSach, Date> ngayMuonColumn;

    @FXML
    private TableColumn<PhieuMuonSach, Date> ngayDuKienTraColumn;

    @FXML
    void openCapNhatTuaSachAction(ActionEvent event) {

    }

    @FXML
    void openHomeWindow(ActionEvent event) {

    }

    @FXML
    void openThemPhieuMuonSach(ActionEvent event) {

    }

    @FXML
    void xoaTuaSachAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initColumn();
        tablePhieuMuonSach.setItems(dataPhieuMuonSach);
    }

    private void initColumn() {
        maPhieuMuonColumn.setCellValueFactory(new PropertyValueFactory<>("maPhieuMuon"));
        maDocGiaColumn.setCellValueFactory(new PropertyValueFactory<>("maDocGia"));
        tenDocGiaColumn.setCellValueFactory(new PropertyValueFactory<>("tenDocGia"));
        maCuonSachColumn.setCellValueFactory(new PropertyValueFactory<>("maCuonSach"));
        tenTuaSachColumn.setCellValueFactory(new PropertyValueFactory<>("tenTuaSach"));
        tenTheLoaiColumn.setCellValueFactory(new PropertyValueFactory<>("tenTheLoai"));
        ngayMuonColumn.setCellValueFactory(new PropertyValueFactory<>("ngayMuon"));
        ngayDuKienTraColumn.setCellValueFactory(new PropertyValueFactory<>("ngayDuKienTra"));

        ngayMuonColumn.setCellFactory(column -> {
            TableCell<PhieuMuonSach, Date> cell = new TableCell<PhieuMuonSach, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(format.format(item));
                    }
                }
            };

            return cell;
        });

        ngayDuKienTraColumn.setCellFactory(column -> {
            TableCell<PhieuMuonSach, Date> cell = new TableCell<PhieuMuonSach, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(format.format(item));
                    }
                }
            };

            return cell;
        });
    }
}
