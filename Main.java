package com.erberkan;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Main {

    public static void main(String[] args) {
        try {

            String fromFile = "http://www.tcmb.gov.tr/kurlar/today.xml";
            String toFile = "..\\Kur.xml";

            URL webSite = new URL(fromFile);
            ReadableByteChannel rbc = Channels.newChannel(webSite.openStream());
            FileOutputStream fos = new FileOutputStream(toFile);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();

            File xmlFile = new File("..\\Kur.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();
            System.out.println(doc.getDocumentElement().getAttributeNode("Tarih"));
            NodeList nList = doc.getElementsByTagName("Currency");
            System.out.println("--------------------");

            for (int t = 0; t < nList.getLength(); t++) {
                Node nNode = nList.item(t);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    System.out.println("Doviz: " + eElement.getAttribute("Kod"));
                    System.out.println("Isim: " + eElement.getElementsByTagName("Isim").item(0).getTextContent());
                    System.out.println("Doviz Alis: " + eElement.getElementsByTagName("ForexBuying").item(0).getTextContent() + " ₺");
                    System.out.println("Doviz Satis: " + eElement.getElementsByTagName("ForexSelling").item(0).getTextContent() + " ₺");
                    System.out.println("--------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
