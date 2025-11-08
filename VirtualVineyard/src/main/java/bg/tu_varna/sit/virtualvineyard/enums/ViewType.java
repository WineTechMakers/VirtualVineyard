package bg.tu_varna.sit.virtualvineyard.enums;

public enum ViewType {
    LOGIN("/bg/tu_varna/sit/virtualvineyard/GUI/main-view.fxml", "Login"),
    ADMIN("/bg/tu_varna/sit/virtualvineyard/GUI/admin-view.fxml", "Admin Panel"),
    OPERATOR("/bg/tu_varna/sit/virtualvineyard/GUI/operator-view.fxml", "Operator Panel"),
    HOST("/bg/tu_varna/sit/virtualvineyard/GUI/host-view.fxml", "Host Panel"),
    REGISTER_USER("/bg/tu_varna/sit/virtualvineyard/GUI/register-user-view.fxml", "Register User"),
    EDIT_USERS("/bg/tu_varna/sit/virtualvineyard/GUI/edit-users-view.fxml", "Edit Users Panel"),
    LIST_USERS("/bg/tu_varna/sit/virtualvineyard/GUI/list-users-view.fxml", "List Users Panel"),
    ADD_GRAPES("/bg/tu_varna/sit/virtualvineyard/GUI/add-grapes-view.fxml", "Add Grapes Panel"),
    ADD_BOTTLES("/bg/tu_varna/sit/virtualvineyard/GUI/add-bottles-view.fxml", "Add Bottles Panel"),
    CHECK_HOST_INVENTORY("/bg/tu_varna/sit/virtualvineyard/GUI/check-host-inventory-view.fxml", "Check Inventory Panel");

    private final String fxmlFile;
    private final String title;

    ViewType(String fxmlFile, String title) {
        this.fxmlFile = fxmlFile;
        this.title = title;
    }

    public String getFxmlFile() {
        return fxmlFile;
    }

    public String getTitle() {
        return title;
    }
}
