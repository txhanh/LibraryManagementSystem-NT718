package home.controller;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import home.Main;
import home.dao.TuaSachDao;
import home.model.DocGia;
import home.model.TuaSach;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class TuaSachDanhSachController implements Initializable {

    TuaSachDao tuaSachDao = new TuaSachDao();
    Main window = new Main();
    ObservableList<TuaSach> data = FXCollections.observableArrayList(tuaSachDao.lietKeTuaSach());

    public static int v_matuasach;
    public static String v_tentuasach;
    public static String v_theloai;
    public static String v_tacgia;
    public static String v_nxb;
    public static int v_soluong;


    @FXML
    private TableView<TuaSach> tableTuaSach;

    @FXML
    private TableColumn<TuaSach, Integer> maTuaSachColumn;

    @FXML
    private TableColumn<TuaSach, String> tenTuaSachColumn;

    @FXML
    private TableColumn<TuaSach, String> theLoaiColumn;

    @FXML
    private TableColumn<TuaSach, String> tacGiaColumn;

    @FXML
    private TableColumn<TuaSach, String> nxbColumn;

    @FXML
    private TableColumn<TuaSach, Integer> soLuongColumn;

    @FXML
    private Button btnCancel;

    @FXML
    private TextField tfSearchTuaSach;

    @FXML
    private Button btnThemTuaSach;

    @FXML
    void openHomeWindow(ActionEvent event) {

        window.loadAnotherWindow("/home/fxml/MainGUI.fxml");
        cancelAction(event);
    }

    @FXML
    void xoaTuaSachAction(ActionEvent event) {
        TuaSach selectedForDelete = tableTuaSach.getSelectionModel().getSelectedItem();
        Alert alert;
        if (selectedForDelete == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Bạn chưa chọn dòng nào để xóa cả!!!");
            alert.showAndWait();
            return;
        }

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Bạn thực sự muốn xóa tựa sách \"" + selectedForDelete.getTenSach() + "\" chứ?" );

        Optional<ButtonType> response = alert.showAndWait();
        if(response.get() == ButtonType.CANCEL){
            return;
        }


        boolean flag = tuaSachDao.xoaTuaSach(selectedForDelete);

        if (flag) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Xóa tựa sách thành công");
            alert.showAndWait();
            data.remove(selectedForDelete);

        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Không xóa được tựa sách");
            alert.showAndWait();
        }
    }

    @FXML
    void openCapNhatTuaSachAction(ActionEvent event) {

        TuaSach selectedForUpdate = tableTuaSach.getSelectionModel().getSelectedItem();
        if(selectedForUpdate == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Bạn chưa chọn dòng nào để cập nhật cả");
            alert.showAndWait();
            return;
        }

        v_matuasach = selectedForUpdate.getMaTuaSach();
        v_tentuasach = selectedForUpdate.getTenSach();
        v_theloai = selectedForUpdate.getTheLoai();
        v_tacgia = selectedForUpdate.getTacGia();
        v_nxb = selectedForUpdate.getNXB();
        v_soluong = selectedForUpdate.getSoLuong();

        window.loadAnotherWindow("/home/fxml/TuaSachChinhSua.fxml");
        cancelAction(event);
    }

    @FXML
    void openThemTuaSach(ActionEvent event) {
        window.loadAnotherWindow("/home/fxml/TuaSachThem.fxml");
        cancelAction(event);
    }


    @FXML
    void cancelAction(ActionEvent event) {
        Stage stage = (Stage) btnThemTuaSach.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initColumn();
        tableTuaSach.setItems(data);
        tableTuaSach.getSortOrder().add(maTuaSachColumn);
        searchTuaSach();

    }

    private void searchTuaSach() {
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        // Đưa đối tượng ObservableList "data" vào trong một đối tượng của FilteredList (khởi đầu là show toàn bộ data)
        FilteredList<TuaSach> filteredData = new FilteredList<>(data, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        // tạo một bộ lọc để xác nhận bất kỳ thay đổi nào
        tfSearchTuaSach.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(tuaSach -> {
                // If filter text is empty, display all persons.
                // nếu ô tìm kiếm trống, thì show toàn bộ người
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                // ép về kiểu chữ thường rồi so sánh dữ liệu trong DB vs dữ liệu trong bộ lọc
                String lowerCaseFilter = newValue.toLowerCase();

                if (tuaSach.getTenSach().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name. Khi trùng với first name
                } else if (tuaSach.getTheLoai().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else if (tuaSach.getTacGia().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else if (tuaSach.getNXB().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(tuaSach.getSoLuong()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(tuaSach.getMaTuaSach()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else
                    return false; // Does not match.
            });
        });

//        if (String.valueOf(employee.getSalary()).indexOf(lowerCaseFilter)!=-1)
//            return true;
        // ép kiểu về String để lọc (String.valueOf(employee.getSalary())

        // 3. Wrap the FilteredList in a SortedList.
        // đưa filter list vào trong sorted list
        SortedList<TuaSach> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        // kiểm tra xem sortlist có khớp với tableview không
        // nếu không khớp thì tableview không bị ảnh hưởng
        sortedData.comparatorProperty().bind(tableTuaSach.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        // show dữ liệu đã được lọc và sắp xếp ra bảng
        tableTuaSach.setItems(sortedData);
        tableTuaSach.getSortOrder().add(maTuaSachColumn);
    }

    private void initColumn() {
        maTuaSachColumn.setCellValueFactory(new PropertyValueFactory<TuaSach, Integer>("maTuaSach"));
        tenTuaSachColumn.setCellValueFactory(new PropertyValueFactory<TuaSach, String>("tenSach"));
        theLoaiColumn.setCellValueFactory(new PropertyValueFactory<TuaSach, String>("theLoai"));
        tacGiaColumn.setCellValueFactory(new PropertyValueFactory<TuaSach, String>("tacGia"));
        nxbColumn.setCellValueFactory(new PropertyValueFactory<TuaSach, String>("NXB"));
        soLuongColumn.setCellValueFactory(new PropertyValueFactory<TuaSach, Integer>("soLuong"));
    }
}
