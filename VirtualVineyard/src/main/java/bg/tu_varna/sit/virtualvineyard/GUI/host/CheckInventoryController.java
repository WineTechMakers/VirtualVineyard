package bg.tu_varna.sit.virtualvineyard.GUI.host;

import bg.tu_varna.sit.virtualvineyard.dao.*;
import bg.tu_varna.sit.virtualvineyard.entities.Bottle;
import bg.tu_varna.sit.virtualvineyard.entities.Grape;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CheckInventoryController {
    @FXML
    private TableView<Grape> grapesTable;
    @FXML
    private TableColumn<Grape, Long> grapeIdColumn;
    @FXML
    private TableColumn<Grape, String> grapeNameColumn;
    @FXML
    private TableColumn<Grape, Integer> grapeQuantityColumn;
    @FXML
    private TableColumn<Grape, String> grapeColorColumn;
    @FXML
    private TableColumn<Grape, Double> grapeYieldColumn;
    @FXML
    private TableColumn<Grape, String> grapeWarehouseColumn;

    @FXML
    private TableView<Bottle> bottlesTable;
    @FXML
    private TableColumn<Bottle, Long> bottleIdColumn;
    @FXML
    private TableColumn<Bottle, Integer> bottleVolumeColumn;
    @FXML
    private TableColumn<Bottle, Integer> bottleQuantityColumn;
    @FXML
    private TableColumn<Bottle, String> bottleWarehouseColumn;

    private final GrapeDAO grapeDAO = new GrapeDAO();
    private final BottleDAO bottleDAO = new BottleDAO();

    @FXML
    public void initialize() {
        //grapes
        grapeIdColumn.setCellValueFactory(cellData ->
                new SimpleLongProperty(cellData.getValue().getGrape_id()).asObject()
        );
        grapeNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName())
        );
        grapeQuantityColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject()
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
