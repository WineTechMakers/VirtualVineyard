module bg.tu_varna.sit.virtualvineyard {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires spring.security.crypto;

    opens bg.tu_varna.sit.virtualvineyard to javafx.fxml;
    exports bg.tu_varna.sit.virtualvineyard;
    exports bg.tu_varna.sit.virtualvineyard.GUI;
    opens bg.tu_varna.sit.virtualvineyard.GUI to javafx.fxml;
    exports bg.tu_varna.sit.virtualvineyard.states;
    opens bg.tu_varna.sit.virtualvineyard.states to javafx.fxml;
    exports bg.tu_varna.sit.virtualvineyard.models;
    opens bg.tu_varna.sit.virtualvineyard.models to org.hibernate.orm.core;
    exports bg.tu_varna.sit.virtualvineyard.enums;
    opens bg.tu_varna.sit.virtualvineyard.enums to javafx.fxml;
    exports bg.tu_varna.sit.virtualvineyard.entities;
    opens bg.tu_varna.sit.virtualvineyard.entities to org.hibernate.orm.core;
}