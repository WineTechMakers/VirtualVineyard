package bg.tu_varna.sit.virtualvineyard.GUI;

import bg.tu_varna.sit.virtualvineyard.dao.PersonDAO;
import bg.tu_varna.sit.virtualvineyard.entities.Person;
import bg.tu_varna.sit.virtualvineyard.models.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class EditUsersController {
    @FXML
    public ComboBox<Person> userComboBox;
    @FXML
    public ComboBox<String> roleComboBox;
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField EGNTextField;
    @FXML
    public TextField usernameTextField;
    @FXML
    public TextField passwordTextField;

    @FXML
    public void initialize() {
        PersonDAO personDAO = new PersonDAO();
        userComboBox.getItems().addAll(personDAO.findAll());

        roleComboBox.getItems().addAll("Host", "Operator");

        //displayed in the dropdown -> show usernames instead of toString()
        userComboBox.setCellFactory(cb -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Person person, boolean empty) {
                super.updateItem(person, empty);
                setText(empty || person == null ? null : person.getUsername());
            }
        });

        //displayed when selected
        userComboBox.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Person person, boolean empty) {
                super.updateItem(person, empty);
                setText(empty || person == null ? null : person.getUsername());
            }
        });
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

        if (name.isEmpty() || egn.isEmpty() || username.isEmpty() || password.isEmpty() || newRole == null) {
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "All fields must be filled!");
            return;
        }

        PersonDAO personDAO = new PersonDAO();
        boolean roleChanged = false;

        String currentRole;
        switch (selected) {
            case Host host -> currentRole = "Host";
            case Operator operator -> currentRole = "Operator";
            default -> {
                NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "Unsupported current user role!");
                return;
            }
        }

        if (!currentRole.equalsIgnoreCase(newRole)) {
            roleChanged = true;
        }

        try {
            selected.setName(name);
            selected.setEGN(egn);
            selected.setUsername(username);
            selected.setPassword(password);

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
