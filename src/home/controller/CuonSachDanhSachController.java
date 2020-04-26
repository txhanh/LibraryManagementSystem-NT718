package home.controller;

import home.Main;
import home.dao.CuonSachDao;
import home.model.CuonSach;
import home.model.TuaSach;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CuonSachDanhSachController implements Initializable {

    Main window = new Main();

    CuonSachDao cuonSachDao = new CuonSachDao();
    ObservableList<CuonSach> data = FXCollections.observableArrayList(cuonSachDao.lietKeCuonSach());


    public static int v_macuonsach;
    public static String v_tentuasach;
    public static int v_trangthai;



    @FXML
    private GridPane paneDocGia;

    @FXML
    private TextField tfSearchCuonSach;

    @FXML
    private Button btnThemCuonSach;

    @FXML
    private Button btnHome;

    @FXML
    private TableView<CuonSach> tableCuonSach;

    @FXML
    private TableColumn<CuonSach, Integer> maCuonSachColumn;

    @FXML
    private TableColumn<CuonSach, Integer> maTuaSachColumn;

    @FXML
    private TableColumn<CuonSach, String> tenTuaSachColumn;

    @FXML
    private TableColumn<CuonSach, String> tenTheLoaiColumn;

    @FXML
    private TableColumn<CuonSach, String> tacGiaColumn;

    @FXML
    private TableColumn<CuonSach, Integer> trangThaiColumn;

    @FXML
    void openCapNhatCuonSachAction(ActionEvent event) {
        CuonSach selectedForUpdate = tableCuonSach.getSelectionModel().getSelectedItem();
        if (selectedForUpdate == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Bạn chưa chọn dòng nào để cập nhật cả");
            alert.showAndWait();
            return;
        }

        v_macuonsach = selectedForUpdate.getMaCuonSach();
        v_tentuasach = selectedForUpdate.getTenTuaSach();
        v_trangthai = selectedForUpdate.getTrangThai();

        window.loadAnotherWindow("/home/fxml/CuonSachChinhSua.fxml");
        cancelAction(event);
    }

    @FXML
    void openHomeWindow(ActionEvent event) {
        window.loadAnotherWindow("/home/fxml/MainGUI.fxml");
        cancelAction(event);
    }

    @FXML
    void openThemCuonSach(ActionEvent event) {
        window.loadAnotherWindow("/home/fxml/CuonSachThem.fxml");
        cancelAction(event);
    }

    @FXML
    void xoaCuonSachAction(ActionEvent event) {
        CuonSach selectedForDeleteCuonSach = tableCuonSach.getSelectionModel().getSelectedItem();

        Alert alert;
        if (selectedForDeleteCuonSach == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Bạn chưa chọn dòng nào để xóa cả!!!");
            alert.showAndWait();
            return;
        }

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Bạn thực sự muốn xóa cuốn sách \"" + selectedForDeleteCuonSach.getTenTuaSach()
                + "\" trong BẢNG CUỐN SÁCH chứ?");

        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.CANCEL) {
            return;
        }


        boolean flag = cuonSachDao.XoaCuonSach(selectedForDeleteCuonSach);

        if (flag) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Xóa cuốn sách thành công");
            alert.showAndWait();
            data.remove(selectedForDeleteCuonSach);

        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Không xóa được cuốn sách");
            alert.showAndWait();
        }
    }

    @FXML
    void cancelAction(ActionEvent event) {
        Stage stage = (Stage) btnThemCuonSach.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initcolumn();
        tableCuonSach.setItems(data);
        tableCuonSach.getSortOrder().add(maCuonSachColumn);
        searchCuonSach();
    }

    private void initcolumn() {
        maCuonSachColumn.setCellValueFactory(new PropertyValueFactory<>("maCuonSach"));
        maTuaSachColumn.setCellValueFactory(new PropertyValueFactory<>("maTuaSach"));
        tenTuaSachColumn.setCellValueFactory(new PropertyValueFactory<>("tenTuaSach"));
        tenTheLoaiColumn.setCellValueFactory(new PropertyValueFactory<>("tenTheLoai"));
        tacGiaColumn.setCellValueFactory(new PropertyValueFactory<>("tacGia"));
        trangThaiColumn.setCellValueFactory(new PropertyValueFactory<>("trangThai"));
    }

    private void searchCuonSach() {
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        // Đưa đối tượng ObservableList "data" vào trong một đối tượng của FilteredList (khởi đầu là show toàn bộ data)
        FilteredList<CuonSach> filteredData = new FilteredList<>(data, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        // tạo một bộ lọc để xác nhận bất kỳ thay đổi nào
        tfSearchCuonSach.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(cuonSach -> {
                // If filter text is empty, display all persons.
                // nếu ô tìm kiếm trống, thì show toàn bộ người
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                // ép về kiểu chữ thường rồi so sánh dữ liệu trong DB vs dữ liệu trong bộ lọc
                String lowerCaseFilter = newValue.toLowerCase();

                if (cuonSach.getTenTuaSach().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name. Khi trùng với first name
                } else if (cuonSach.getTenTheLoai().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else if (cuonSach.getTacGia().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else if (String.valueOf(cuonSach.getTrangThai()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(cuonSach.getMaCuonSach()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(cuonSach.getMaTuaSach()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
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
        SortedList<CuonSach> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        // kiểm tra xem sortlist có khớp với tableview không
        // nếu không khớp thì tableview không bị ảnh hưởng
        sortedData.comparatorProperty().bind(tableCuonSach.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        // show dữ liệu đã được lọc và sắp xếp ra bảng
        tableCuonSach.setItems(sortedData);
        tableCuonSach.getSortOrder().add(maTuaSachColumn);
    }
}
