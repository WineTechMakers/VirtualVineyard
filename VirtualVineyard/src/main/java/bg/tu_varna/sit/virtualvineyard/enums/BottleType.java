package bg.tu_varna.sit.virtualvineyard.enums;

public enum BottleType
{
    ML750(750),
    ML375(375),
    ML200(200),
    ML187(187);

    private final int volume;

    BottleType(int volume)
    {
        this.volume=volume;
    }
    public int getVolume()
    {
        return volume;
    }
}