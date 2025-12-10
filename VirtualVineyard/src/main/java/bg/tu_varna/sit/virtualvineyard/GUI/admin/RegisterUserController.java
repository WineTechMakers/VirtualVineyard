package bg.tu_varna.sit.virtualvineyard.GUI.admin;

import bg.tu_varna.sit.virtualvineyard.GUI.NavigationManager;
import bg.tu_varna.sit.virtualvineyard.Normalizer;
import bg.tu_varna.sit.virtualvineyard.entities.*;
import bg.tu_varna.sit.virtualvineyard.models.*;
import bg.tu_varna.sit.virtualvineyard.dao.PersonDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;

public class RegisterUserController{
    @FXML private TextField nameTextField;
    @FXML private TextField EGNTextField;
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private ComboBox<String> roleComboBox;

    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll("Operator", "Host");
    }

    @FXML
    public void onRegisterButtonClick() {
        String name = nameTextField.getText();
        String egn = EGNTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String role = roleComboBox.getValue();

        if (name.isEmpty() || egn.isEmpty() || username.isEmpty() || password.isEmpty() || role == null) {
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "All fields must be filled!");
            return;
        }

        Person person = null;
        try {
            if ("Operator".equalsIgnoreCase(role)) {
                person = new Operator(name, egn, username, password);
            } else if ("Host".equalsIgnoreCase(role)) {
                person = new Host(name, egn, username, password);
            } else {
                NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "Please select a role!");
                return;
            }
        } catch (Exception e){
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", e.getMessage());
            return;
        }

        //save in database
        PersonDAO personDAO = new PersonDAO();
        boolean saved = false;
        try {
            saved = personDAO.create(person);
        } catch (Exception e) {
            LogManager.getLogger().error(e.getMessage());
        }

        if (saved) {
            NavigationManager.showAlert(Alert.AlertType.INFORMATION, "Success", "User registered successfully!");
            nameTextField.clear();
            EGNTextField.clear();
            usernameTextField.clear();
            passwordTextField.clear();
            roleComboBox.getSelectionModel().clearSelection();
        } else {
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "Username or EGN already exists!");
        }
    }
}
