package bg.tu_varna.sit.virtualvineyard.GUI.operator;

import bg.tu_varna.sit.virtualvineyard.GUI.NavigationManager;
import bg.tu_varna.sit.virtualvineyard.enums.ViewType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OperatorController {
    private static final Logger logger = LogManager.getLogger(OperatorController.class);
    @FXML public Label operatorLabel;
    @FXML private StackPane contentPane;

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
    private void onListBottledWinesClick() {
        NavigationManager.loadView(ViewType.LIST_BOTTLED_WINES, contentPane);
    }

    @FXML
    private void onLogoutClick() {
        try {
            NavigationManager.closeWindow(operatorLabel);
            NavigationManager.openNewWindow(ViewType.LOGIN);
        } catch (Exception e) {
            logger.error("Unknown error occured'{}'", e.toString());
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "Cannot open Login panel!");
        }
    }
}
