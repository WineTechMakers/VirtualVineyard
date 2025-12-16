package bg.tu_varna.sit.virtualvineyard.entities;

import bg.tu_varna.sit.virtualvineyard.GUI.NavigationManager;
import bg.tu_varna.sit.virtualvineyard.Normalizer;
import jakarta.persistence.*;
import javafx.scene.control.Alert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

@Entity
@Table (name = "Person")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PERSON_TYPE")
public abstract class Person implements Normalizer
{
    //рефлекция - директно ползва без нужда от getter, setter и параметризирани конструктури
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

    public Person() {

    }

    public Person(String name, String EGN, String username, String password) {
        this.name = name.trim();
        setEGN(EGN);
        setUsername(username);
        setPassword(password);
    }

    public Person(Person other) {
        this.person_id = other.person_id;
        this.name = other.name;
        this.EGN = other.EGN;
        this.username = other.username;
        this.password = other.password;
    }

    public Long getPerson_id() {
        return person_id;
    }

    public String getName() {
        return name;
    }

    public String getEGN() {
        return EGN;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPersonType() { return this.getClass().getSimpleName(); }

    public void setName(String name) {
        this.name = name.trim();
    }

    public void setEGN(String EGN) {
        if (EGN == null || EGN.length() != 10 || !EGN.matches("\\d+")) {
            throw new IllegalArgumentException("EGN must be exactly 10 characters");
        }
        else {
            this.EGN = EGN;
        }
    }

    public void setUsername(String username) {
        //validation of text fields
        if(!isEnglishAndNumbersOnly(username)){
            throw new IllegalArgumentException("Allowed latin characters and numbers only!");
        }
        this.username = username.trim();
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    @Override
    public String toString() {
        return this.username;
    }

    public boolean passwordMatch(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, this.password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(username, person.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }
}
