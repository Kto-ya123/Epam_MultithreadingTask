package src.yan.epam.task4.singleton;

import java.util.concurrent.locks.ReentrantLock;

public class SeaPortSingleton
{
    private static ReentrantLock reentrantLock=new ReentrantLock();
    private static SeaPort instance=null;

    private SeaPortSingleton(){}

    public static SeaPort getInstance()
    {
        reentrantLock.lock();
        try
        {
            if (instance == null)
            {
                instance =new SeaPort();
            }
        }
        finally
        {
            reentrantLock.unlock();
        }
        return instance;
    }

}
