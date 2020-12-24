package task4.main;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import task4.creation.DOMParser;
import task4.exception.XmlBuilderException;
import task4.port.Ship;

import java.util.Deque;

public class Main
{
    public static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args)
    {
        try
        {
            System.out.println("Порт начал работу!");
            Deque<Ship> shipsDeque = DOMParser.getshipsDeque();

            int shipCount=shipsDeque.size();
            for (int i=0;i<shipCount;i++)
            {
                shipsDeque.pollFirst().start();
            }
        }
        catch (XmlBuilderException ex)
        {
            LOGGER.log(Level.ERROR, ex);
        }
    }

}
