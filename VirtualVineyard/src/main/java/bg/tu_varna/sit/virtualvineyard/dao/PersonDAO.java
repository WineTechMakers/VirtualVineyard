package bg.tu_varna.sit.virtualvineyard.dao;

import bg.tu_varna.sit.virtualvineyard.entities.Person;
import bg.tu_varna.sit.virtualvineyard.models.Administrator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PersonDAO extends AbstractDAO<Person> {
    private static final Logger logger = LogManager.getLogger(PersonDAO.class);

    public PersonDAO() {
        setEntityClass(Person.class);
    }

    public static Person findByUsername(String username) {
        try (EntityManagerFactory susf = Persistence.createEntityManagerFactory("myPU"); EntityManager sus = susf.createEntityManager()) {
            CriteriaBuilder cb = sus.getCriteriaBuilder();
            CriteriaQuery<Person> cq = cb.createQuery(Person.class);
            Root<Person> Root = cq.from(Person.class);
            cq.select(Root).where(cb.equal(Root.get("username"), username));
            Person retrieved = sus.createQuery(cq).getSingleResult();
            return retrieved;
        } catch (NoResultException e) {
            logger.error("User '{}' isn't registered", username);
        } catch (Exception e) {
            logger.error("Unknown error '{}'", e.toString());
        }
        return null;
    }

    public boolean updatePersonType(Long personId, String newType) {
        if(personId == null || newType == null)
            throw new IllegalArgumentException("Arguments cannot be null");

        completeAction(() -> {
            entityManager.createNativeQuery(
                            "UPDATE person SET person_type = :newType WHERE person_id = :id"
                    )
                    .setParameter("newType", newType.toUpperCase()) //ignore red
                    .setParameter("id", personId) //ignore red
                    .executeUpdate();
        });
        return true;
    }

    public static Person authenticate(String username, String password)
    {
        try {
            Person retrieved = findByUsername(username);
            if(retrieved == null)
                throw new NoResultException("");
            if (!retrieved.passwordMatch(password))
                throw new Exception("wrong password");
            logger.info("Authentication successful for user '{}'", username);
            return retrieved;
        } catch (NoResultException e) {
            logger.error("User '{}' couldn't be found", username);
        } catch (Exception e) {
            logger.error("During authentication for user '{}' password didn't verify", username);
        }
        return null;
    }
}