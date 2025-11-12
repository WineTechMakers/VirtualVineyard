import jakarta.persistence.*;
import org.junit.jupiter.api.*;

public abstract class JpaTestBase {
    protected EntityManagerFactory emf;
    protected EntityManager em;

    @BeforeEach
    void setUp() {
        emf = Persistence.createEntityManagerFactory("myPU"); //VirtualVineyardPU
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }

    @AfterEach
    void tearDown() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback(); //rollback to keep DB clean
        }
        em.close();
        emf.close();
    }
}
