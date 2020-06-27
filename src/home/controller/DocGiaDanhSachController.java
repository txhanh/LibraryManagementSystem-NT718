package home.controller;

import home.Main;
import home.dao.DocGiaDao;
import home.model.DocGia;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DocGiaDanhSachController implements Initializable {

    DocGiaDao memberDao = new DocGiaDao();
    Main window = new Main();
    ObservableList<DocGia> dataTable = FXCollections.observableArrayList(memberDao.getAllMember());



    /*
    Tạo 1 object DocGia và sử dụng get/set để truyền dữ liệu
    khi được chọn từ bảng TableView để thực hiện việc
    cập nhật thông tin Độc giả.
    Truyền tất cả các thông tin từ bảng TableView sang
    cửa sổ Cập nhật thông tin Độc giả
     */
    private static DocGia selectedMemberForUpdate;

    public static DocGia getSelectedMemberForUpdate() {
        return selectedMemberForUpdate;
    }

    public static void setSelectedMemberForUpdate(DocGia selectedMemberForUpdate) {
        DocGiaDanhSachController.selectedMemberForUpdate = selectedMemberForUpdate;
    }

    @FXML
    private TableView<DocGia> tableMember;

    @FXML
    private TableColumn<DocGia, Integer> maDocGiaColumn;

    @FXML
    private TableColumn<DocGia, String> hoDocGiaColumn;

    @FXML
    private TableColumn<DocGia, String> tenDocGiaColumn;

    @FXML
    private TableColumn<DocGia, String> sdtColumn;

    @FXML
    private TableColumn<DocGia, String> emailColumn;

    @FXML
    private TextField tfSearchMemberInfo;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnAddMember;

    @FXML
    private Button btnHome;

    @FXML
    void openHomeWindow(ActionEvent event) {

        window.loadAnotherWindow("/home/fxml/MainGUI.fxml");


        cancelAction(event);
    }

    @FXML
    void openCapNhatDocGiaAction(ActionEvent event) {

        selectedMemberForUpdate = tableMember.getSelectionModel().getSelectedItem();


        if (selectedMemberForUpdate == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Bạn chưa chọn dòng nào để cập nhật cả!!!");
            alert.showAndWait();
            return ;
        }


        window.loadAnotherWindow("/home/fxml/DocGiaChinhSua.fxml", "Cập nhật thông tin độc giả");
        cancelAction(event);

    }


    @FXML
        void openAddMemberWindow(ActionEvent event) {
        window.loadAnotherWindow("/home/fxml/DocGiaThem.fxml", "Thêm độc giả");
        cancelAction(event);
    }


    @FXML
    void xoaDocGiaAction(ActionEvent event) {
        DocGia selectedMemberForDelete = tableMember.getSelectionModel().getSelectedItem();
        Alert alert;
        if (selectedMemberForDelete == null) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Bạn chưa dòng nào để xóa cả !!!");
            alert.showAndWait();
            return;
        }


        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Bạn thực sự muốn xóa độc giả \"" + selectedMemberForDelete.getTenDocGia() + "\" chứ?");

        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.CANCEL) {
            return;
        }

        boolean flag = memberDao.deleteMember(selectedMemberForDelete);

        if (flag) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Xóa độc giả thành công");
            alert.showAndWait();
            dataTable.remove(selectedMemberForDelete);
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Không xóa được độc giả. Kiểm tra lại ràng buộc toàn vẹn !");
            alert.showAndWait();
        }
    }

    @FXML
    void cancelAction(ActionEvent event) {
        Stage stage = (Stage) btnAddMember.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initColumn();
        tableMember.setItems(dataTable);
        tableMember.getSortOrder().add(maDocGiaColumn);
        searchMemberInfo();
    }

    private void searchMemberInfo() {
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        // Đưa đối tượng ObservableList "data" vào trong một đối tượng của FilteredList (khởi đầu là show toàn bộ data)
        FilteredList<DocGia> filteredData = new FilteredList<>(dataTable, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        // tạo một bộ lọc để xác nhận bất kỳ thay đổi nào
        tfSearchMemberInfo.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(member -> {
                // If filter text is empty, display all persons.
                // nếu ô tìm kiếm trống, thì show toàn bộ người
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                // ép về kiểu chữ thường rồi so sánh dữ liệu trong DB vs dữ liệu trong bộ lọc
                String lowerCaseFilter = newValue.toLowerCase();

                if (member.getHoDocGia().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name. Khi trùng với first name
                } else if (member.getTenDocGia().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else if (member.getSdt().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else if (member.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(member.getMaDocGia()).toLowerCase().indexOf(lowerCaseFilter) != -1) {
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
        SortedList<DocGia> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        // kiểm tra xem sortlist có khớp với tableview không
        // nếu không khớp thì tableview không bị ảnh hưởng
        sortedData.comparatorProperty().bind(tableMember.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        // show dữ liệu đã được lọc và sắp xếp ra bảng
        tableMember.setItems(sortedData);
        tableMember.getSortOrder().add(maDocGiaColumn);
    }

    private void initColumn() {
        maDocGiaColumn.setCellValueFactory(new PropertyValueFactory<DocGia, Integer>("maDocGia"));
        hoDocGiaColumn.setCellValueFactory(new PropertyValueFactory<DocGia, String>("hoDocGia"));
        tenDocGiaColumn.setCellValueFactory(new PropertyValueFactory<DocGia, String>("tenDocGia"));
        sdtColumn.setCellValueFactory(new PropertyValueFactory<DocGia, String>("sdt"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<DocGia, String>("email"));
    }
}
