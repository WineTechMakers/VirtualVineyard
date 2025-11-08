package bg.tu_varna.sit.virtualvineyard.GUI.login;

import bg.tu_varna.sit.virtualvineyard.GUI.NavigationManager;
import bg.tu_varna.sit.virtualvineyard.dao.PersonDAO;
import bg.tu_varna.sit.virtualvineyard.entities.Person;
import bg.tu_varna.sit.virtualvineyard.enums.ViewType;
import bg.tu_varna.sit.virtualvineyard.models.Administrator;
import bg.tu_varna.sit.virtualvineyard.models.Host;
import bg.tu_varna.sit.virtualvineyard.models.Operator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label loggedText;

    @FXML
    public void initialize() {
        usernameTextField.setText("admin");
        passwordTextField.setText("1234");
    }

    @FXML
    protected void onLogInButtonClick() {
        Person user = PersonDAO.authenticate(usernameTextField.getText(), passwordTextField.getText());
        if(user != null) {
            //loggedText.setText("Welcome to our Virtual Vineyard!");
            switch (user) {
                case Administrator administrator -> openAdminWindow();
                case Host host -> openHostWindow();
                case Operator operator -> openOperatorWindow();
                default -> {
                }
            }
            ((Stage) usernameTextField.getScene().getWindow()).close(); //close login window
        }
        else {
            loggedText.setText("Invalid username or password!");
        }
    }

    private void openAdminWindow() {
        NavigationManager.openNewWindow(ViewType.ADMIN);
    }

    private void openHostWindow() {
        NavigationManager.openNewWindow(ViewType.HOST);
    }

    private void openOperatorWindow() {
        NavigationManager.openNewWindow(ViewType.OPERATOR);
    }
}