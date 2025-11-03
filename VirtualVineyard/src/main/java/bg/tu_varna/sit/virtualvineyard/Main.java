package bg.tu_varna.sit.virtualvineyard;

import bg.tu_varna.sit.virtualvineyard.dao.PersonDAO;
import bg.tu_varna.sit.virtualvineyard.entities.Person;

public class Main {
    public static void main(String[] args)
    {
        //PersonDAO.testwriting();
        //WineSystem a = new WineSystem();
        //a.authenticate("John Doe", "f");

        //Person admin = new Administrator("admin", "1234567890", "admin", "1234");
        //PersonDAO dao = new PersonDAO();
        //dao.create(admin);

        Person lol = PersonDAO.authenticate("admin", "1234");
        System.out.println(lol);
    }
}