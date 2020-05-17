package home.controller;

import home.Main;
import home.dao.CuonSachDao;
import home.dao.DocGiaDao;
import home.dao.PhieuPhatDao;
import home.dao.PhieuTraSachDao;
import home.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

import static home.controller.CuonSachThemController.trangThai1;

public class PhieuPhatThemController implements Initializable {

    Main window = new Main();

    PhieuTraSachDao phieuTraSachDao = new PhieuTraSachDao();
    ObservableList<PhieuTraSach> danhSanhPhieuTra;

    DocGiaDao docGiaDao = new DocGiaDao();
    ObservableList<DocGia> docGiaList;

    CuonSachDao cuonSachDao = new CuonSachDao();
    ObservableList<CuonSach> cuonSachList;

    @FXML
    private Button btnThemPhieuPhat;

    @FXML
    private Button btnCancel;

    @FXML
    private ComboBox<DocGia> comboboxDocGia;

    @FXML
    private ComboBox<CuonSach> comboboxCuonSach;

    @FXML
    private ComboBox<PhieuTraSach> comboboxMaPhieuTraTre;

    @FXML
    private TextField tfTienPhat;

    @FXML
    void autoShowContent(ActionEvent event) {
        int maPhieuTraTreTemp = comboboxMaPhieuTraTre.getValue().getMaPhieuTra();
        for (PhieuTraSach elementPhieuTra : danhSanhPhieuTra) {
            if (maPhieuTraTreTemp == elementPhieuTra.getMaPhieuTra()) {
                setComboboxDocGiaInfo(elementPhieuTra.getMaDocGia());
                setComboboxCuonSachInfo(elementPhieuTra.getMaCuonSach());
                tfTienPhat.setText(String.valueOf(elementPhieuTra.getTienPhat()));
                tfTienPhat.setDisable(true);
            }
        }
    }

    private void setComboboxDocGiaInfo(int maDocGia) {
        docGiaList = FXCollections.observableArrayList(docGiaDao.getAllMember());
        Iterator<DocGia> i = docGiaList.iterator();
        while (i.hasNext()) {
            DocGia docGia = i.next(); // must be called before you can call i.remove()
            if (maDocGia != docGia.getMaDocGia()) {
                i.remove();
            }
        }

        comboboxDocGia.setConverter(new StringConverter<DocGia>() {

            @Override
            public String toString(DocGia object) {
                return object.getMaDocGia() + " - " + object.getTenDocGia();
            }

            @Override
            public DocGia fromString(String string) {
                return comboboxDocGia.getItems().stream().filter(ap ->
                        ap.getTenDocGia().equals(string)).findFirst().orElse(null);
            }
        });
        comboboxDocGia.setItems(docGiaList);
        comboboxDocGia.getSelectionModel().selectFirst();
    }

    private void setComboboxCuonSachInfo(int masoCuonSach) {

        cuonSachList = FXCollections.observableArrayList(cuonSachDao.lietKeCuonSach());
        Iterator<CuonSach> i = cuonSachList.iterator();
        while (i.hasNext()) {
            CuonSach sach = i.next(); // must be called before you can call i.remove()
            if (masoCuonSach != sach.getMaCuonSach()) {
                i.remove();
            }
        }

        comboboxCuonSach.setConverter(new StringConverter<CuonSach>() {

            @Override
            public String toString(CuonSach object) {
                return object.getMaCuonSach() + " - " + object.getTenTuaSach() + " (MS: " + object.getMaTuaSach() + ")";
            }

            @Override
            public CuonSach fromString(String string) {
                return comboboxCuonSach.getItems().stream().filter(ap ->
                        ap.getTenTuaSach().equals(string)).findFirst().orElse(null);
            }
        });
        comboboxCuonSach.setItems(cuonSachList);
        comboboxCuonSach.getSelectionModel().selectFirst();

    }

    @FXML
    void cancelAction(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
        window.loadAnotherWindow("/home/fxml/PhieuPhatDanhSach.fxml");
    }

    @FXML
    void themPhieuPhatAction(ActionEvent event) {
        int maDG = comboboxDocGia.getValue().getMaDocGia();
        int maPhieuTra = comboboxMaPhieuTraTre.getValue().getMaPhieuTra();
        long tienPhat = Long.parseLong(tfTienPhat.getText());

        PhieuPhat phieuPhat = new PhieuPhat(maDG,maPhieuTra,tienPhat);
        PhieuPhatDao phieuPhatDao = new PhieuPhatDao();
        boolean flag = phieuPhatDao.themPhieuPhat(phieuPhat);
        if(flag){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Thêm phiếu phạt thành công");
            alert.showAndWait();
            cancelAction(event);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Không thêm được phiếu phạt");
            alert.showAndWait();
            cancelAction(event);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // khởi tạo danh sách phiếu trả
        danhSanhPhieuTra = FXCollections.observableArrayList(phieuTraSachDao.lietKePhieuTraSach());

        /*
        thực hiện xóa những dòng mà có Tiền phạt = 0
         */
        Iterator<PhieuTraSach> i = danhSanhPhieuTra.iterator();
        while (i.hasNext()) {
            PhieuTraSach phieuTraSachTre = i.next(); // must be called before you can call i.remove()
            if (phieuTraSachTre.getTienPhat() == 0) {
                i.remove();
            }
        }
        /*
        set comboboxMaPhieuTraTre có giá trị là những dòng mà có tiền phạt > 0
        (tức là những phiếu trả sách TRỄ)
         */
        comboboxMaPhieuTraTre.setItems(danhSanhPhieuTra);

        // Convert từ Object Combobox -> chuỗi String
        comboboxMaPhieuTraTre.setConverter(new StringConverter<PhieuTraSach>() {

            @Override
            public String toString(PhieuTraSach object) {
                return "Phiếu trả sách trễ số: " + object.getMaPhieuTra() + " - Độc giả: " + object.getTenDocGia();
            }

            @Override
            public PhieuTraSach fromString(String string) {
                return comboboxMaPhieuTraTre.getItems().stream().filter(ap ->
                        ap.getTenDocGia().equals(string)).findFirst().orElse(null);
            }
        });
        // lấy phần tử đầu tiên
//        comboboxMaPhieuTraTre.getSelectionModel().selectFirst();


    }
}
