package home.controller;

import home.Main;
import home.dao.DocGiaDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import home.model.DocGia;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DocGiaChinhSuaController implements Initializable {

    Main window = new Main();

    @FXML
    private TextField tfHoDocGia;

    @FXML
    private TextField tfTenDocGia;

    @FXML
    private TextField tfSDT;

    @FXML
    private TextField tfEmail;

    @FXML
    private Button btnEditMember;

    @FXML
    private Button btnCancel;

    @FXML
    void cancelAction(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        window.loadAnotherWindow("/home/fxml/DocGiaDanhSach.fxml");
    }

    @FXML
    void editMemberAction(ActionEvent event) {


        String ho = tfHoDocGia.getText();
        String ten = tfTenDocGia.getText();
        String sdt = tfSDT.getText();
        String email = tfEmail.getText();
        int madg = DocGiaDanhSachController.getSelectedMemberForUpdate().getMaDocGia();

        Alert alert;
        if (ho.isEmpty() || ten.isEmpty() || sdt.isEmpty() || email.isEmpty()) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Bạn phải nhập đầy đủ thông tin");
            alert.showAndWait();
            return;
        }

        DocGia member = new DocGia(madg, ho, ten, sdt, email);
        DocGiaDao memberDao = new DocGiaDao();

        boolean flag = memberDao.updateMember(member);
        if (flag) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật dữ liệu thành công");
            alert.showAndWait();
            cancelAction(event);

        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật dữ liệu thất bại!!!");
            alert.showAndWait();
            cancelAction(event);

        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DocGia selectedDocGia = DocGiaDanhSachController.getSelectedMemberForUpdate();

        tfHoDocGia.setText(selectedDocGia.getHoDocGia());
        tfTenDocGia.setText(selectedDocGia.getTenDocGia());
        tfSDT.setText(selectedDocGia.getSdt());
        tfEmail.setText(selectedDocGia.getEmail());

    }
}
