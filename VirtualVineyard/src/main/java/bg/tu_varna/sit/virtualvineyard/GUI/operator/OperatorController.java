package bg.tu_varna.sit.virtualvineyard.GUI.operator;

import bg.tu_varna.sit.virtualvineyard.GUI.NavigationManager;
import bg.tu_varna.sit.virtualvineyard.enums.ViewType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class OperatorController {
    @FXML
    public Label operatorLabel;
    @FXML
    private StackPane contentPane;

    @FXML
    private void onAddWineClick() {
        NavigationManager.loadView(ViewType.ADD_WINE, contentPane);
    }

    @FXML
    private void onListWinesClick() {
        NavigationManager.loadView(ViewType.LIST_WINES, contentPane);
    }

    @FXML
    private void onBottleWinesClick() {
        NavigationManager.loadView(ViewType.BOTTLE_WINES, contentPane);
    }

    @FXML
    private void onLogoutClick() {
        try {
            NavigationManager.closeWindow(operatorLabel);
            NavigationManager.openNewWindow(ViewType.LOGIN);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "Cannot open Login panel!");
        }
    }
}
