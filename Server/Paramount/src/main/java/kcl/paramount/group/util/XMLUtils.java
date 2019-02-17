package kcl.paramount.group.util;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


/*
    Used to analysis the XML configuration file
 */
public class XMLUtils {

    private Document doc;

    // the first doc used to local test, the second is used on server
    // the relative path to configuration file is still not find a better way to get it
    public XMLUtils() {
        try {
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dFactory.newDocumentBuilder();
            //doc = builder.parse("src/main/java/kcl/paramount/group/config/DBConfig.xml");

            doc = builder.parse("/var/lib/tomcat8/webapps/Paramount/WEB-INF/classes/kcl/paramount/group/config/DBConfig.xml");


        } catch (ParserConfigurationException e) {
            //auto generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            //auto generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            //auto generated catch block
            e.printStackTrace();
        }

    }

    // fetch the attribute from the tag name
    public String getAttribute(String attributeName) {
        String attribute = null;

        try {
            NodeList nl = doc.getElementsByTagName(attributeName);
            Node attributeNode = nl.item(0).getFirstChild();
            attribute = attributeNode.getNodeValue();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return attribute;
    }

    // a small test
    public static void main(String[] args) {
        XMLUtils xmlUtils = new XMLUtils();
        System.out.println(xmlUtils.getAttribute("user"));
    }

}
