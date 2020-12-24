package task4.singleton;

import task4.exception.ThreadTimeOutException;
import task4.port.Pier;

import java.util.ArrayDeque;
import java.util.Deque;

public class SeaPort
{
    private static final int PIERS_AMOUNT=4;
    public static final int STORAGE_MAX_CAPACITY=100;
    private Deque<Pier> piersPool=new ArrayDeque<>();
    private Integer countContainers;


    public SeaPort()
    {
        countContainers = 0;
        piersPool.add(new Pier(1));
        piersPool.add(new Pier(2));
        piersPool.add(new Pier(3));
        piersPool.add(new Pier(4));
    }

    public Pier getPier() throws ThreadTimeOutException,InterruptedException
    {
        Pier pier = null;
        while (pier == null){
            synchronized (piersPool) {
                pier = piersPool.pollFirst();

            }

            Thread.sleep(1000);
        }

        return pier;
    }

    public  void  returnPier(Pier pier)
    {
        synchronized (piersPool){
            piersPool.offer(pier);
            System.out.println("Причал " + pier.getPierNum() + " свободен.");
        }
    }

    public Integer getCurrentAmountOfContainers()
    {
        synchronized (countContainers){
            return countContainers;
        }
    }

    public void addCurrentAmountOfContainers(int containersCount)
    {
        synchronized (countContainers){
            countContainers += containersCount;
        }
    }

    public void decrementCurrentAmountOfContainers(int containersCount)
    {
        synchronized (countContainers){
            countContainers -= containersCount;
        }
    }

}
