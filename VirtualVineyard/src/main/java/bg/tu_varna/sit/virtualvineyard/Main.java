package bg.tu_varna.sit.virtualvineyard;

import bg.tu_varna.sit.virtualvineyard.dao.PersonDAO;
import bg.tu_varna.sit.virtualvineyard.entities.Person;

public class Main {
    public static void main(String[] args)
    {
        //PersonDAO.testwriting();
        //WineSystem a = new WineSystem();
        //a.authenticate("John Doe", "f");

        Person lol = PersonDAO.authenticate("johndoeoperator", "1234567");
        System.out.println(lol);
    }
}