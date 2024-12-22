package com.uhl.daz;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlToPostgres {
    public static void main(String[] args) {
        String fileName = "file.xml";
        String url = "jdbc:postgresql://localhost:5432/database_name";
        String user = "username";
        String password = "password";
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(fileName));
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("record");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    String age = element.getElementsByTagName("age").item(0).getTextContent();
                    String address = element.getElementsByTagName("address").item(0).getTextContent();
                    String query = "INSERT INTO table_name (name, age, address) VALUES (?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, name);
                    stmt.setString(2, age);
                    stmt.setString(3, address);
                    stmt.executeUpdate();
                }
            }
            conn.close();
        } catch (SQLException | javax.xml.parsers.ParserConfigurationException | org.xml.sax.SAXException e) {
            System.err.format("Exception: %s%n", e);
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}