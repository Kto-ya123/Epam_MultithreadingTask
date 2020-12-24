package task4.port;

import org.apache.logging.log4j.Level;
import task4.exception.ThreadTimeOutException;
import task4.singleton.SeaPort;
import task4.singleton.SeaPortSingleton;

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
    private Integer importance;

    public Ship(int shipNum, int containersCount, OperationType operation, Integer priority, Integer importance)
    {
        this.importance = importance;
        this.setPriority(priority);
        this.shipNum=shipNum;
        this.containersCount=containersCount;
        this.operation=operation;
    }

    public void run()
    {
        SeaPort seaPort= SeaPortSingleton.getInstance();
        Pier pier=null;
        while(true) {
            try {
                swim();
                pier = seaPort.getPier();
                pier.workStart(operation, containersCount);
                System.out.println("Корабль " + shipNum + " обслужен.");
            } catch (ThreadTimeOutException ex) {
                LOGGER.log(Level.ERROR, ex);
            } catch (InterruptedException ex) {
                LOGGER.log(Level.ERROR, ex);
            } finally {
                if (pier != null) {
                    seaPort.returnPier(pier);
                }
            }
        }
    }

    private void swim() throws InterruptedException {
        Thread.sleep(5000 - (this.importance * 1000));
    }

}
