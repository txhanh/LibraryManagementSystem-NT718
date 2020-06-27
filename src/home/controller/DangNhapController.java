package home.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DangNhapController {

    @FXML
    private TextField tfUser;

    @FXML
    private PasswordField pwPassword;

    @FXML
    private Button btnDangNhap;


    private static String nguoiDung = null;

    public static String getNguoiDung() {
        return nguoiDung;
    }

    public static void setNguoiDung(String nguoiDung) {
        DangNhapController.nguoiDung = nguoiDung;
    }

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    void dangNhapMethod(ActionEvent event) throws IOException {

        if (tfUser.getText().equals("admin") && pwPassword.getText().equals("admin")) {
            Stage primaryStage = new Stage();
            nguoiDung = "Quản lí";
            Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/MainGUI.fxml"));
//         grab your root here
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });

            // move around here
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    primaryStage.setX(event.getScreenX() - xOffset);
                    primaryStage.setY(event.getScreenY() - yOffset);
                }
            });

            primaryStage.setTitle("Phần mềm quản lí thư viện");
            primaryStage.initStyle(StageStyle.TRANSPARENT);


            primaryStage.setScene(new Scene(root));
            primaryStage.show();

            Stage stage = (Stage) btnDangNhap.getScene().getWindow();
            stage.close();

        } else if (tfUser.getText().equals("thuthu") && pwPassword.getText().equals("1")) {
            Stage primaryStage = new Stage();
            nguoiDung = "Thủ thư";
            Parent root = FXMLLoader.load(getClass().getResource("/home/fxml/MainGUI.fxml"));
//         grab your root here
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });

            // move around here
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    primaryStage.setX(event.getScreenX() - xOffset);
                    primaryStage.setY(event.getScreenY() - yOffset);
                }
            });

            primaryStage.setTitle("Phần mềm quản lí thư viện");
            primaryStage.initStyle(StageStyle.TRANSPARENT);


            primaryStage.setScene(new Scene(root));
            primaryStage.show();

            Stage stage = (Stage) btnDangNhap.getScene().getWindow();
            stage.close();


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Sai tên đăng nhập hoặc mật khẩu, vui lòng thử lại");
            alert.showAndWait();
            tfUser.setText("");
            pwPassword.setText("");
            return;
        }
    }


}
