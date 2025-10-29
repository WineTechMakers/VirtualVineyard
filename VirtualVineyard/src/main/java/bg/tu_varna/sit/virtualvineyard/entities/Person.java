package bg.tu_varna.sit.virtualvineyard.entities;

import bg.tu_varna.sit.virtualvineyard.enums.RoleType;
import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table (name = "Person")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PERSON_TYPE")
public abstract class Person
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long person_id;

    @Column(length = 50, nullable = false)
    protected String name;

    @Column(length = 10, nullable = false, unique = true)
    protected String EGN;

    @Column(length = 60, nullable = false, unique = true)
    protected String username;

    @Column(nullable = false, unique = true)
    private String password; //hash-иране
    //окей ли е да дърпаме обекта и да го автентикираме по паролата?

    @Column(nullable = false)
    protected RoleType role;

    public Person(String name, String EGN, String username, String password, RoleType role) {
        this.name = name;
        this.EGN = EGN;
        this.username = username;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
        this.role = role;
    }

    public Person() {

    }

    public String getEGN() {
        return EGN;
    }

    public void setEGN(String EGN) {
        if (EGN == null || EGN.length() != 10) {
            throw new IllegalArgumentException("EGN must be exactly 10 characters");
        }
        else {
            this.EGN = EGN;
        }
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
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, this.password);
    }
}
