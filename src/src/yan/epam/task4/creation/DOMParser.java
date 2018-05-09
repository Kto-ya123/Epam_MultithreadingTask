package src.yan.epam.task4.creation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import src.yan.epam.task4.port.Ship;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import src.yan.epam.task4.exception.XmlBuilderException;


public class DOMParser
{
    private static Deque<Ship> shipsDeque=new ArrayDeque<>();
    private static final String filePath="data/Ships.xml";

    public static Deque<Ship> getshipsDeque() throws XmlBuilderException
    {
        try
        {
            File XmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(XmlFile);
            document.getDocumentElement().normalize();

            NodeList shipNodeList = document.getElementsByTagName("ship");
            fillshipsDeque(shipNodeList);
            return shipsDeque;
        }
        catch (ParserConfigurationException ex)
        {
            throw new XmlBuilderException(ex+" In file: ",filePath);
        }
        catch (SAXException ex)
        {
            throw new XmlBuilderException("Error in SAX parser: "+ex+" with file: ",filePath);
        }
        catch (IOException ex)
        {
            throw new XmlBuilderException("Error in I/O stream: "+ex+" with file: ", filePath);
        }
    }

    private static void fillshipsDeque(NodeList nodeList)
    {
        for (int temp = 0; temp < nodeList.getLength(); temp++)
        {
            Node nNode = nodeList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element eElement = (Element) nNode;

                int shipNum=Integer.parseInt(eElement.getElementsByTagName("shipNum").item(0).getTextContent());
                int containersCount=Integer.parseInt(eElement.getElementsByTagName("containersCount").item(0).getTextContent());
                String operation=eElement.getElementsByTagName("operation").item(0).getTextContent();

                if(("CONTAINER_LOAD").equals(operation))
                {shipsDeque.add(new Ship(shipNum,containersCount, Ship.OperationType.CONTAINER_LOAD));}
                else
                {shipsDeque.add(new Ship(shipNum,containersCount, Ship.OperationType.CONTAINER_UNLOAD));}
            }
        }
    }

}
