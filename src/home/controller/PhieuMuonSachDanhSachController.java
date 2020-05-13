package home.controller;

import home.Main;
import home.dao.PhieuMuonSachDao;
import home.model.DocGia;
import home.model.PhieuMuonSach;
import home.model.TuaSach;
import javafx.beans.property.SimpleIntegerProperty;
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
import java.util.*;

public class PhieuMuonSachDanhSachController implements Initializable {

    public static final String patternDay = "dd - MM - yyyy";

    Main window = new Main();

    PhieuMuonSachDao phieuMuonSachDao = new PhieuMuonSachDao();
    ObservableList<PhieuMuonSach> dataPhieuMuonSach =
            FXCollections.observableArrayList(phieuMuonSachDao.lietKePhieuMuonSach());

    public static int v_maDocGia;
    public static int v_maCuonSach;
    public static Date v_ngayMuon;
    public static Date v_ngayDuKienTra;
    public static int v_maPhieuMuon;

    @FXML
    private GridPane paneDocGia;

    @FXML
    private TextField tfSearchPhieuMuonSach;

    @FXML
    private Button btnThemPhieuMuonSach;

    @FXML
    private Button btnHome;

    @FXML
    private TableView<PhieuMuonSach> tablePhieuMuonSach;

    @FXML
    private TableColumn<PhieuMuonSach, Integer> maPhieuMuonColumn;

    @FXML
    private TableColumn<PhieuMuonSach, Integer> maDocGiaColumn;

    @FXML
    private TableColumn<PhieuMuonSach, String> tenDocGiaColumn;

    @FXML
    private TableColumn<PhieuMuonSach, Integer> maCuonSachColumn;

    @FXML
    private TableColumn<PhieuMuonSach, String> tenTuaSachColumn;

    @FXML
    private TableColumn<PhieuMuonSach, String> tenTheLoaiColumn;

    @FXML
    private TableColumn<PhieuMuonSach, Date> ngayMuonColumn;

    @FXML
    private TableColumn<PhieuMuonSach, Date> ngayDuKienTraColumn;


    @FXML
    void openCapNhatPhieuMuonSachAction(ActionEvent event) {
        PhieuMuonSach selectedForUpdate = tablePhieuMuonSach.getSelectionModel().getSelectedItem();
        if (selectedForUpdate == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Bạn chưa chọn dòng nào để cập nhật cả");
            alert.showAndWait();
            return;
        }

        v_maDocGia = selectedForUpdate.getMaDocGia();
        v_maCuonSach = selectedForUpdate.getMaCuonSach();
        v_ngayMuon = selectedForUpdate.getNgayMuon();
        v_ngayDuKienTra = selectedForUpdate.getNgayDuKienTra();
        v_maPhieuMuon = selectedForUpdate.getMaPhieuMuon();

        window.loadAnotherWindow("/home/fxml/PhieuMuonSachChinhSua.fxml", "Cập nhật Phiếu mượn sách");
        cancelAction(event);
    }

    @FXML
    void xoaPhieuMuonSachAction(ActionEvent event) {
        PhieuMuonSach phieuMuonSachForDelete = tablePhieuMuonSach.getSelectionModel().getSelectedItem();
        Alert alert;
        if (phieuMuonSachForDelete == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Bạn chưa dòng nào để xóa cả !!!");
            alert.showAndWait();
            return;
        }


        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Bạn thực sự muốn xóa phiếu mượn sách số \""
                + phieuMuonSachForDelete.getMaPhieuMuon() + "\" chứ?");

        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.CANCEL) {
            return;
        }

        boolean flag = phieuMuonSachDao.xoaPhieuMuonSach(phieuMuonSachForDelete);

        if (flag) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Xóa phiếu mượn sách thành công");
            alert.showAndWait();
            dataPhieuMuonSach.remove(phieuMuonSachForDelete);
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Không xóa được phiếu mượn sách. Kiểm tra lại ràng buộc toàn vẹn !");
            alert.showAndWait();
        }
    }

    @FXML
    void openHomeWindow(ActionEvent event) {
        window.loadAnotherWindow("/home/fxml/MainGUI.fxml");
        cancelAction(event);
    }

    @FXML
    void openThemPhieuMuonSach(ActionEvent event) {
        window.loadAnotherWindow("/home/fxml/PhieuMuonSachThem.fxml", "Thêm phiếu mượn sách");
        cancelAction(event);
    }

    @FXML
    void cancelAction(ActionEvent event) {
        Stage stage = (Stage) btnThemPhieuMuonSach.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initColumn();
        tablePhieuMuonSach.setItems(dataPhieuMuonSach);
        tablePhieuMuonSach.getSortOrder().add(maPhieuMuonColumn);
        searchPhieuMuonSach();
    }

    private void searchPhieuMuonSach() {
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        // Đưa đối tượng ObservableList "data" vào trong một đối tượng của FilteredList (khởi đầu là show toàn bộ data)
        FilteredList<PhieuMuonSach> filteredData = new FilteredList<>(dataPhieuMuonSach, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        // tạo một bộ lọc để xác nhận bất kỳ thay đổi nào
        tfSearchPhieuMuonSach.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(phieuMuonSach -> {
                // If filter text is empty, display all persons.
                // nếu ô tìm kiếm trống, thì show toàn bộ người
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                DateFormat dateFormat = new SimpleDateFormat(patternDay);

                // Compare first name and last name of every person with filter text.
                // ép về kiểu chữ thường rồi so sánh dữ liệu trong DB vs dữ liệu trong bộ lọc
                String lowerCaseFilter = newValue.toLowerCase();

                if (phieuMuonSach.getTenDocGia().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name. Khi trùng với first name
                } else if (phieuMuonSach.getTenTuaSach().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else if (phieuMuonSach.getTenTheLoai().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else if (String.valueOf(phieuMuonSach.getMaCuonSach()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(phieuMuonSach.getMaDocGia()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(phieuMuonSach.getMaPhieuMuon()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (dateFormat.format(phieuMuonSach.getNgayMuon()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (dateFormat.format(phieuMuonSach.getNgayDuKienTra()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
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
        SortedList<PhieuMuonSach> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        // kiểm tra xem sortlist có khớp với tableview không
        // nếu không khớp thì tableview không bị ảnh hưởng
        sortedData.comparatorProperty().bind(tablePhieuMuonSach.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        // show dữ liệu đã được lọc và sắp xếp ra bảng
        tablePhieuMuonSach.setItems(sortedData);
        tablePhieuMuonSach.getSortOrder().add(maPhieuMuonColumn);
    }

    private void initColumn() {
        maPhieuMuonColumn.setCellValueFactory(new PropertyValueFactory<>("maPhieuMuon"));
        maDocGiaColumn.setCellValueFactory(new PropertyValueFactory<>("maDocGia"));
        tenDocGiaColumn.setCellValueFactory(new PropertyValueFactory<>("tenDocGia"));
        maCuonSachColumn.setCellValueFactory(new PropertyValueFactory<>("maCuonSach"));
        tenTuaSachColumn.setCellValueFactory(new PropertyValueFactory<>("tenTuaSach"));
        tenTheLoaiColumn.setCellValueFactory(new PropertyValueFactory<>("tenTheLoai"));
        ngayMuonColumn.setCellValueFactory(new PropertyValueFactory<>("ngayMuon"));
        ngayDuKienTraColumn.setCellValueFactory(new PropertyValueFactory<>("ngayDuKienTra"));

        // code dùng để định dạng kiểu dữ liệu Date theo ý muốn: dd-MM-yyyy vi VN
        // dùng dd/MM/yyyy
//        String pattern = "dd - MM - yyyy";
        ngayMuonColumn.setCellFactory(column -> {
            TableCell<PhieuMuonSach, Date> cell = new TableCell<PhieuMuonSach, Date>() {
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

        ngayDuKienTraColumn.setCellFactory(column -> {
            TableCell<PhieuMuonSach, Date> cell = new TableCell<PhieuMuonSach, Date>() {
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
