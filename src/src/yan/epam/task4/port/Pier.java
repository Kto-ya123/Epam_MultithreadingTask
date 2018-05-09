package src.yan.epam.task4.port;

import src.yan.epam.task4.singleton.SeaPort;
import src.yan.epam.task4.singleton.SeaPortSingleton;
import java.util.concurrent.TimeUnit;
import static src.yan.epam.task4.singleton.SeaPort.STORAGE_MAX_CAPACITY;

public class Pier
{
    private int pierNum;

    public Pier(int pierNum)
    {
        this.pierNum=pierNum;
    }

    public int getPierNum()
    {
        return pierNum;
    }

    public void workStart(Ship.OperationType operation,int containersCount)//Задержку на выполнение работы не добавлял, т.к. начинается путаница между потоками с текущим кол-вом контейнеров на складе.
    {
        try
        {
            SeaPort seaPort= SeaPortSingleton.getInstance();
            int currentAmountOfContainers;
        switch (operation)
        {
            case CONTAINER_UNLOAD:
                while (true)
                {
                    currentAmountOfContainers = (seaPort.getCurrentAmountOfContainers()).get();
                    if ((currentAmountOfContainers + containersCount) <= STORAGE_MAX_CAPACITY)// Проверка наличия свободного места на складе
                    {
                        System.out.println("Причал "+pierNum+" занят: "+"Разгрузка "+containersCount+" контейнеров с корабля... Занято мест на складе: " + seaPort.getCurrentAmountOfContainers());
                        seaPort.addCurrentAmountOfContainers(containersCount);
                        break;
                    }
                    else
                    {
                        System.out.println("Причал "+pierNum+" занят: "+"Недостаточно места на складе для разгрузки контейнеров. Ожидание...");
                        TimeUnit.SECONDS.sleep(4);
                    }
                }
                break;
            case CONTAINER_LOAD:
                while (true)
                {
                    currentAmountOfContainers=(seaPort.getCurrentAmountOfContainers()).get();
                    if (currentAmountOfContainers >= containersCount)// Проверка наличия нужного количества контейнеров на складе
                    {
                        System.out.println("Причал "+pierNum+" занят: "+"Загрузка "+containersCount+" контейнеров на корабль... Всего доступно контейнеров: " + seaPort.getCurrentAmountOfContainers());
                        seaPort.decrementCurrentAmountOfContainers(containersCount);
                        break;
                    }
                    else
                    {
                        System.out.println("Причал "+pierNum+" занят: "+"Недостаточно контейнеров на складе для загрузки на корабль. Ожидание...");
                        TimeUnit.SECONDS.sleep(4);
                    }
                }
                break;
        }
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }
}
