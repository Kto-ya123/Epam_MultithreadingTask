package src.yan.epam.task4.port;

import org.apache.logging.log4j.Level;
import src.yan.epam.task4.exception.ThreadTimeOutException;
import src.yan.epam.task4.singleton.SeaPort;
import src.yan.epam.task4.singleton.SeaPortSingleton;
import static src.yan.epam.task4.main.Main.LOGGER;

public class Ship extends Thread
{
    public enum OperationType
    {
        CONTAINER_UNLOAD, CONTAINER_LOAD
    }

    private int shipNum;
    private int containersCount;
    private OperationType operation;

    public Ship(int shipNum,int containersCount, OperationType operation)
    {
        this.shipNum=shipNum;
        this.containersCount=containersCount;
        this.operation=operation;
    }

    public void run()
    {
        SeaPort seaPort= SeaPortSingleton.getInstance();
        Pier pier=null;
        try
        {
            pier=seaPort.getPier();
            pier.workStart(operation,containersCount);
            System.out.println("Корабль "+shipNum+" обслужен.");
        }
        catch (ThreadTimeOutException ex)
        {
            LOGGER.log(Level.ERROR, ex);
        }
        catch (InterruptedException ex)
        {
            LOGGER.log(Level.ERROR, ex);
        }
        finally
        {
            if (pier!=null)
            {
                seaPort.returnPier(pier);
            }
        }
    }

}
