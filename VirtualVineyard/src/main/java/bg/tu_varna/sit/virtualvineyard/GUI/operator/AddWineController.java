package bg.tu_varna.sit.virtualvineyard.GUI.operator;

import bg.tu_varna.sit.virtualvineyard.dao.GrapeDAO;
import bg.tu_varna.sit.virtualvineyard.dao.WineDAO;
import bg.tu_varna.sit.virtualvineyard.dao.WineGrapeDAO;
import bg.tu_varna.sit.virtualvineyard.entities.Grape;
import bg.tu_varna.sit.virtualvineyard.entities.Wine;
import bg.tu_varna.sit.virtualvineyard.entities.WineGrape;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class AddWineController {
    @FXML
    public TextField nameTextField;
    @FXML
    public ComboBox<Grape> grapeComboBox;
    @FXML
    public Spinner<Integer> percentageSpinner;
    @FXML
    public TableView<WineGrape> recipeTable;
    @FXML
    public TableColumn<WineGrape, String> grapeCol;
    @FXML
    public TableColumn<WineGrape, Integer> percentageCol;

    private ObservableList<WineGrape> recipeList = FXCollections.observableArrayList(); //UI updates automatically when we add/remove items
    private int remainingPercentage = 100;

    @FXML
    public void initialize() {
        //Spinner: 0 to 100, step = 5
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 100, 5);
        percentageSpinner.setValueFactory(valueFactory);

        loadGrapes();

        //table binding
        grapeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getGrape().getName()));
        percentageCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPercentage()).asObject());

        recipeTable.setItems(recipeList);
    }

    private void loadGrapes() {
        GrapeDAO grapeDAO = new GrapeDAO();
        List<Grape> grapes = grapeDAO.findAll();
        grapeComboBox.setItems(FXCollections.observableArrayList(grapes));

        //displayed in the dropdown -> show usernames instead of toString()
        grapeComboBox.setCellFactory(listView -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Grape grape, boolean empty) {
                super.updateItem(grape, empty);
                setText(empty || grape == null ? null : grape.getName());
            }
        });

        //displayed when selected
        grapeComboBox.setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Grape grape, boolean empty) {
                super.updateItem(grape, empty);
                setText(empty || grape == null ? null : grape.getName());
            }
        });
    }

    @FXML
    public void onAddGrapeClick() {
        Grape selectedGrape = grapeComboBox.getValue();
        int pct = percentageSpinner.getValue();

        if (selectedGrape == null || pct <= 0) return;

        WineGrape wg = new WineGrape(null, selectedGrape, pct); // wine = null for now
        recipeList.add(wg);

        remainingPercentage -= pct;

        percentageSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, remainingPercentage, remainingPercentage)
        );

        grapeComboBox.getSelectionModel().clearSelection();
    }

    @FXML
    public void onAddWineClick() {
        if (remainingPercentage != 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Recipe incomplete");
            alert.setContentText("The percentages must add up to 100%.");
            alert.show();
            return;
        }

        String wineName = nameTextField.getText();
        if (wineName == null || wineName.isBlank()) return;

        Wine wine = new Wine(wineName);
        new WineDAO().create(wine);

        for (WineGrape wg : recipeList) {
            wg.setWine(wine); //assigning real wine
            new WineGrapeDAO().create(wg);
        }

        recipeList.clear();
        remainingPercentage = 100;

        percentageSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 100)
        );
    }
}