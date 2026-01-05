package bg.tu_varna.sit.virtualvineyard.GUI.operator;

import bg.tu_varna.sit.virtualvineyard.GUI.NavigationManager;
import bg.tu_varna.sit.virtualvineyard.dao.BottledWineDAO;
import bg.tu_varna.sit.virtualvineyard.entities.BottledWine;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

public class ListBottledWinesController {
    @FXML private TableView<BottledWine> bottledWinesTable;
    @FXML private TableColumn<BottledWine, String> wineNameColumn;
    @FXML private TableColumn<BottledWine, Integer> bottleVolumeColumn;
    @FXML private TableColumn<BottledWine, Integer> quantityColumn;
    @FXML private TableColumn<BottledWine, String> warehouseColumn;
    @FXML private TableColumn<BottledWine, String> bottledWineDateColumn;

    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;

    private final BottledWineDAO bottledWineDAO = new BottledWineDAO();

    @FXML
    public void initialize() {
        setupTableColumns();
        loadData();
    }

    private void setupTableColumns() {
        //BottledWine -> Wine -> Name)
        wineNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getWine().getName()));

        //BottledWine -> Bottle -> Volume (Enum) -> getVolume())
        bottleVolumeColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getBottle().getVolume().getVolume()).asObject());

        quantityColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());

        //BottledWine -> Warehouse -> Name)
        warehouseColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getWarehouse().getName()));

        bottledWineDateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getProductionDate() != null
                                ? cellData.getValue().getProductionDate().toString()
                                : "N/A"
                )
        );
    }

    private void loadData() {
        List<BottledWine> data = bottledWineDAO.findAll();

        ObservableList<BottledWine> observableList = FXCollections.observableArrayList(data);
        bottledWinesTable.setItems(observableList);
        bottleVolumeColumn.setSortType(TableColumn.SortType.DESCENDING);
        bottledWinesTable.getSortOrder().addAll(bottledWineDateColumn, wineNameColumn, bottleVolumeColumn);
    }

    @FXML
    public void onSearchClick(){
        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();

        if (start == null || end == null) {
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Error", "Please select both a Start Date and an End Date.");
            return;
        }

        if (start.isAfter(end)) {
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Invalid Range", "Start Date cannot be after End Date.");
            return;
        }

        List<BottledWine> filteredData = bottledWineDAO.findByDateRange(start, end);

        if (filteredData.isEmpty()) {
            NavigationManager.showAlert(Alert.AlertType.INFORMATION, "Info", "No bottled wines found for the selected period.");
        }

        bottledWinesTable.setItems(FXCollections.observableArrayList(filteredData));
        bottleVolumeColumn.setSortType(TableColumn.SortType.DESCENDING);
        bottledWinesTable.getSortOrder().addAll(bottledWineDateColumn, wineNameColumn, bottleVolumeColumn);
    }

    @FXML
    public void onClearClick(){
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);

        loadData();
    }
}
