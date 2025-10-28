package org.example;

public enum Bottle
{
    ML750(750),
    ML375(375),
    ML200(200),
    ML187(187);

    private final int volume;

    Bottle(int volume)
    {
        this.volume=volume;
    }
    public int getVolume()
    {
        return volume;
    }
}