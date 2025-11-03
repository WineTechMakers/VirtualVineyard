package bg.tu_varna.sit.virtualvineyard.dao;

import bg.tu_varna.sit.virtualvineyard.entities.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class PersonDAO extends AbstractDAO<Person> {
    public PersonDAO() {
        setEntityClass(Person.class);
    }

    public static Person authenticate(String username, String password)
    {
        EntityManagerFactory susf = null;
        EntityManager sus = null;
        try {
            susf = Persistence.createEntityManagerFactory("myPU");
            sus = susf.createEntityManager();
            CriteriaBuilder cb = sus.getCriteriaBuilder();
            CriteriaQuery<Person> cq = cb.createQuery(Person.class);
            Root<Person> Root = cq.from(Person.class);
            cq.select(Root).where(cb.equal(Root.get("username"), username));
            Person retrieved = sus.createQuery(cq).getSingleResult();
            if(!retrieved.passwordMatch(password)) {
                throw new Exception("wrong password");
            }
            return retrieved;
        }
        catch (NoResultException e)
        {
            System.out.println("No such user found");
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        finally {

            if(sus!=null) sus.close();
            if(susf!=null) susf.close();
        }
        return null;
    }

//    public static void testwriting() {
//        try {
//            EntityManagerFactory susf = Persistence.createEntityManagerFactory("myPU");
//            EntityManager sus = susf.createEntityManager();
//            if (sus == null) {
//                System.out.println("didnt work");
//                return;
//            }
//            sus.getTransaction().begin();
//            //Person person = new Administrator("John Doe", "1375707735", "johndoen", "1234");
//            Person person = new Operator("John Doe", "1375707737", "johndoeoperator", "1234567");
//            sus.persist(person);
//            sus.getTransaction().commit();
//        } catch (Exception e) {
//            System.out.println("NOpie");
//            System.out.println(e.getMessage());
//        }
//    }
//    public static Person testreading(String user, String pass)
//    {
//        try {
//            /*EntityManagerFactory susf = Persistence.createEntityManagerFactory("myPU");
//            EntityManager sus = susf.createEntityManager();
//            Administrator retrievedProduct = sus.find(Administrator.class, 2L);
//            System.out.println(retrievedProduct.getEGN());
//            return retrievedProduct;*/
//            EntityManagerFactory susf = Persistence.createEntityManagerFactory("myPU");
//            EntityManager sus = susf.createEntityManager();
//            CriteriaBuilder cb = sus.getCriteriaBuilder();
//            CriteriaQuery<Administrator> cq = cb.createQuery(Administrator.class);
//            Root<Administrator> adminRoot = cq.from(Administrator.class);
//
//            cq.select(adminRoot).where(cb.equal(adminRoot.get("username"), user));
//            Administrator retrievedAdmin = sus.createQuery(cq).getSingleResult();
//            System.out.println(retrievedAdmin.getEGN());
//            return retrievedAdmin;
//        }
//        catch(Exception e)
//        {
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }
}
