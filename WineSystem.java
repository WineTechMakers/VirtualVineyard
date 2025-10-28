package org.example;

import org.example.Entities.Administrator;
import org.example.Entities.Person;
import org.example.dao.PersonDAO;

public class WineSystem
{
    private SystemState state;
    private Person user;

    WineSystem()
    {
        state = new LockedState();
    }
    public void control(String input)
    {
        state.control("LOL");
        if(user!=null)
            System.out.println(user.getEGN());
    }
    public void authenticate(String username, String password)
    {
        state = new UnlockedState();
        user = new Administrator("John Doe", "0141240495", "johndoe", "1234");
    }
}
