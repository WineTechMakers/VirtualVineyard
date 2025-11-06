package bg.tu_varna.sit.virtualvineyard.GUI;

import bg.tu_varna.sit.virtualvineyard.enums.ViewType;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class NavigationManager {
    public static void switchScene(Node anyNode, ViewType viewType) {
        try {
            //closeWindow(anyNode);
            FXMLLoader loader = new FXMLLoader(NavigationManager.class.getResource(viewType.getFxmlFile()));
            Parent root = loader.load();

            Stage stage = (Stage) anyNode.getScene().getWindow();
            stage.setTitle(viewType.getTitle());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void openNewWindow(ViewType viewType) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationManager.class.getResource(viewType.getFxmlFile()));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(viewType.getTitle());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Error!", "Cannot load panel!");
        }
    }
}
