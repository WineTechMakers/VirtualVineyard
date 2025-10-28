package org.example;

import org.example.Entities.Person;
import org.example.dao.PersonDAO;

public class Main {
    public static void main(String[] args)
    {
        //PersonDAO.testwriting();
        //WineSystem a = new WineSystem();
        //a.authenticate("John Doe", "f");
        Person lol = PersonDAO.authenticate("johndoe", "1234");
        if(lol!=null)
            System.out.println(lol.getName());
    }
}