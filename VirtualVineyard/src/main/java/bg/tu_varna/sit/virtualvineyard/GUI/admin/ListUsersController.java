package bg.tu_varna.sit.virtualvineyard.GUI.admin;

import bg.tu_varna.sit.virtualvineyard.dao.PersonDAO;
import bg.tu_varna.sit.virtualvineyard.entities.Person;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListUsersController {
    @FXML public TableView<Person> personTableView;
    @FXML public TableColumn<Person, Long> idCol;
    @FXML public TableColumn<Person, String> roleCol;
    @FXML public TableColumn<Person, String> nameCol;
    @FXML public TableColumn<Person, String> egnCol;
    @FXML public TableColumn<Person, String> usernameCol;

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("person_id"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("personType"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        egnCol.setCellValueFactory(new PropertyValueFactory<>("EGN"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        loadPeople();
    }

    private void loadPeople() {
        PersonDAO personDAO = new PersonDAO();
        var people = personDAO.findAll();
        personTableView.getItems().setAll(people);
    }
}
