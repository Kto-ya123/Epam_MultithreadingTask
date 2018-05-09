package src.yan.epam.task4.singleton;

import src.yan.epam.task4.exception.ThreadTimeOutException;
import src.yan.epam.task4.port.Pier;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class SeaPort
{
    private static final int PIERS_AMOUNT=4;
    public static final int STORAGE_MAX_CAPACITY=100;
    private Semaphore semaphore=new Semaphore(PIERS_AMOUNT,true);
    private Deque<Pier> piersPool=new ArrayDeque<>();
    private ReentrantLock reentrantLock=new ReentrantLock();
    private AtomicInteger currentAmountOfContainers=new AtomicInteger(0);


    public SeaPort()
    {
        piersPool.add(new Pier(1));
        piersPool.add(new Pier(2));
        piersPool.add(new Pier(3));
        piersPool.add(new Pier(4));
    }

    public Pier getPier() throws ThreadTimeOutException,InterruptedException
    {
            if (semaphore.tryAcquire(30, TimeUnit.SECONDS))
            {
                try
                {
                    reentrantLock.lock();
                    Pier pier = piersPool.pollFirst();
                    return pier;
                }
                finally
                {
                    reentrantLock.unlock();
                }
            }
        throw new ThreadTimeOutException("Превышено время ожидания получения получения очереди");
    }

    public  void  returnPier(Pier pier)
    {
        try
        {
            reentrantLock.lock();
            piersPool.offer(pier);
            semaphore.release();
            System.out.println("Причал " + pier.getPierNum() + " свободен.");
        }
        finally
        {
            reentrantLock.unlock();
        }
    }

    public AtomicInteger getCurrentAmountOfContainers()
    {
        return currentAmountOfContainers;
    }

    public void addCurrentAmountOfContainers(int containersCount)
    {
        currentAmountOfContainers.addAndGet(containersCount);
    }

    public void decrementCurrentAmountOfContainers(int containersCount)
    {
        currentAmountOfContainers.addAndGet(-containersCount);
    }

}
