package bg.tu_varna.sit.virtualvineyard.GUI.operator;

import bg.tu_varna.sit.virtualvineyard.chain.BottlingFactory;
import bg.tu_varna.sit.virtualvineyard.dao.WineDAO;
import bg.tu_varna.sit.virtualvineyard.entities.BottledWine;
import bg.tu_varna.sit.virtualvineyard.dao.BottledWineDAO;
import bg.tu_varna.sit.virtualvineyard.entities.Wine;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class BottleWinesController {
    @FXML
    private ComboBox<Wine> wineComboBox;

    @FXML
    private TableView<BottledWine> bottledWinesTable;

    @FXML
    private TableColumn<BottledWine, Integer> bottleVolumeColumn;

    @FXML
    private TableColumn<BottledWine, Integer> quantityColumn;

    @FXML
    private TableColumn<BottledWine, String> warehouseNameColumn;

    private final BottledWineDAO bottledWineDAO = new BottledWineDAO();
    private final WineDAO wineDAO = new WineDAO();

    @FXML
    public void initialize() {
        ObservableList<Wine> wines = FXCollections.observableArrayList(wineDAO.findAll());
        wineComboBox.setItems(wines);

        bottleVolumeColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getBottle().getVolume().getVolume()).asObject()
        );

        quantityColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject()
        );

        warehouseNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getWarehouse().getName())
        );
    }

    @FXML
    public void loadBottledWineData() {
        Wine selectedWine = wineComboBox.getSelectionModel().getSelectedItem();
        if (selectedWine != null) {
            List<BottledWine> results = bottledWineDAO.findByWine(selectedWine);
            //BottlingFactory factory = new BottlingFactory();
            bottledWinesTable.setItems(FXCollections.observableArrayList(results));
        } else {
            bottledWinesTable.getItems().clear();
        }
    }
}
