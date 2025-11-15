package bg.tu_varna.sit.virtualvineyard.GUI.host;

import bg.tu_varna.sit.virtualvineyard.GUI.NavigationManager;
import bg.tu_varna.sit.virtualvineyard.dao.*;
import bg.tu_varna.sit.virtualvineyard.entities.*;
import bg.tu_varna.sit.virtualvineyard.enums.WarehouseContentType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddGrapeController {
    @FXML public ComboBox<Warehouse> warehouseComboBox;
    @FXML public TextField nameTextField;
    @FXML public TextField quantityTextField;
    @FXML public TextField wineYieldTextField;
    @FXML public RadioButton blackRadioButton;
    @FXML public RadioButton whiteRadioButton;

    private final WarehouseDAO warehouseDAO = new WarehouseDAO();
    private final GrapeDAO grapeDAO = new GrapeDAO();

    private final ToggleGroup colorGroup = new ToggleGroup();

    @FXML
    public void initialize() {
        ObservableList<Warehouse> grapeWarehouses = FXCollections.observableArrayList(warehouseDAO.findByContentType(WarehouseContentType.GRAPE_ONLY));
        warehouseComboBox.setItems(grapeWarehouses);

        blackRadioButton.setToggleGroup(colorGroup);
        whiteRadioButton.setToggleGroup(colorGroup);
        blackRadioButton.setSelected(true); //default
    }

    @FXML
    private void onAddGrapeClick() {
        Warehouse warehouse = warehouseComboBox.getValue();
        String name = nameTextField.getText();
        String quantityText = quantityTextField.getText();
        String yieldText = wineYieldTextField.getText();

        if (warehouse == null || name.isEmpty() || quantityText.isEmpty() || yieldText.isEmpty()) {
            NavigationManager.showAlert(Alert.AlertType.WARNING, "Missing Data", "Please fill all fields and select a warehouse.");
            return;
        }

        int quantity;
        double wineYield;
        try {
            quantity = Integer.parseInt(quantityText);
            wineYield = Double.parseDouble(yieldText);
        } catch (NumberFormatException e) {
            NavigationManager.showAlert(Alert.AlertType.ERROR, "Invalid Input", "Quantity must be integer and wine yield must be a number.");
            return;
        }

        boolean isBlack = blackRadioButton.isSelected();

        Grape grape = new Grape(name, quantity, isBlack, wineYield, warehouse);
        grapeDAO.create(grape);

        NavigationManager.showAlert(Alert.AlertType.INFORMATION, "Success", "Grape added successfully!");

        warehouseComboBox.setValue(null);
        nameTextField.clear();
        quantityTextField.clear();
        wineYieldTextField.clear();
        blackRadioButton.setSelected(true);
    }
}
