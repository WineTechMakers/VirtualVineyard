package org.example.entities;

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
    protected String password; //hash-иране
    //окей ли е да дърпаме обекта и да го автентикираме по паролата?
    //role

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
