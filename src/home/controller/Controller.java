package home.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    DocGiaDao memberDao = new DocGiaDao();
    Main window = new Main();
    ObservableList<DocGia> dataTable = FXCollections.observableArrayList(memberDao.getAllMember());

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

    @FXML
    private GridPane paneDocGia;

    @FXML
    private GridPane paneTuaSach;

    @FXML
    private GridPane paneCuonSach;

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
    private Pane paneShow;

    @FXML
    public BorderPane borderPane;

    @FXML
    void openAddMemberWindow(ActionEvent event) {
        window.loadAnotherWindow("/home/fxml/DocGiaThem.fxml");
        Stage stage = (Stage) btnAddMember.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

    @FXML
    void handleClose(MouseEvent event) {
        if (event.getSource() == btnClose) {
            System.exit(0);
        }
    }


    @FXML
    void handleClickAction(ActionEvent event) {
        if (event.getSource() == btnDocGia) {
//            labelStatus.setText("QUẢN LÍ THÔNG TIN ĐỘC GIẢ");
//            paneStatus.setBackground(new Background(new BackgroundFill(Color.rgb
//                    (113,86,200), CornerRadii.EMPTY, Insets.EMPTY)));
//            paneDocGia.toFront();

            loadUI("/home/fxml/DocGiaDanhSach.fxml");

        } else if (event.getSource() == btnTuaSach) {
//            labelStatus.setText("QUẢN LÍ THÔNG TIN TỰA SÁCH");
//            paneStatus.setBackground(new Background(new BackgroundFill(Color.rgb
//                    (43,63,99), CornerRadii.EMPTY, Insets.EMPTY)));
//            paneTuaSach.toFront();
            loadUI("/home/fxml/TuaSachDanhSach.fxml");
        } else if (event.getSource() == btnCuonSach) {
//            labelStatus.setText("QUẢN LÍ THÔNG TIN CUỐN SÁCH");
//            paneStatus.setBackground(new Background(new BackgroundFill(Color.rgb
//                    (43,99,63), CornerRadii.EMPTY, Insets.EMPTY)));
//            paneCuonSach.toFront();
        } else if (event.getSource() == btnPhieuMuon) {
//            labelStatus.setText("QUẢN LÍ THÔNG TIN PHIẾU MƯỢN SÁCH");
//            paneStatus.setBackground(new Background(new BackgroundFill(Color.rgb
//                    (99,42,70), CornerRadii.EMPTY, Insets.EMPTY)));
            // chua co pane
        } else if (event.getSource() == btnPhieuTra) {
//            labelStatus.setText("QUẢN LÍ THÔNG TIN PHIẾU TRẢ SÁCH");
//            paneStatus.setBackground(new Background(new BackgroundFill(Color.rgb
//                    (33,130,200), CornerRadii.EMPTY, Insets.EMPTY)));
            //chưa có pane
        }


    }

    public void loadUI(String nameUI) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(nameUI));
        } catch (IOException e) {
            e.printStackTrace();
        }
        borderPane.setCenter(root);
    }
}
