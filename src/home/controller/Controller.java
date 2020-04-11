package home.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

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
    private Pane paneStatus;

    @FXML
    private Label labelStatus;

    @FXML
    private FontAwesomeIconView btnClose;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void handleClose(MouseEvent event) {
        if(event.getSource() == btnClose){
            System.exit(0);
        }
    }


    @FXML
    void handleClickAction(ActionEvent event) {
        if (event.getSource() == btnDocGia) {
            labelStatus.setText("QUẢN LÍ THÔNG TIN ĐỘC GIẢ");
            paneStatus.setBackground(new Background(new BackgroundFill(Color.rgb
                    (113,86,200), CornerRadii.EMPTY, Insets.EMPTY)));
        } else if (event.getSource() == btnTuaSach) {
            labelStatus.setText("QUẢN LÍ THÔNG TIN TỰA SÁCH");
            paneStatus.setBackground(new Background(new BackgroundFill(Color.rgb
                    (43,63,99), CornerRadii.EMPTY, Insets.EMPTY)));
        } else if (event.getSource() == btnCuonSach) {
            labelStatus.setText("QUẢN LÍ THÔNG TIN CUỐN SÁCH");
            paneStatus.setBackground(new Background(new BackgroundFill(Color.rgb
                    (43,99,63), CornerRadii.EMPTY, Insets.EMPTY)));
        } else if (event.getSource() == btnPhieuMuon) {
            labelStatus.setText("QUẢN LÍ THÔNG TIN PHIẾU MƯỢN SÁCH");
            paneStatus.setBackground(new Background(new BackgroundFill(Color.rgb
                    (99,42,70), CornerRadii.EMPTY, Insets.EMPTY)));
        } else if (event.getSource() == btnPhieuTra) {
            labelStatus.setText("QUẢN LÍ THÔNG TIN PHIẾU TRẢ SÁCH");
            paneStatus.setBackground(new Background(new BackgroundFill(Color.rgb
                    (33,130,200), CornerRadii.EMPTY, Insets.EMPTY)));
        }


    }
}
