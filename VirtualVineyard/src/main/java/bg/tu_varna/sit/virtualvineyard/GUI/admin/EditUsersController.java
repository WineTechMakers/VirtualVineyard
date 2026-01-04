package bg.tu_varna.sit.virtualvineyard.GUI.admin;

import bg.tu_varna.sit.virtualvineyard.GUI.NavigationManager;
import bg.tu_varna.sit.virtualvineyard.dao.PersonDAO;
import bg.tu_varna.sit.virtualvineyard.entities.Person;
import bg.tu_varna.sit.virtualvineyard.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class EditUsersController {
    @FXML public ComboBox<Person> userComboBox;
    @FXML public ComboBox<String> roleComboBox;
    @FXML public TextField nameTextField;
    @FXML public TextField EGNTextField;
    @FXML public TextField usernameTextField;
    @FXML public PasswordField passwordTextField;

    @FXML
    public void initialize() {
        PersonDAO personDAO = new PersonDAO();
        Person admin = PersonDAO.findByUsername("admin");

        List<Person> users = personDAO.findAll();
        users.remove(admin);
        userComboBox.getItems().addAll(users);
        roleComboBox.getItems().addAll("Host", "Operator");
    }

    @FXML
    public void onUserSelected() {
        Person selected = userComboBox.getValue();
        if (selected != null) {
            nameTextField.setText(selected.getName());
            EGNTextField.setText(selected.getEGN());
            usernameTextField.setText(selected.getUsername());
            passwordTextField.setText(""); //no need to show encoded password
            roleComboBox.setValue(selected instanceof Operator ? "Operator" : "Host");
        }
    }

    @FXML
    public void onSaveButtonClick() {
        Person selected = userComboBox.getValue();
        if (selected == null) {
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "Please select a user to edit!");
            return;
        }

        String name = nameTextField.getText();
        String egn = EGNTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String newRole = roleComboBox.getValue();

        if (name.isEmpty() || egn.isEmpty() || username.isEmpty() || newRole == null) { //|| password.isEmpty()
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "All fields must be filled!");
            return;
        }

        PersonDAO personDAO = new PersonDAO();
        boolean roleChanged = false;

        String currentRole;
        try {

            switch (selected) {
                case Host host -> currentRole = "Host";
                case Operator operator -> currentRole = "Operator";
                default -> {
                    NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "Unsupported current user role!");
                    return;
                }
            }
        }
        catch (Exception e) {
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "Error: " + e.getMessage());
            return;
        }

        if (!currentRole.equalsIgnoreCase(newRole)) {
            roleChanged = true;
        }

        try {
            selected.setName(name);
            selected.setEGN(egn);
            selected.setUsername(username);
            if(!password.isEmpty()) {
                selected.setPassword(password);
            }
            personDAO.update(selected);

        } catch (Exception e) {
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "Failed to update user fields: " + e.getMessage());
            return;
        }

        if (roleChanged) {
            try {
                boolean typeUpdated = personDAO.updatePersonType(selected.getPerson_id(), newRole);

                if (typeUpdated) {
                    NavigationManager.showAlert(Alert.AlertType.INFORMATION, "Success", "User fields and role updated successfully!");
                } else {
                    NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "User fields updated, but role change failed.");
                }
            } catch (Exception e) {
                NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "Failed to change user role (type): " + e.getMessage());
            }

        } else {
            NavigationManager.showAlert(Alert.AlertType.INFORMATION, "Success", "User fields updated successfully.");
        }
    }
}
