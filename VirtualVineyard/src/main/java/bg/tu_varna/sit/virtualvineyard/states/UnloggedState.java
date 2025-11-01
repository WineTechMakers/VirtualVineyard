package bg.tu_varna.sit.virtualvineyard.states;

public class UnloggedState implements SystemState
{
    @Override
    public void control(String input)
    {
        System.out.println("Locked");
    }
}
