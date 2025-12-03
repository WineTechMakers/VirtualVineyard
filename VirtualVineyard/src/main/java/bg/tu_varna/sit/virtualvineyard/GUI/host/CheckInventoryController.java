package bg.tu_varna.sit.virtualvineyard.GUI.host;

import bg.tu_varna.sit.virtualvineyard.dao.*;
import bg.tu_varna.sit.virtualvineyard.entities.*;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CheckInventoryController {
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

    @FXML private TableView<Bottle> bottlesTable;
    @FXML private TableColumn<Bottle, Long> bottleIdColumn;
    @FXML private TableColumn<Bottle, Integer> bottleVolumeColumn;
    @FXML private TableColumn<Bottle, Integer> bottleQuantityColumn;
    @FXML private TableColumn<Bottle, String> bottleWarehouseColumn;

    private final WarehouseDAO warehouseDAO = new WarehouseDAO();
    private final GrapeDAO grapeDAO = new GrapeDAO();
    private final BottleDAO bottleDAO = new BottleDAO();

    @FXML
    public void initialize() {
        //warehouses
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

        //grapes
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

        ObservableList<Grape> grapes = FXCollections.observableArrayList(grapeDAO.findAll());
        grapesTable.setItems(grapes);

        //bottles
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

        ObservableList<Bottle> bottles = FXCollections.observableArrayList(bottleDAO.findAll());
        bottlesTable.setItems(bottles);
    }
}
