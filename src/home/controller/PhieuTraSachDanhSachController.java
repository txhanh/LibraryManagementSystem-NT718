package home.controller;

import home.Main;
import home.dao.PhieuTraSachDao;
import home.model.PhieuMuonSach;
import home.model.PhieuTraSach;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import static home.controller.PhieuMuonSachDanhSachController.patternDay;

public class PhieuTraSachDanhSachController implements Initializable {

    Main window = new Main();

    PhieuTraSachDao phieuTraSachDao = new PhieuTraSachDao();
    ObservableList<PhieuTraSach> datePhieuTraSach =
            FXCollections.observableArrayList(phieuTraSachDao.lietKePhieuTraSach());

    @FXML
    private GridPane panePhieuTraSach;

    @FXML
    private TextField tfSearchPhieuTraSach;

    @FXML
    private Button btnThemPhieuTraSach;

    @FXML
    private Button btnHome;

    @FXML
    private TableView<PhieuTraSach> tablePhieuTraSach;

    @FXML
    private TableColumn<PhieuTraSach, Integer> maPhieuTraColumn;

    @FXML
    private TableColumn<PhieuTraSach, Integer> maPhieuMuonColumn;

    @FXML
    private TableColumn<PhieuTraSach, String> tenTuaSachColumn;

    @FXML
    private TableColumn<PhieuTraSach, String> tenDocGiaColumn;

    @FXML
    private TableColumn<PhieuTraSach, Date> ngayMuonColumn;

    @FXML
    private TableColumn<PhieuTraSach, Date> ngayTraColumn;

    @FXML
    private TableColumn<PhieuTraSach, Integer> soNgayMuonColumn;

    @FXML
    private TableColumn<PhieuTraSach, Integer> soNgayTraTreColumn;

    @FXML
    private TableColumn<PhieuTraSach, Long> tienPhatColumn;

    @FXML
    void openCapNhatPhieuTraSachAction(ActionEvent event) {

    }

    @FXML
    void openHomeWindow(ActionEvent event) {
        window.loadAnotherWindow("/home/fxml/MainGUI.fxml");
        cancelAction(event);
    }

    @FXML
    void cancelAction(ActionEvent event) {
        Stage stage = (Stage) btnThemPhieuTraSach.getScene().getWindow();
        stage.close();
    }

    @FXML
    void openThemPhieuTraSach(ActionEvent event) {
        window.loadAnotherWindow("/home/fxml/PhieuTraSachThem.fxml", "Thêm phiếu trả sách");
        cancelAction(event);
    }

    @FXML
    void xoaPhieuTraSachAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initColumn();
        tablePhieuTraSach.setItems(datePhieuTraSach);
        tablePhieuTraSach.getSortOrder().add(maPhieuTraColumn);
        searchPhieuTraSach();
    }

    private void searchPhieuTraSach() {
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        // Đưa đối tượng ObservableList "data" vào trong một đối tượng của FilteredList (khởi đầu là show toàn bộ data)
        FilteredList<PhieuTraSach> filteredData = new FilteredList<>(datePhieuTraSach, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        // tạo một bộ lọc để xác nhận bất kỳ thay đổi nào
        tfSearchPhieuTraSach.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(phieuTraSach -> {
                // If filter text is empty, display all persons.
                // nếu ô tìm kiếm trống, thì show toàn bộ người
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                DateFormat dateFormat = new SimpleDateFormat(patternDay);

                // Compare first name and last name of every person with filter text.
                // ép về kiểu chữ thường rồi so sánh dữ liệu trong DB vs dữ liệu trong bộ lọc
                String lowerCaseFilter = newValue.toLowerCase();

                if (phieuTraSach.getTenDocGia().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name. Khi trùng với first name
                } else if (phieuTraSach.getTenSachMuon().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else if (String.valueOf(phieuTraSach.getTienPhat()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else if (String.valueOf(phieuTraSach.getSoNgayTraTre()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches last name.
                } else if (String.valueOf(phieuTraSach.getSoNgayMuon()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(phieuTraSach.getMaPhieuTra()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(phieuTraSach.getMaPhieuMuon()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (dateFormat.format(phieuTraSach.getNgayMuonSach()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (dateFormat.format(phieuTraSach.getNgayTraSach()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
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
        SortedList<PhieuTraSach> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        // kiểm tra xem sortlist có khớp với tableview không
        // nếu không khớp thì tableview không bị ảnh hưởng
        sortedData.comparatorProperty().bind(tablePhieuTraSach.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        // show dữ liệu đã được lọc và sắp xếp ra bảng
        tablePhieuTraSach.setItems(sortedData);
        tablePhieuTraSach.getSortOrder().add(maPhieuTraColumn);
    }


    private void initColumn() {
        maPhieuTraColumn.setCellValueFactory(new PropertyValueFactory<>("maPhieuTra"));
        maPhieuMuonColumn.setCellValueFactory(new PropertyValueFactory<>("maPhieuMuon"));
        tenTuaSachColumn.setCellValueFactory(new PropertyValueFactory<>("tenSachMuon"));
        tenDocGiaColumn.setCellValueFactory(new PropertyValueFactory<>("tenDocGia"));
        ngayMuonColumn.setCellValueFactory(new PropertyValueFactory<>("ngayMuonSach"));
        ngayTraColumn.setCellValueFactory(new PropertyValueFactory<>("ngayTraSach"));
        soNgayMuonColumn.setCellValueFactory(new PropertyValueFactory<>("soNgayMuon"));
        soNgayTraTreColumn.setCellValueFactory(new PropertyValueFactory<>("soNgayTraTre"));
        tienPhatColumn.setCellValueFactory(new PropertyValueFactory<>("tienPhat"));


        // code dùng để định dạng kiểu dữ liệu Date theo ý muốn: dd-MM-yyyy vi VN
        // dùng dd/MM/yyyy
//        String pattern = "dd - MM - yyyy";
        ngayMuonColumn.setCellFactory(column -> {
            TableCell<PhieuTraSach, Date> cell = new TableCell<PhieuTraSach, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat(patternDay);

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(format.format(item));
                    }
                }
            };
            return cell;
        });

        ngayTraColumn.setCellFactory(column -> {
            TableCell<PhieuTraSach, Date> cell = new TableCell<PhieuTraSach, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat(patternDay);

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(format.format(item));
                    }
                }
            };
            return cell;
        });
    }
}
