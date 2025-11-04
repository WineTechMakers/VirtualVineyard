package bg.tu_varna.sit.virtualvineyard.GUI;

import bg.tu_varna.sit.virtualvineyard.enums.ViewType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class AdminController {
    @FXML
    public Label adminLabel;

    @FXML
    public void onRegisterUserButtonClick() {
        NavigationManager.openNewWindow(ViewType.REGISTER_USER);
    }

    @FXML
    public void onEditUsersButtonClick() {
        NavigationManager.openNewWindow(ViewType.EDIT_USERS);
    }

    @FXML
    public void onListUsersButtonClick() {
        NavigationManager.openNewWindow(ViewType.LIST_USERS);
    }

    @FXML
    public void onBackButtonClick() {
        try {
            NavigationManager.closeWindow(adminLabel);
            NavigationManager.openNewWindow(ViewType.LOGIN);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "Cannot open Login panel!");
        }
    }
}
