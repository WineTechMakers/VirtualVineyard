package bg.tu_varna.sit.virtualvineyard.GUI;

import bg.tu_varna.sit.virtualvineyard.enums.ViewType;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Objects;

public class NavigationManager {
    private static final Logger logger = LogManager.getLogger(NavigationManager.class);
    public static void openNewWindow(ViewType viewType) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationManager.class.getResource(viewType.getFxmlFile()));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(viewType.getTitle());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            logger.error("Error loading '{}'", viewType.getTitle());
            new Alert(Alert.AlertType.ERROR, "Error loading " + viewType.getTitle()).showAndWait();
        }
    }

    public static void closeWindow(Node anyNode) {
        Stage stage = (Stage) anyNode.getScene().getWindow();
        stage.close();
    }

    public static void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void loadView(ViewType viewType, StackPane contentPane) {
        try {
            Parent view = FXMLLoader.load(
                    Objects.requireNonNull(
                            NavigationManager.class.getResource(viewType.getFxmlFile())
                    )
            );
            contentPane.getChildren().setAll(view); //replaces view
        } catch (IOException e) {
            logger.error("Error!, Cannot load panel!");
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "Cannot load panel!");
        }
    }
}
