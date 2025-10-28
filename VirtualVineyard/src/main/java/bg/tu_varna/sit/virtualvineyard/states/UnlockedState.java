package bg.tu_varna.sit.virtualvineyard.states;

public class UnlockedState implements SystemState
{
    @Override
    public void control(String input)
    {
        System.out.println("Unlocked");
    }
}
