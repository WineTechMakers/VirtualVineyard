package bg.tu_varna.sit.virtualvineyard.GUI.operator;

import bg.tu_varna.sit.virtualvineyard.chain.BottlingFactory;
import bg.tu_varna.sit.virtualvineyard.GUI.NavigationManager;
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

        if (bottledWineWarehouseComboBox.getValue() == null ||
                bottleWarehouseComboBox.getValue() == null) {
            NavigationManager.showAlert(Alert.AlertType.ERROR,"Error!","You must select all three warehouses!");
            return;
        }

        if (selectedWine == null) {
            NavigationManager.showAlert(Alert.AlertType.WARNING, "No wine", "Please select a wine to bottle.");
            return;
        }

        try {
            BottlingFactory factory = new BottlingFactory(
                    bottledWineWarehouseComboBox.getValue(),
                    bottleWarehouseComboBox.getValue()
            );

            factory.bottleWine(selectedWine);
            StringBuilder alerts = new StringBuilder();
            for (Warehouse warehouse : warehouseDAO.findAll()){
                if(warehouse.getWarehouseType().getType().equals(WarehouseContentType.GRAPE_ONLY.toString())
                        && warehouse.isCriticalLimit()){
                    alerts.append("\nGrape critical low limit reached in Warehouse ");
                    alerts.append(warehouse.getName());
                    alerts.append("!");
                }
            }

            if(bottleWarehouseComboBox.getValue().isCriticalLimit()){
                alerts.append("\nBottle critical low limit reached!");
            }

            List<BottledWine> results = bottledWineDAO.findByWine(selectedWine);
            bottledWinesTable.setItems(FXCollections.observableArrayList(results));

            NavigationManager.showAlert(Alert.AlertType.INFORMATION, "Success", "Bottling finished." + alerts);
        } catch (Exception ex) {
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Bottling error", ex.getMessage());
        }
    }
}
