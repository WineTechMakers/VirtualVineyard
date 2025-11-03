package bg.tu_varna.sit.virtualvineyard.models;

import bg.tu_varna.sit.virtualvineyard.entities.Person;

public class WineSystem
{
    private Person user;

    WineSystem()
    {
    }
    public void control(String input)
    {
        if(user!=null)
            System.out.println(user.getEGN());
    }
    public void authenticate(String username, String password)
    {
        user = new Administrator("John Doe", "0141240495", "johndoe", "1234");
    }
}
