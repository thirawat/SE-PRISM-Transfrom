package se.chula;

import prism.Prism;
import se.chula.prism.PrismFormatter;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import org.xml.sax.SAXException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String xmlPath = "xml/pta.xml";
        
        PrismFormatter formater = new PrismFormatter();
        try {
            formater.readXML(xmlPath);
            String prism_code = formater.transform();
            System.out.println(prism_code);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Prism prism = new Prism(null);
    }
}
