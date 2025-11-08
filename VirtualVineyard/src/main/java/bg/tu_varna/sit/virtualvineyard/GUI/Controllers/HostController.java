package bg.tu_varna.sit.virtualvineyard.GUI.Controllers;

import bg.tu_varna.sit.virtualvineyard.GUI.NavigationManager;
import bg.tu_varna.sit.virtualvineyard.enums.ViewType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class HostController {
    @FXML
    public Label hostLabel;
    @FXML
    public StackPane contentPane;

    @FXML
    public void onAddGrapesClick() {
        NavigationManager.loadView(ViewType.ADD_GRAPES, contentPane);
    }

    @FXML
    public void onAddBottlesClick() {
        NavigationManager.loadView(ViewType.ADD_BOTTLES, contentPane);
    }

    @FXML
    public void onCheckInventoryClick() {
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
