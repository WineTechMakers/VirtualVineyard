package bg.tu_varna.sit.virtualvineyard.GUI.operator;

import bg.tu_varna.sit.virtualvineyard.dao.BottledWineDAO;
import bg.tu_varna.sit.virtualvineyard.entities.BottledWine;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class ListBottledWinesController {
    @FXML private TableView<BottledWine> bottledWinesTable;
    @FXML private TableColumn<BottledWine, String> wineNameColumn;
    @FXML private TableColumn<BottledWine, Integer> bottleVolumeColumn;
    @FXML private TableColumn<BottledWine, Integer> quantityColumn;
    @FXML private TableColumn<BottledWine, String> warehouseColumn;

    private final BottledWineDAO bottledWineDAO = new BottledWineDAO();

    @FXML
    public void initialize() {
        setupTableColumns();
        loadData();
    }

    private void setupTableColumns() {
        //BottledWine -> Wine -> Name)
        wineNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getWine().getName()));

        //BottledWine -> Bottle -> Volume (Enum) -> getVolume())
        bottleVolumeColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getBottle().getVolume().getVolume()).asObject());

        quantityColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());

        //BottledWine -> Warehouse -> Name)
        warehouseColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getWarehouse().getName()));
    }

    private void loadData() {
        List<BottledWine> data = bottledWineDAO.findAll();

        ObservableList<BottledWine> observableList = FXCollections.observableArrayList(data);
        bottledWinesTable.setItems(observableList);
    }
}
