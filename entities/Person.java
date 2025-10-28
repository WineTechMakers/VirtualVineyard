package org.example.Entities;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class Person
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    protected String name;
    @Column(nullable = false,unique = true)
    protected String EGN;
    @Column(nullable = false, unique = true)
    protected String username;
    @Column(nullable = false, unique = true)
    protected String password;

    public String getEGN() {
        return EGN;
    }

    public String getName() {
        return name;
    }

    public boolean passwordMatch(String password)
    {
        return this.password.equals(password);
    }
}
