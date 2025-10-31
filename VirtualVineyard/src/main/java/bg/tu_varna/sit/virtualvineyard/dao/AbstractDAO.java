package bg.tu_varna.sit.virtualvineyard.dao;

import jakarta.persistence.*;

import java.util.List;

public abstract class AbstractDAO<T> { // extends Serializable?
    private Class<T> entityClass;

    @PersistenceContext
    private EntityManager entityManager;

    public final void setEntityClass(Class<T> entityClassToSet){
        this.entityClass = entityClassToSet;
    }

    public T findOne(long id){
        return entityManager.find(entityClass, id);
    }
    public List<T> findAll(){
        return entityManager.createQuery("from " + entityClass.getName())
                .getResultList();
    }

    public void create(T entity){
        entityManager.persist(entity);
    }

    public T update(T entity){
        return entityManager.merge(entity);
    }

    public void delete(T entity){
        entityManager.remove(entity);
    }
    public void deleteById(long entityId){
        T entity = findOne(entityId);
        delete(entity);
    }
}
