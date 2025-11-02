package bg.tu_varna.sit.virtualvineyard.GUI.controllers;

import bg.tu_varna.sit.virtualvineyard.dao.PersonDAO;
import bg.tu_varna.sit.virtualvineyard.entities.Person;
import bg.tu_varna.sit.virtualvineyard.models.Administrator;
import bg.tu_varna.sit.virtualvineyard.models.Host;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {
    public TextField usernameTextField;
    public TextField passwordTextField;
    @FXML
    private Label loggedText;

    @FXML
    protected void onLogInButtonClick() {
        Person user = PersonDAO.authenticate(usernameTextField.getText(), passwordTextField.getText());
        //Person user = PersonDAO.authenticate("johndoeoperator", "1234567");
        if(user != null) {
            loggedText.setText("Welcome to our Virtual Vineyard!");
            if (user instanceof Administrator) {
                openAdminWindow();
            } else if (user instanceof Host) {
                openUserWindow();
            }
            else{
                //
            }
            ((Stage) usernameTextField.getScene().getWindow()).close(); // close login window
        }
        else {
            loggedText.setText("Invalid username or password!");
        }
    }

    private void openAdminWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-view.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Admin Panel");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openUserWindow() {

    }
}