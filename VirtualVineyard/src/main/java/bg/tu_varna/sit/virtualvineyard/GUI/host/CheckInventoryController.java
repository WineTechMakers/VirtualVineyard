package bg.tu_varna.sit.virtualvineyard.GUI.host;

import bg.tu_varna.sit.virtualvineyard.GUI.NavigationManager;
import bg.tu_varna.sit.virtualvineyard.dao.*;
import bg.tu_varna.sit.virtualvineyard.entities.*;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

public class CheckInventoryController {
    //thresholds
    private static final double GRAPE_CRITICAL_LIMIT = 50.0; // kg
    private static final int BOTTLE_CRITICAL_LIMIT = 100;    // pieces

    @FXML public TableView<Warehouse> warehousesTable;
    @FXML public TableColumn<Warehouse, Long> warehouseIdColumn;
    @FXML public TableColumn<Warehouse, String> warehouseTypeColumn;
    @FXML public TableColumn<Warehouse, String> warehouseNameColumn;
    @FXML public TableColumn<Warehouse, String> warehouseAddressColumn;

    @FXML private TableView<Grape> grapesTable;
    @FXML private TableColumn<Grape, Long> grapeIdColumn;
    @FXML private TableColumn<Grape, String> grapeNameColumn;
    @FXML private TableColumn<Grape, Double> grapeQuantityColumn;
    @FXML private TableColumn<Grape, String> grapeColorColumn;
    @FXML private TableColumn<Grape, Double> grapeYieldColumn;
    @FXML private TableColumn<Grape, String> grapeWarehouseColumn;
    @FXML private TableColumn<Grape, String> grapeDateColumn;

    @FXML private TableView<Bottle> bottlesTable;
    @FXML private TableColumn<Bottle, Long> bottleIdColumn;
    @FXML private TableColumn<Bottle, Integer> bottleVolumeColumn;
    @FXML private TableColumn<Bottle, Integer> bottleQuantityColumn;
    @FXML private TableColumn<Bottle, String> bottleWarehouseColumn;
    @FXML private TableColumn<Bottle, String> bottleDateColumn;

    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;

    private final WarehouseDAO warehouseDAO = new WarehouseDAO();
    private final GrapeDAO grapeDAO = new GrapeDAO();
    private final BottleDAO bottleDAO = new BottleDAO();

    @FXML
    public void initialize() {
        setupWarehouses();
        setupGrapes();
        setupBottles();
    }

    private void setupWarehouses() {
        warehouseIdColumn.setCellValueFactory(cellData ->
                new SimpleLongProperty(cellData.getValue().getWarehouse_id()).asObject()
        );
        warehouseTypeColumn.setCellValueFactory(cellData -> {
            WarehouseType type = cellData.getValue().getWarehouseType();
            String typeName = (type != null) ? type.getType() : "";
            return new SimpleStringProperty(typeName);
        });
        warehouseNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName())
        );
        warehouseAddressColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAddress())
        );
        ObservableList<Warehouse> warehouses = FXCollections.observableArrayList(warehouseDAO.findAll());
        warehousesTable.setItems(warehouses);
    }

    private void setupGrapes() {
        grapeIdColumn.setCellValueFactory(cellData ->
                new SimpleLongProperty(cellData.getValue().getGrape_id()).asObject()
        );
        grapeNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName())
        );
        grapeQuantityColumn.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getQuantity()).asObject()
        );
        grapeColorColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().isBlack() ? "Black" : "White")
        );
        grapeYieldColumn.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getWineYield()).asObject()
        );
        grapeWarehouseColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getWarehouse().getName())
        );

        grapeDateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getDateReceived() != null
                                ? cellData.getValue().getDateReceived().toString()
                                : "N/A"
                )
        );

        grapesTable.setRowFactory(tv -> new TableRow<Grape>() {
            @Override
            protected void updateItem(Grape item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else if (item.getQuantity() <= 0) {
                    //Red -> Empty
                    setStyle("-fx-background-color: #ffcccc;");
                } else if (item.getQuantity() < GRAPE_CRITICAL_LIMIT) {
                    //Orange/Yellow -> Critically Low
                    setStyle("-fx-background-color: #fff4cc;");
                } else {
                    setStyle("-fx-background-color: #E7EEBA;");
                }
            }
        });

        loadAllGrapes();
    }

    private void setupBottles() {
        bottleIdColumn.setCellValueFactory(cellData ->
                new SimpleLongProperty(cellData.getValue().getBottle_id()).asObject()
        );
        bottleVolumeColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getVolume().getVolume()).asObject()
        );
        bottleQuantityColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject()
        );
        bottleWarehouseColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getWarehouse().getName())
        );

        bottleDateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getDateReceived() != null
                                ? cellData.getValue().getDateReceived().toString()
                                : "N/A"
                )
        );

        bottlesTable.setRowFactory(tv -> new TableRow<Bottle>() {
            @Override
            protected void updateItem(Bottle item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else if (item.getQuantity() <= 0) {
                    //Red -> Empty
                    setStyle("-fx-background-color: #ffcccc;");
                } else if (item.getQuantity() < BOTTLE_CRITICAL_LIMIT) {
                    //Orange/Yellow -> Critically Low
                    setStyle("-fx-background-color: #fff4cc;");
                } else {
                    setStyle("-fx-background-color: #F0F0A7;");
                }
            }
        });

        loadAllBottles();
    }

    private void loadAllGrapes() {
        grapesTable.setItems(FXCollections.observableArrayList(grapeDAO.findAll()));
    }

    private void loadAllBottles() {
        bottlesTable.setItems(FXCollections.observableArrayList(bottleDAO.findAll()));
    }

    @FXML
    public void onSearchClick(){
        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();

        if (start == null || end == null) {
            NavigationManager.showAlert(Alert.AlertType.WARNING,"Alert","Please choose a start and end date!");
            return;
        }

        if (start.isAfter(end)) {
            NavigationManager.showAlert(Alert.AlertType.WARNING,"Alert","Start date cannot be after end date!");
            return;
        }

        List<Grape> filteredGrapes = grapeDAO.findByDateRange(start, end);
        List<Bottle> filteredBottles = bottleDAO.findByDateRange(start, end);

        grapesTable.setItems(FXCollections.observableArrayList(filteredGrapes));
        bottlesTable.setItems(FXCollections.observableArrayList(filteredBottles));
    }

    @FXML
    public void onClearClick(){
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);

        loadAllGrapes();
        loadAllBottles();
    }
}
