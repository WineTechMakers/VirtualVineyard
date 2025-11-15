package bg.tu_varna.sit.virtualvineyard.GUI.operator;

import bg.tu_varna.sit.virtualvineyard.GUI.NavigationManager;
import bg.tu_varna.sit.virtualvineyard.dao.WineDAO;
import bg.tu_varna.sit.virtualvineyard.entities.*;
import bg.tu_varna.sit.virtualvineyard.enums.ViewType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListWinesController {
    @FXML private TableView<Wine> winesTable;
    @FXML private TableColumn<Wine, String> wineNameColumn;
    @FXML private TableColumn<Wine, Void> editColumn;
    @FXML private TableColumn<Wine, Void> deleteColumn;

    private final WineDAO wineDAO = new WineDAO();

    @FXML
    public void initialize() {
        wineNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        ObservableList<Wine> wines = FXCollections.observableArrayList(wineDAO.findAll());
        winesTable.setItems(wines);

        //double click row -> show grapes
        winesTable.setRowFactory(tv -> {
            TableRow<Wine> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Wine wine = row.getItem();
                    showGrapes(wine);
                }
            });
            return row;
        });

        //edit column
        editColumn.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            {
                editButton.setOnAction(event -> {
                    Wine wine = getTableView().getItems().get(getIndex());
                    openEditWine(wine);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : editButton);
            }
        });

        //delete column
        deleteColumn.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Wine wine = getTableView().getItems().get(getIndex());
                    deleteWine(wine);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteButton);
            }
        });
    }

    private void showGrapes(Wine wine) {
        StringBuilder sb = new StringBuilder();
        for (WineGrape wg : wine.getWineGrapes()) {
            sb.append(wg.getGrape().getName())
                    .append(" - ")
                    .append(wg.getPercentage())
                    .append("%\n");
        }
        NavigationManager.showAlert(Alert.AlertType.INFORMATION, "Grapes in " + wine.getName(), sb.toString());
    }

    private void openEditWine(Wine wine) {
        //to do: modify AddWineController to accept an existing Wine object
        NavigationManager.openNewWindow(ViewType.ADD_WINE); // + Wine wine
    }

    private void deleteWine(Wine wine) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Wine");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to delete " + wine.getName() + "?");
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                wineDAO.delete(wine);
                winesTable.getItems().remove(wine);
            }
        });
    }
}
