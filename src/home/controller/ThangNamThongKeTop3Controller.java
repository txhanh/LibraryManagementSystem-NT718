package home.controller;

import home.Main;
import home.dao.JDBCConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ThangNamThongKeTop3Controller implements Initializable {

    Main window = new Main();

    @FXML
    private TextField tfThang;

    @FXML
    private TextField tfNam;

    @FXML
    private Button btnThongKeTop3;

    @FXML
    void thongKeTop3Action(ActionEvent event) throws JRException {
        int thang = Integer.valueOf(tfThang.getText());
        int nam = Integer.valueOf(tfNam.getText());

        /*
        Tạo hashmap và truyền tham số từ giao diện vào
         */
        HashMap hashmap = new HashMap();
        hashmap.put("thang", thang);
        hashmap.put("nam", nam);

        /*
        Tạo kết nối với CSDL
        Chọn đường dẫn file report đã tạo, và file output dạng pdf
         */
        Connection connection = JDBCConnection.getJDBCConnection();
        String dir = ".\\src\\home\\report\\TK3_Top3SachMuon.jrxml";
//        String pdf = "D:\\IntelliJ\\iReport\\Data\\DemoTK1\\Top3SachMuon\\Top3SachMuon.pdf";

        /*
        Gọi các thư viện của Jasper report
         */
        JasperDesign jd = JRXmlLoader.load(dir);
        JasperReport jr = JasperCompileManager.compileReport(dir);
        JasperPrint jp = JasperFillManager.fillReport(jr, hashmap, connection);
        JasperViewer jv = new JasperViewer(jp, false); // tắt report mà không tắt chương trình
        jv.setVisible(true);

        cancelAction(event);
    }

    @FXML
    void cancelAction(ActionEvent event) {
        Stage stage = (Stage) btnThongKeTop3.getScene().getWindow();
        stage.close();
        window.loadAnotherWindow("/home/fxml/BaoCaoThongKe.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*
        Các textField chỉ được phép nhập số, không cho nhập các ký tự khác
         */

        tfThang.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfThang.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        tfNam.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfNam.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
}
