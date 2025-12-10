package bg.tu_varna.sit.virtualvineyard.dao;

import jakarta.persistence.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public abstract class AbstractDAO<T> { // extends Serializable?
    private static final Logger logger = LogManager.getLogger(AbstractDAO.class);
    private Class<T> entityClass;

    @PersistenceContext
    public static EntityManager entityManager;

    public final void setEntityClass(Class<T> entityClassToSet)
    {
        this.entityClass = entityClassToSet;
        EntityManagerFactory susf = Persistence.createEntityManagerFactory("myPU");
        entityManager = susf.createEntityManager();
    }

    public T findOne(long id){
        return entityManager.find(entityClass, id);
    }

    public List<T> findAll(){
        return entityManager.createQuery("from " + entityClass.getName())
                .getResultList();
    }

    public boolean create(T entity){
        if(entity==null)
            throw new IllegalArgumentException("Entity cannot be null");
        completeAction(() -> entityManager.persist(entity));
        return true;
    }

    public void update(T entity){
        if(entity == null)
            throw new IllegalArgumentException("Entity cannot be null");
        completeAction(() -> entityManager.merge(entity));
    }

    public void delete(T entity){
        if(entity==null)
            throw new IllegalArgumentException("Entity cannot be null");
        completeAction(() -> {
            T managed = entityManager.contains(entity) ? entity : entityManager.merge(entity);
            entityManager.remove(managed);
        });
    }
    public void deleteById(long entityId){
        T entity = findOne(entityId);
        delete(entity);
    }

    public static void completeAction(Runnable action)
    {
        EntityTransaction transaction = entityManager.getTransaction();
        try
        {
            transaction.begin();
            action.run();
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive())
                transaction.rollback();
            logger.error("Couldn't establish connection to DB");
            throw new RuntimeException(e);
        }
    }
}
