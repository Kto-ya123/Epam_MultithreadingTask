package task4.singleton;

public class SeaPortSingleton
{
    private static SeaPort instance=null;

    private SeaPortSingleton(){}

    public synchronized static SeaPort getInstance()
    {
        if (instance == null)
        {
            instance =new SeaPort();
        }

        return instance;
    }

}
