package bg.tu_varna.sit.virtualvineyard.dao;

import jakarta.persistence.*;

import java.util.List;

public abstract class AbstractDAO<T> { // extends Serializable?
    private Class<T> entityClass;

    @PersistenceContext
    private EntityManager entityManager;

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

    public boolean update(T entity){
        if(entity == null)
            throw new IllegalArgumentException("Entity cannot be null");
        completeAction(() -> entityManager.merge(entity));
        return true;
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

    private void completeAction(Runnable action)
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
            throw new RuntimeException(e);
        }
    }
}
