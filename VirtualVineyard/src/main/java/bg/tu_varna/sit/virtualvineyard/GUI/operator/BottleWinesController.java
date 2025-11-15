package bg.tu_varna.sit.virtualvineyard.GUI.operator;

import bg.tu_varna.sit.virtualvineyard.dao.*;
import bg.tu_varna.sit.virtualvineyard.entities.*;
import bg.tu_varna.sit.virtualvineyard.enums.WarehouseContentType;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class BottleWinesController {
    @FXML private ComboBox<Wine> wineComboBox;
    @FXML private ComboBox<Warehouse> bottledWineWarehouseComboBox;
    @FXML private ComboBox<Warehouse> bottleWarehouseComboBox;
    @FXML private ComboBox<Warehouse> grapeWarehouseComboBox;

    @FXML private TableView<BottledWine> bottledWinesTable;
    @FXML private TableColumn<BottledWine, Integer> bottleVolumeColumn;
    @FXML private TableColumn<BottledWine, Integer> quantityColumn;

    private final BottledWineDAO bottledWineDAO = new BottledWineDAO();
    private final WineDAO wineDAO = new WineDAO();
    private final WarehouseDAO warehouseDAO = new WarehouseDAO();

    @FXML
    public void initialize() {
        ObservableList<Wine> wines = FXCollections.observableArrayList(wineDAO.findAll());
        wineComboBox.setItems(wines);
        ObservableList<Warehouse> bottledWineWarehouses = FXCollections.observableArrayList(warehouseDAO.findByContentType(WarehouseContentType.BOTTLED_WINE_ONLY));
        bottledWineWarehouseComboBox.setItems(bottledWineWarehouses);
        ObservableList<Warehouse> bottleWarehouses = FXCollections.observableArrayList(warehouseDAO.findByContentType(WarehouseContentType.BOTTLE_ONLY));
        bottleWarehouseComboBox.setItems(bottleWarehouses);
        ObservableList<Warehouse> grapeWarehouses = FXCollections.observableArrayList(warehouseDAO.findByContentType(WarehouseContentType.GRAPE_ONLY));
        grapeWarehouseComboBox.setItems(grapeWarehouses);

        bottleVolumeColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getBottle().getVolume().getVolume()).asObject()
        );

        quantityColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject()
        );
    }

    @FXML
    public void onBottleWineClick(){
        Wine selectedWine = wineComboBox.getSelectionModel().getSelectedItem();
        if (selectedWine != null) {
            List<BottledWine> results = bottledWineDAO.findByWine(selectedWine);
            //BottlingFactory factory = new BottlingFactory();
            bottledWinesTable.setItems(FXCollections.observableArrayList(results));
        } else {
            bottledWinesTable.getItems().clear();
        }
    }
}
