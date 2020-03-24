package com.company;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Scanner;

public class Main {

    private static void parseFile(String fileName, String newFileName) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
       // Document resultDocument = builder.newDocument();
        Document document = builder.parse(fileName);
        NodeList nodeList = document.getElementsByTagName("student");
      //  Element resultStudent = resultDocument.createElement("student");
      //  resultStudent.setAttribute("lastname", document.getDocumentElement().getAttribute("lastname"));
       // resultDocument.appendChild(resultStudent);
        NodeList childNodes = nodeList.item(0).getChildNodes();
        double sum = 0;
        int count = 0;
        double average = 0;
        for (int i = 0; i < childNodes.getLength(); i++) {
            NamedNodeMap namedNodeMap = childNodes.item(i).getAttributes();
            if (namedNodeMap != null && childNodes.item(i).getNodeName().equals("subject")) {
                sum += Integer.parseInt(namedNodeMap.getNamedItem("mark").getNodeValue());
                count++;
              //  Element resultSubject = resultDocument.createElement("subject");
              //  resultSubject.setAttribute("mark", namedNodeMap.getNamedItem("mark").getNodeValue());
               // resultSubject.setAttribute("title", namedNodeMap.getNamedItem("title").getNodeValue());
              //  resultStudent.appendChild(resultSubject);
            } else if (childNodes.item(i).getNodeName().equals("average")) {
                if (count > 0) average = sum / count;
                 childNodes.item(i).setTextContent(Double.toString(average));
          //      Element resultAverage = resultDocument.createElement("average");
           //     resultAverage.appendChild(resultDocument.createTextNode(Double.toString(average)));
            //    resultStudent.appendChild(resultAverage);
            }
        }
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        File file = new File(newFileName);
        transformer.transform(new DOMSource(document/*resultDocument*/), new StreamResult((new FileOutputStream(file))));
    }

    public static void main(String[] args) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите название файла для чтения:");
        String name = in.nextLine();
        System.out.println("Введите название выходного файла:");
        String outName = in.nextLine();
        parseFile(name, outName);
    }
}
