package bg.tu_varna.sit.virtualvineyard.GUI.host;

import bg.tu_varna.sit.virtualvineyard.GUI.NavigationManager;
import bg.tu_varna.sit.virtualvineyard.enums.ViewType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class HostController {
    @FXML
    private Label hostLabel;
    @FXML
    private StackPane contentPane;

    @FXML
    private void onAddWarehouseClick() {
        NavigationManager.loadView(ViewType.ADD_WAREHOUSE, contentPane);
    }

    @FXML
    private void onAddGrapeClick() {
        NavigationManager.loadView(ViewType.ADD_GRAPE, contentPane);
    }

    @FXML
    private void onAddBottleClick() {
        NavigationManager.loadView(ViewType.ADD_BOTTLE, contentPane);
    }

    @FXML
    private void onCheckInventoryClick() {
        NavigationManager.loadView(ViewType.CHECK_HOST_INVENTORY, contentPane);
    }

    @FXML
    private void onLogoutClick() {
        try {
            NavigationManager.closeWindow(hostLabel);
            NavigationManager.openNewWindow(ViewType.LOGIN);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "Cannot open Login panel!");
        }
    }
}
