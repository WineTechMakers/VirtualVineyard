package bg.tu_varna.sit.virtualvineyard.models;

import bg.tu_varna.sit.virtualvineyard.entities.Person;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("OPERATOR")
public class Operator extends Person {
    public Operator(String name, String EGN, String username, String password) {
        super(name, EGN, username, password);
    }

    public Operator()
    {

    }

    public Operator(Person other) {
        super(other);
    }
}
