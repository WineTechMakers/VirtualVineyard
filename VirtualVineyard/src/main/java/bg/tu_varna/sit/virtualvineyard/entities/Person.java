package bg.tu_varna.sit.virtualvineyard.entities;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table (name = "Person")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PERSON_TYPE")
public abstract class Person
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
        this.name = name;
        this.EGN = EGN;
        this.username = username;
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
        this.name = name;
    }

    public void setEGN(String EGN) {
        if (EGN == null || EGN.length() != 10) {
            throw new IllegalArgumentException("EGN must be exactly 10 characters");
        }
        else {
            this.EGN = EGN;
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person: ");
        sb.append("person_id=").append(person_id);
        sb.append(", name=").append(name);
        sb.append(", EGN=").append(EGN);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        return sb.toString();
    }

    public boolean passwordMatch(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, this.password);
    }
}
