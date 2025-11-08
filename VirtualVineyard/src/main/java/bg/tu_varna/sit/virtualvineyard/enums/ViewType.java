package bg.tu_varna.sit.virtualvineyard.enums;

public enum ViewType {
    LOGIN("/bg/tu_varna/sit/virtualvineyard/GUI/login/main-view.fxml", "Login"),
    ADMIN("/bg/tu_varna/sit/virtualvineyard/GUI/admin/admin-view.fxml", "Admin Panel"),
    OPERATOR("/bg/tu_varna/sit/virtualvineyard/GUI/operator/operator-view.fxml", "Operator Panel"),
    HOST("/bg/tu_varna/sit/virtualvineyard/GUI/host/host-view.fxml", "Host Panel"),
    REGISTER_USER("/bg/tu_varna/sit/virtualvineyard/GUI/admin/register-user-view.fxml", "Register User"),
    EDIT_USERS("/bg/tu_varna/sit/virtualvineyard/GUI/admin/edit-users-view.fxml", "Edit Users Panel"),
    LIST_USERS("/bg/tu_varna/sit/virtualvineyard/GUI/admin/list-users-view.fxml", "List Users Panel"),
    ADD_WAREHOUSE("/bg/tu_varna/sit/virtualvineyard/GUI/host/add-warehouse-view.fxml", "Add Warehouse Panel"),
    ADD_GRAPE("/bg/tu_varna/sit/virtualvineyard/GUI/host/add-grape-view.fxml", "Add Grape Panel"),
    ADD_BOTTLE("/bg/tu_varna/sit/virtualvineyard/GUI/host/add-bottle-view.fxml", "Add Bottle Panel"),
    CHECK_HOST_INVENTORY("/bg/tu_varna/sit/virtualvineyard/GUI/host/check-inventory-view.fxml", "Check Inventory Panel");

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
