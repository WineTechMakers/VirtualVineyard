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
            passwordTextField.setText(selected.getPassword());
            roleComboBox.setValue(selected instanceof Operator ? "Operator" : "Host");
        }
    }

    @FXML
    public void onEditButtonClick() {
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

        if (newRole == null){
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "Please select a role!");
            return;
        }

        PersonDAO personDAO = new PersonDAO();

        if ((selected instanceof Host && "Host".equalsIgnoreCase(newRole)) ||
                (selected instanceof Operator && "Operator".equalsIgnoreCase(newRole))) {
            selected.setName(name);
            selected.setEGN(egn);
            selected.setUsername(username);
            selected.setPassword(password);
            personDAO.update(selected);
            NavigationManager.showAlert(Alert.AlertType.INFORMATION, "Success", "User updated successfully!");
            return;
        }

        if (name.isEmpty() || egn.isEmpty() || username.isEmpty() || password.isEmpty()) {
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "All fields must be filled!");
            return;
        }

        Person newPerson;
        if ("Host".equalsIgnoreCase(newRole))
            newPerson = new Host();
        else if ("Operator".equalsIgnoreCase(newRole))
            newPerson = new Operator();
        else {
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "Invalid role!");
            return;
        }

        // set updated data
        newPerson.setName(name);
        newPerson.setEGN(egn);
        newPerson.setUsername(username);
        newPerson.setPassword(password);

        // delete old record and save new one
        personDAO.delete(selected);
        boolean saved = personDAO.create(newPerson);

        if (saved) {
            NavigationManager.showAlert(Alert.AlertType.INFORMATION, "Success", "User updated successfully!");
        } else {
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "Could not update user.");
        }
    }

    @FXML
    public void onBackButtonClick() {
        try {
            NavigationManager.closeWindow(nameTextField);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "Cannot open Admin panel!");
        }
    }
}
