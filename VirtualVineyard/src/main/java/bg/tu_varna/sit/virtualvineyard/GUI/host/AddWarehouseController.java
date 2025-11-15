package bg.tu_varna.sit.virtualvineyard.GUI.host;

import bg.tu_varna.sit.virtualvineyard.GUI.NavigationManager;
import bg.tu_varna.sit.virtualvineyard.dao.WarehouseDAO;
import bg.tu_varna.sit.virtualvineyard.dao.WarehouseTypeDAO;
import bg.tu_varna.sit.virtualvineyard.entities.Warehouse;
import bg.tu_varna.sit.virtualvineyard.entities.WarehouseType;
import bg.tu_varna.sit.virtualvineyard.enums.WarehouseContentType;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AddWarehouseController {
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField addressTextField;
    @FXML
    public ComboBox<WarehouseContentType> typeComboBox;

    @FXML
    public void initialize() {
        typeComboBox.getItems().addAll(WarehouseContentType.values());
    }

    @FXML
    private void onAddWarehouseClick() {
        String name = nameTextField.getText().trim();
        String address = addressTextField.getText().trim();
        WarehouseContentType type = typeComboBox.getValue();

        if (name.isEmpty()) {
            NavigationManager.showAlert(
                    javafx.scene.control.Alert.AlertType.WARNING,
                    "Validation Error",
                    "Warehouse name cannot be empty!"
            );
            return;
        }

        WarehouseTypeDAO warehouseTypeDAO = new WarehouseTypeDAO();
        WarehouseType warehouseType = warehouseTypeDAO.findOne(type.ordinal() + 1);
        Warehouse warehouse = new Warehouse(name, address, warehouseType);
        WarehouseDAO warehouseDAO = new WarehouseDAO();
        warehouseDAO.create(warehouse);

        NavigationManager.showAlert(
                javafx.scene.control.Alert.AlertType.INFORMATION,
                "Success",
                "Warehouse added successfully!"
        );

        nameTextField.clear();
        addressTextField.clear();
    }
}
