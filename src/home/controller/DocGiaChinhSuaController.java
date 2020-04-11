package home.controller;

import home.Main;
import home.dao.DocGiaDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import home.model.DocGia;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
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
        window.loadAnotherWindow("/com/javafx/lib/fxml/DocGiaDanhSach.fxml", "Danh sách độc giả");
    }

    @FXML
    void editMemberAction(ActionEvent event) {


        String ho = tfHoDocGia.getText();
        String ten = tfTenDocGia.getText();
        String sdt = tfSDT.getText();
        String email = tfEmail.getText();
        int madg = DocGiaDanhSachController.v_maDG;
        DocGia member = new DocGia(madg,ho,ten,sdt,email);
        DocGiaDao memberDao = new DocGiaDao();

        boolean flag = memberDao.updateMember(member);
        if(flag){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật dữ liệu thành công");
            alert.showAndWait();
            cancelAction(event);

        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Cập nhật dữ liệu thất bại!!!");
            alert.showAndWait();
            cancelAction(event);

        }
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tfHoDocGia.setText(DocGiaDanhSachController.v_ho);
        tfTenDocGia.setText(DocGiaDanhSachController.v_ten);
        tfSDT.setText(DocGiaDanhSachController.v_sdt);
        tfEmail.setText(DocGiaDanhSachController.v_email);
    }
}
