package bg.tu_varna.sit.virtualvineyard.GUI;

import bg.tu_varna.sit.virtualvineyard.enums.ViewType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HostController {
    @FXML
    public Label hostLabel;

    @FXML
    public void onAddGrapesButtonClick() {
        NavigationManager.openNewWindow(ViewType.ADD_GRAPES);
    }

    @FXML
    public void onAddBottlesButtonClick() {
        NavigationManager.openNewWindow(ViewType.ADD_BOTTLES);
    }

    @FXML
    public void onCheckInventoryButtonClick() {
        NavigationManager.openNewWindow(ViewType.CHECK_HOST_INVENTORY);
    }

    @FXML
    public void onBackButtonClick() {
        NavigationManager.closeWindow(hostLabel);
    }
}
