package bg.tu_varna.sit.virtualvineyard.models;

import bg.tu_varna.sit.virtualvineyard.dao.PersonDAO;
import bg.tu_varna.sit.virtualvineyard.dao.WarehouseDAO;
import bg.tu_varna.sit.virtualvineyard.entities.Person;
import bg.tu_varna.sit.virtualvineyard.entities.Warehouse;
import jakarta.persistence.NoResultException;

public class WineSystem
{
    private Person user;
    private Warehouse warehouse;

    WineSystem()
    {
        user = null;
        warehouse = null;
    }
    public void control(String input)
    {

    }
    public void authenticate(String username, String password)
    {
        Person user = PersonDAO.authenticate(username, password);
        if(user==null)
            throw new NoResultException("no such user");
        this.user = user;
    }
    public void selectWarehouse(Long id)
    {
        WarehouseDAO wd = new WarehouseDAO();
        Warehouse res = wd.findOne(id);
        if(res==null)
            throw new NoResultException("no such warehouse");
        warehouse = res;
    }
}
