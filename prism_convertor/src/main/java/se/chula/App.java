package se.chula;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import org.xml.sax.SAXException;

import se.chula.pta.PTAConverter;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // long startTime = System.currentTimeMillis();

        String xmlPath = args[0];
        // String xmlPath = "xml/03_pta_food_delivery.xml";
        // String xmlPath = "xml/simple_model.xml";
        
        PTAConverter converter = new PTAConverter();
        try {
            converter.loadUppaalModel(xmlPath);
            
            String prism_code = converter.transform();
            
            System.out.println(prism_code);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // long endTime = System.currentTimeMillis();
        // double executionTime = (endTime - startTime) / 1000.0;
        // System.out.println("Execution time: " + executionTime + " seconds");
    }
}
