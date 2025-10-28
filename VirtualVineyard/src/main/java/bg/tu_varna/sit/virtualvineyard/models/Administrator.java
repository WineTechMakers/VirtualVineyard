package bg.tu_varna.sit.virtualvineyard.models;

import bg.tu_varna.sit.virtualvineyard.entities.Person;
import bg.tu_varna.sit.virtualvineyard.enums.RoleType;

public class Administrator extends Person
{
    public Administrator(String name, String EGN, String username, String password) {
        super(name, EGN, username, password);
    }
}