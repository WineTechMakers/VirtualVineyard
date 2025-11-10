package bg.tu_varna.sit.virtualvineyard;

import bg.tu_varna.sit.virtualvineyard.dao.PersonDAO;
import bg.tu_varna.sit.virtualvineyard.entities.Person;
import bg.tu_varna.sit.virtualvineyard.models.Administrator;

public class AdminInit
{
    public static void init()
    {
        Person test = PersonDAO.authenticate("admin", "1234");
        if(test != null) {
            System.out.println("No need to create");
            return;
        }
        PersonDAO pd = new PersonDAO();
        test = new Administrator("admin", "0000000000", "admin", "1234");
        pd.create(test);
        System.out.println("Admin initialized with pass 1234");
    }
}
