package bg.tu_varna.sit.virtualvineyard.entities;

import bg.tu_varna.sit.virtualvineyard.enums.RoleType;
import jakarta.persistence.*;

@MappedSuperclass
public abstract class Person
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long person_id;

    @Column(nullable = false)
    protected String name;

    @Column(nullable = false,unique = true)
    protected String EGN;

    @Column(nullable = false, unique = true)
    protected String username;

    @Column(nullable = false, unique = true)
    protected String password; //hash-иране
    //окей ли е да дърпаме обекта и да го автентикираме по паролата?

    @Column(nullable = false)
    protected RoleType role;

    public Person(String name, String EGN, String username, String password) {
        this.name = name;
        this.EGN = EGN;
        this.username = username;
        this.password = password;
    }

    public String getEGN() {
        return EGN;
    }

    public String getName() {
        return name;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public boolean passwordMatch(String password)
    {
        return this.password.equals(password);
    }
}
