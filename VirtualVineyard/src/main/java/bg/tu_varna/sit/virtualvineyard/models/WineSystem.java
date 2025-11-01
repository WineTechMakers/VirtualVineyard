package bg.tu_varna.sit.virtualvineyard.models;

import bg.tu_varna.sit.virtualvineyard.entities.Person;
import bg.tu_varna.sit.virtualvineyard.states.UnloggedState;
import bg.tu_varna.sit.virtualvineyard.states.SystemState;
import bg.tu_varna.sit.virtualvineyard.states.LoggedState;

public class WineSystem
{
    private SystemState state;
    private Person user;

    WineSystem()
    {
        state = new UnloggedState();
    }
    public void control(String input)
    {
        state.control("LOL");
        if(user!=null)
            System.out.println(user.getEGN());
    }
    public void authenticate(String username, String password)
    {
        state = new LoggedState();
        user = new Administrator("John Doe", "0141240495", "johndoe", "1234");
    }
}
