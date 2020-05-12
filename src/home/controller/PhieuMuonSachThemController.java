package home.controller;

import home.dao.CuonSachDao;
import home.dao.DocGiaDao;
import home.dao.TuaSachDao;
import home.model.CuonSach;
import home.model.DocGia;
import home.model.TuaSach;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import javax.print.Doc;
import java.net.URL;
import java.util.ResourceBundle;

public class PhieuMuonSachThemController implements Initializable {

    DocGiaDao docGiaDao = new DocGiaDao();
    CuonSachDao cuonSachDao = new CuonSachDao();

    ObservableList<DocGia> tenDocGiaList =
            FXCollections.observableArrayList(docGiaDao.getAllMember());

    ObservableList<CuonSach> cuonSachList =
            FXCollections.observableArrayList(cuonSachDao.lietKeCuonSach());

    @FXML
    private ComboBox<DocGia> comboboxDocGia;

    @FXML
    private ComboBox<CuonSach> comboboxCuonSach;

    @FXML
    private Button btnThemPhieuMuon;

    @FXML
    private Button btnCancel;

    @FXML
    private DatePicker datepickerNgayMuonSach;

    @FXML
    private DatePicker datepickerNgayDuKienTra;

    @FXML
    void cancelAction(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void themPhieuMuonSachAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // show mã độc giả + họ tên độc giả
        comboboxDocGia.setItems(tenDocGiaList);
        comboboxDocGia.setConverter(new StringConverter<DocGia>() {

            @Override
            public String toString(DocGia object) {
                return object.getMaDocGia() + " - " + object.getHoDocGia() + " " + object.getTenDocGia();
            }

            @Override
            public DocGia fromString(String string) {
                return comboboxDocGia.getItems().stream().filter(ap ->
                        ap.getTenDocGia().equals(string)).findFirst().orElse(null);
            }
        });
        comboboxDocGia.getSelectionModel().selectFirst();

        // show mã cuốn sách + tên tựa sách
        comboboxCuonSach.setItems(cuonSachList);
        comboboxCuonSach.setConverter(new StringConverter<CuonSach>() {

            @Override
            public String toString(CuonSach object) {
                return object.getMaCuonSach() + " - " + object.getTenTuaSach() + " (MS: " + object.getMaTuaSach() +")";
            }

            @Override
            public CuonSach fromString(String string) {
                return comboboxCuonSach.getItems().stream().filter(ap ->
                        ap.getTenTuaSach().equals(string)).findFirst().orElse(null);
            }
        });
        comboboxCuonSach.getSelectionModel().selectFirst();
    }
}
