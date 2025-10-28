package org.example;

public class LockedState implements SystemState
{
    @Override
    public void control(String input)
    {
        System.out.println("Locked");
    }
}
