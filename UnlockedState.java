package org.example;

public class UnlockedState implements SystemState
{
    @Override
    public void control(String input)
    {
        System.out.println("Unlocked");
    }
}
