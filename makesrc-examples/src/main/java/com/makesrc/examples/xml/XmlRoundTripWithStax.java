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

/**
 * A quick demonstration of using the STAX API for showing a roundtrip of writing the
 * state of an object out to XML and then reading it back in and displaying it.
 *
 * @author Kent Yang
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class XmlRoundTripWithStax {
  public static void main(String[] args) {
    try {
      File f = new File("Kent.xml");
      writePersonXml(
          new Person("Kent", "Yang", "kent.yang@gmail.com", "888-888-8888"),
          f);
      Person p = readPersonXml(f);
      System.out.println("Information for Person: \n" + p);
    } catch (Throwable e) {
      System.err.println("Failed StAX demonstration with Person class!");
    }
  }

  private static void writePersonXml(Person p, File f)
      throws Exception {
    XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
    try {
      FileWriter fileWriter = new FileWriter(f);
      XMLStreamWriter writer =
          outputFactory.createXMLStreamWriter(fileWriter);

      writer.writeStartDocument("1.0");
      writer.writeStartElement("person");
      writer.writeStartElement("firstName");
      writer.writeCharacters(p.getFirstName());
      writer.writeEndElement();
      writer.writeStartElement("lastName");
      writer.writeCharacters(p.getLastName());
      writer.writeEndElement();
      writer.writeStartElement("email");
      writer.writeAttribute("type", "work");
      writer.writeCharacters(p.getEmail());
      writer.writeEndElement();
      writer.writeStartElement("phone");
      writer.writeAttribute("type", "cell");
      writer.writeCharacters(p.getPhone());
      writer.writeEndElement();
      writer.writeEndElement();
      writer.flush();
      writer.close();
    } catch (IOException | XMLStreamException e) {
      System.err.println(e);
      throw e;
    }
  }

  private static Person readPersonXml(File f) {
    Person p = new Person();

    if (f.exists()) {
      try {
        XMLInputFactory inputFactory = XMLInputFactory.newFactory();
        FileReader fileReader = new FileReader(f);
        XMLStreamReader reader =
            inputFactory.createXMLStreamReader(fileReader);

        while (reader.hasNext()) {
          int eventType = reader.getEventType();
          switch (eventType) {
            case XMLStreamConstants.START_ELEMENT:
              String elementName = reader.getLocalName();
              if (elementName.equals("Person")) {
                System.out.println("Start new Person processing...");
              } else if (elementName.equals("firstName")) {
                p.setFirstName(reader.getElementText());
              } else if (elementName.equals("lastName")) {
                p.setLastName(reader.getElementText());
              } else if (elementName.equals("email")) {
                // Check for type
                int numAttributes = reader.getAttributeCount();
                for (int i = 0; i < numAttributes; i++)
                  System.out.println("Email attribute: " +
                      reader.getAttributeLocalName(i) +
                      "=" + reader.getAttributeValue(i));
                p.setEmail(reader.getElementText());
              } else if (elementName.equals("phone")) {
                int numAttributes = reader.getAttributeCount();
                for (int i = 0; i < numAttributes; i++)
                  System.out.println("Phone attribute: " +
                      reader.getAttributeLocalName(i) +
                      "=" + reader.getAttributeValue(i));
                p.setPhone(reader.getElementText());
              }
              break;
            case XMLStreamConstants.END_ELEMENT:
              elementName = reader.getLocalName();
              if (elementName.equals("Person")) {
                System.out.println("Done processing person...");
              }
              break;
          }
          reader.next();
        }
      } catch (FileNotFoundException | XMLStreamException e) {
        System.err.println(e);
        return p;
      }
    }
    return p;
  }
}


