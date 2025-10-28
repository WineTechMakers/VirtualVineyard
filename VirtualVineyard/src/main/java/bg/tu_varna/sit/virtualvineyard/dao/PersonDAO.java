package bg.tu_varna.sit.virtualvineyard.dao;

import bg.tu_varna.sit.virtualvineyard.entities.*;
import bg.tu_varna.sit.virtualvineyard.models.Administrator;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class PersonDAO {
    public static void testwriting() {
        EntityManager sus;
        try (EntityManagerFactory susf = Persistence.createEntityManagerFactory("myPU")) {
            sus = susf.createEntityManager();
        }
        sus.getTransaction().begin();
        Person person = new Administrator("John Doe", "0141240945", "johndoe", "1234");
        sus.persist(person);
        sus.getTransaction().commit();
    }
    public static Person testreading(String user, String pass)
    {
        try {
            /*EntityManagerFactory susf = Persistence.createEntityManagerFactory("myPU");
            EntityManager sus = susf.createEntityManager();
            Administrator retrievedProduct = sus.find(Administrator.class, 2L);
            System.out.println(retrievedProduct.getEGN());
            return retrievedProduct;*/
            EntityManagerFactory susf = Persistence.createEntityManagerFactory("myPU");
            EntityManager sus = susf.createEntityManager();
            CriteriaBuilder cb = sus.getCriteriaBuilder();
            CriteriaQuery<Administrator> cq = cb.createQuery(Administrator.class);
            Root<Administrator> adminRoot = cq.from(Administrator.class);

            cq.select(adminRoot).where(cb.equal(adminRoot.get("username"), user));
            Administrator retrievedAdmin = sus.createQuery(cq).getSingleResult();
            System.out.println(retrievedAdmin.getEGN());
            return retrievedAdmin;
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            System.out.println("KUR");
        }
        return null;
    }

    public static Person authenticate(String username, String password)
    {
        EntityManagerFactory susf = null;
        EntityManager sus = null;
        try {
            susf = Persistence.createEntityManagerFactory("myPU");
            sus = susf.createEntityManager();
            CriteriaBuilder cb = sus.getCriteriaBuilder();
            CriteriaQuery<Administrator> cq = cb.createQuery(Administrator.class);
            Root<Administrator> adminRoot = cq.from(Administrator.class);

            cq.select(adminRoot).where(cb.equal(adminRoot.get("username"), username));
            Administrator retrievedAdmin = sus.createQuery(cq).getSingleResult();
            if(retrievedAdmin.passwordMatch(password))
                return retrievedAdmin;
            else
                return null;
        }
        catch (NoResultException e)
        {
            System.out.println("No such user found");
            return null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        finally {

            if(sus!=null) sus.close();
            if(susf!=null) susf.close();
        }
        return null;
    }
}