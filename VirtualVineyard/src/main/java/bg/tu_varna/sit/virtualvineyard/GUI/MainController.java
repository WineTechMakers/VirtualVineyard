package bg.tu_varna.sit.virtualvineyard.GUI;

import bg.tu_varna.sit.virtualvineyard.dao.PersonDAO;
import bg.tu_varna.sit.virtualvineyard.entities.Person;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
        }
        else {
            loggedText.setText("Invalid username or password!");
        }
    }
}