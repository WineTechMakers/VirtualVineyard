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

    public Person(String name, String EGN, String username, String password) {
        this.name = name;
        this.EGN = EGN;
        this.username = username;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
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
