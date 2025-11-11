package bg.tu_varna.sit.virtualvineyard.GUI.host;

import bg.tu_varna.sit.virtualvineyard.GUI.NavigationManager;
import bg.tu_varna.sit.virtualvineyard.dao.WarehouseDAO;
import bg.tu_varna.sit.virtualvineyard.entities.Warehouse;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddWarehouseController {
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField addressTextField;

    @FXML
    private void onAddWarehouseClick() {
        String name = nameTextField.getText().trim();
        String address = addressTextField.getText().trim();

        if (name.isEmpty()) {
            NavigationManager.showAlert(
                    javafx.scene.control.Alert.AlertType.WARNING,
                    "Validation Error",
                    "Warehouse name cannot be empty!"
            );
            return;
        }

        Warehouse warehouse = new Warehouse(name, address);
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
