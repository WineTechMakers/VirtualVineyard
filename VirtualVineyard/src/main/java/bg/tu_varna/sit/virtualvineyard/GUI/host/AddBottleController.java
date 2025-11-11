package bg.tu_varna.sit.virtualvineyard.GUI.host;

import bg.tu_varna.sit.virtualvineyard.GUI.NavigationManager;
import bg.tu_varna.sit.virtualvineyard.dao.BottleDAO;
import bg.tu_varna.sit.virtualvineyard.dao.WarehouseDAO;
import bg.tu_varna.sit.virtualvineyard.entities.Bottle;
import bg.tu_varna.sit.virtualvineyard.entities.Warehouse;
import bg.tu_varna.sit.virtualvineyard.enums.BottleType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AddBottleController {
    @FXML
    private ComboBox<Warehouse> warehouseComboBox;
    @FXML
    private ComboBox<BottleType> volumeComboBox;
    @FXML
    private TextField quantityField;

    private final WarehouseDAO warehouseDAO = new WarehouseDAO();
    private final BottleDAO bottleDAO = new BottleDAO();

    @FXML
    public void initialize() {
        warehouseComboBox.getItems().addAll(warehouseDAO.findAll());
        volumeComboBox.getItems().addAll(BottleType.values());
    }

    @FXML
    private void onAddBottleClick() {
        Warehouse warehouse = warehouseComboBox.getValue();
        BottleType volume = volumeComboBox.getValue();
        String quantityText = quantityField.getText();

        if (warehouse == null || volume == null || quantityText.isEmpty()) {
            NavigationManager.showAlert(Alert.AlertType.WARNING, "Missing Data", "Please select a warehouse, volume and enter quantity.");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Invalid Input", "Quantity must be a number.");
            return;
        }

        Bottle bottle = new Bottle(volume, quantity, warehouse);
        bottleDAO.create(bottle);
        NavigationManager.showAlert(Alert.AlertType.INFORMATION, "Success", "Bottle added successfully!");

        warehouseComboBox.setValue(null);
        volumeComboBox.setValue(null);
        quantityField.clear();
    }
}
