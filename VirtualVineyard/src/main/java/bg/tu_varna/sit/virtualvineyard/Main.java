package bg.tu_varna.sit.virtualvineyard;

import bg.tu_varna.sit.virtualvineyard.dao.PersonDAO;
import bg.tu_varna.sit.virtualvineyard.entities.Person;
import bg.tu_varna.sit.virtualvineyard.models.Administrator;

public class Main {
    public static void main(String[] args)
    {
        //PersonDAO.testwriting();
        //WineSystem a = new WineSystem();
        //a.authenticate("John Doe", "f");

        Person admin = new Administrator("admin", "2234567890", "adminc", "12345");
        PersonDAO dao = new PersonDAO();
        //dao.create(admin);
        admin = dao.findOne(18L);

        //Person lol = PersonDAO.authenticate("admin", "1234");
        //System.out.println(admin);
    }
}