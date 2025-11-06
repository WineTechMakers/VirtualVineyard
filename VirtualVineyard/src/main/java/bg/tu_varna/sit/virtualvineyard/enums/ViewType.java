package bg.tu_varna.sit.virtualvineyard.enums;

public enum ViewType {
    LOGIN("main-view.fxml", "Login"),
    ADMIN("admin-view.fxml", "Admin Panel"),
    OPERATOR("operator-view.fxml", "Operator Panel"),
    HOST("host-view.fxml", "Host Panel"),
    REGISTER_USER("register-user-view.fxml", "Register User"),
    EDIT_USERS("edit-users-view.fxml", "Edit Users Panel"),
    LIST_USERS("list-users-view.fxml", "List Users Panel"),
    ADD_GRAPES("add_grapes-view.fxml", "Add Grapes Panel"),
    ADD_BOTTLES("add-bottles-view.fxml", "Add Bottles Panel"),
    CHECK_HOST_INVENTORY("check-host-inventory-view.fxml", "Check Inventory Panel");

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
