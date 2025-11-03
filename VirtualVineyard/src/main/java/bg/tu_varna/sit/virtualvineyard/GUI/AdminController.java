package bg.tu_varna.sit.virtualvineyard.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class AdminController {
    @FXML
    public void onRegisterUserClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register-user-view.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Register New User");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading register-user-view.fxml").showAndWait();
        }
    }
}
