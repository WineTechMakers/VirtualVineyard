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
}
