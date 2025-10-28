package org.example.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrators")
public class Administrator extends Person
{
    public Administrator()
    {

    }
    public Administrator(String name, String EGN, String username, String password)
    {
        this.EGN = EGN;
        this.name = name;
        this.username = username;
        this.password = password;
    }
}