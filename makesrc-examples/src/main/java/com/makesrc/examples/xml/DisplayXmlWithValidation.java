package com.makesrc.examples.xml;
/* 
  Copyright (c) 2018 Kent Yang
  
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
 
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
 
  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <https://www.gnu.org/licenses/>.
  limitations under the License.
*/

import java.sql.SQLOutput;
import org.w3c.dom.*;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

/**
 * Generic class showing how to process any XML file generically with DOM and
 * also perform 2 types of validation.  First, using a Validator to validate
 * the XML then setting it as part of the DOM Builder factory.  Additionally,
 * it also demonstrates how to attached a error handler to allow the parser
 * and validator to callback and report errors
 *
 * @author Kent Yang
 */

public class DisplayXmlWithValidation {
  private static final XmlErrorHandler XML_ERROR_HANDLER =
      new DisplayXmlWithValidation.XmlErrorHandler();

  public static void displayXml(File xmlFile, File xsdFile) {
    try {

      System.out.println("No Validation");
      SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(new StreamSource(new FileInputStream(xsdFile)));
      Validator validator = schema.newValidator();
      validator.setErrorHandler(XML_ERROR_HANDLER);
      validator.validate(new StreamSource(xmlFile));

      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      dbFactory.setSchema(schema);
      dbFactory.setIgnoringElementContentWhitespace(true);

      DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();

      docBuilder.setErrorHandler(XML_ERROR_HANDLER);

      Document doc = docBuilder.parse(xmlFile);

      List<Node> nodeList = new ArrayList<>();
      nodeList.add(doc);

      while (nodeList.size() > 0) {
        Node node = nodeList.get(0);

        if (node instanceof Document) System.out.println("Document Node");
        else if (node instanceof Element) {
          System.out.println("Element Node: " + ((Element) node).getTagName());
          NamedNodeMap attrMap = node.getAttributes();
          for (int i = 0; i < attrMap.getLength(); i++) {
            Attr attribute = (Attr) attrMap.item(i);
            System.out.print(
                "\tAttribute Key: " + attribute.getName() + " Value: " + attribute.getValue());
          }
          if (node.hasAttributes()) System.out.println();
        } else if (node instanceof Text) System.out.println("Text Node: " + node.getNodeValue());
        else System.out.println("Other Type: " + node.getNodeValue());

        if (node.hasChildNodes()) {
          NodeList nl = node.getChildNodes();
          for (int i = 0; i < nl.getLength(); i++) {
            nodeList.add(nl.item(i));
          }
        }
        nodeList.remove(0);
      }
    } catch (Exception e) {
      // Change: We simplify the exception handling
      System.err.println(
          "Error! Can not display XML file: " + xmlFile + " using: " + xsdFile + " to validate.");
      e.printStackTrace();
    }
  }

  public static void main(String args[]) {
    // Change: the usage to provide extra parameter
    if (args.length == 2 && args[0].endsWith(".xml") && args[1].endsWith(".xsd"))
      displayXml(new File(args[0]), new File(args[1]));
    else System.out.println("Usage: java DisplayXmlWithValidation <file name>.xml <file name>.xsd");
  }

  static class XmlErrorHandler extends DefaultHandler {

    public void warning(SAXParseException e) {
      errorDisplay("Warning", e);
    }

    public void error(SAXParseException e) {
      errorDisplay("Error", e);
    }

    public void fatalError(SAXParseException e) {
      errorDisplay("Fatal Error", e);
    }

    // note you can add extra methods to anonymous inner class
    private void errorDisplay(String errorType, SAXParseException e) {
      System.err.println(
          "Error Handler: "
              + errorType
              + " Line: "
              + e.getLineNumber()
              + "\nError Message: "
              + e.getMessage());
    }
  }
}

