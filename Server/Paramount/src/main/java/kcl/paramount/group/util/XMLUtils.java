package kcl.paramount.group.util;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


public class XMLUtils {

    private Document doc;

    public XMLUtils() {
        try {
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dFactory.newDocumentBuilder();
            doc = builder.parse("src/main/java/kcl/paramount/group/config/DBConfig.xml");
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

}
