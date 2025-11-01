package bg.tu_varna.sit.virtualvineyard.models;

import bg.tu_varna.sit.virtualvineyard.entities.Person;
import bg.tu_varna.sit.virtualvineyard.enums.RoleType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("HOST")
public class Host extends Person {

    public Host(String name, String EGN, String username, String password) {
        super(name, EGN, username, password);
    }
    public Host()
    {

    }
}
