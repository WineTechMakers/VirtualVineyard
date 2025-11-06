package bg.tu_varna.sit.virtualvineyard.GUI;

import bg.tu_varna.sit.virtualvineyard.enums.ViewType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class AdminController {
    @FXML
    private StackPane contentPane;
    @FXML
    public Label adminLabel;

    @FXML
    private void onRegisterUserClick() {
        NavigationManager.loadView(ViewType.REGISTER_USER, contentPane);
    }

    @FXML
    private void onEditUsersClick() {
        NavigationManager.loadView(ViewType.EDIT_USERS, contentPane);
    }

    @FXML
    private void onListUsersClick() {
        NavigationManager.loadView(ViewType.LIST_USERS, contentPane);
    }

    @FXML
    private void onRunHostClick() {
        NavigationManager.loadView(ViewType.HOST, contentPane);
    }

    @FXML
    private void onRunOperatorClick() {
        NavigationManager.loadView(ViewType.OPERATOR, contentPane);
    }

    @FXML
    private void onLogoutClick() {
        try {
            NavigationManager.closeWindow(adminLabel);
            NavigationManager.openNewWindow(ViewType.LOGIN);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "Cannot open Login panel!");
        }
    }
}
