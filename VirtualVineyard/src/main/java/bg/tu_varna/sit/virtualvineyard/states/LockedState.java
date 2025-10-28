package bg.tu_varna.sit.virtualvineyard.states;

public class LockedState implements SystemState
{
    @Override
    public void control(String input)
    {
        System.out.println("Locked");
    }
}
