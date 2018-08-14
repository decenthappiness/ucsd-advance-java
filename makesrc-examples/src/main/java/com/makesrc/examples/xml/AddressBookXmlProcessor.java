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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

/**
 * Shows how to annotate Java classes so that it generate the necessary JAXB classes
 * automatically so that Objects can be marshaled and un-marshaled to and from XML.
 * Additionally an XSD is generated for validation.
 *
 * @author Kent Yang
 */
public class AddressBookXmlProcessor {
  private static DayTimer daytimer;
  private final String schemaFileName = "addressbook.xsd";
  private final String xmlFileName = "addressbook.xml";
  private final File outputDir;
  private ByteArrayOutputStream xmlOutputStream;
  private StringBuilder validationMessage;

  public AddressBookXmlProcessor() {
    outputDir = new File("generated");
    if (!outputDir.exists()) {
      outputDir.mkdirs();
    }
    setup();
  }

  public static void main(String args[]) {
    try {
      AddressBookXmlProcessor abxp = new AddressBookXmlProcessor();
      abxp.marshalXml();
      abxp.unmarshalXml();
    } catch (Exception e) {
      System.err.println("Oh, Oh, marshalling and unmarshalling failed!");
    }
  }

  private void setup() {
    this.daytimer = new DayTimer();
    this.daytimer.add(new Person("Kent", "Yang", "kent.yang@gmail.com", "8588296201"));
    this.daytimer.add(new Person("Mike", "Yang", "mike.yang@gmail.com", "8588296202"));
    this.daytimer.add(new Person("Dave", "Yang", "dave.yang@gmail.com", "8588296203"));
    this.daytimer.add(new Person("John", "Yang", "john.yang@gmail.com", "8588296204"));
    this.daytimer.add(new Person("Lisa", "Yang", "lisa.yang@gmail.com", "8588296205"));
  }

  public String getXmlOutputStream() {
    return xmlOutputStream.toString();
  }

  public String getValidationMessage() {
    return validationMessage.toString();
  }

  public DayTimer getDayTimer() {
    return daytimer;
  }

  private void generateSchema(JAXBContext ctx, String schemaName)
      throws Exception {

    String success;
    final StreamResult result = new StreamResult(getFilePath(schemaName));
    ctx.generateSchema(new SchemaOutputResolver() {
      @Override
      public Result createOutput(String namespaceUri, String schemaName)
          throws IOException {
        return result;
      }
    });
  }

  public void marshalXml()
      throws Exception {

    // specify a name for the generated XML instance document
    OutputStream os = new FileOutputStream(getFilePath(xmlFileName));

    // create a ByteArrayOutputStream so we can dump the xml to a display
    xmlOutputStream = new ByteArrayOutputStream();

    // create a JAXBContext for the PrintOrder class
    JAXBContext ctx = JAXBContext.newInstance(DayTimer.class);

    // generate an XML schema from the annotated object model
    generateSchema(ctx, schemaFileName);

    // create a marshaller
    Marshaller marshaller = ctx.createMarshaller();

    // the property JAXB_FORMATTED_OUTPUT specifies whether or not the
    // marshalled XML data is formatted with line-feeds and indentation
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

    // marshal the data in the Java content tree
    // to the XML instance document daytimer.xml
    marshaller.marshal(daytimer, os);
    marshaller.marshal(daytimer, xmlOutputStream);
  }

  public void unmarshalXml()
      throws Exception {

    // create a JAXBContext for the PrintOrder class
    JAXBContext ctx = JAXBContext.newInstance(DayTimer.class);

    // create an Unmarshaller and set the validationEvent handler
    Unmarshaller unmarshaller = ctx.createUnmarshaller();
    unmarshaller.setEventHandler(new DayTimerValidationEventHandler());

    // get a reference to the schema to use for validation
    SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema schema = sf.newSchema(getFilePath(schemaFileName));

    // set the unmarshaller's schema
    unmarshaller.setSchema(schema);

    daytimer = (DayTimer) unmarshaller.unmarshal(getFilePath(xmlFileName));

    Person[] parray = daytimer.getSortedListByLastName();
    for (Person p : parray)
      System.out.println(p);
  }

  // Convenience method to get the file path
  private File getFilePath(String fileName) {
    return new File(outputDir, fileName);
  }

  //
  //  Inner class to handle validation events
  //
  class DayTimerValidationEventHandler
      implements ValidationEventHandler {

    public boolean handleEvent(ValidationEvent ve) {
      validationMessage = new StringBuilder();

      if (ve.getSeverity() == ValidationEvent.FATAL_ERROR ||
          ve.getSeverity() == ValidationEvent.ERROR) {

        ValidationEventLocator locator = ve.getLocator();

        //Print message from validation event
        validationMessage.append("Invalid daytimer XML document: " + locator.getURL() + "\n");

        validationMessage.append("Error: " + ve.getMessage() + "\n");

        //Output line and column number
        validationMessage.append("Error at column " +
            locator.getColumnNumber() +
            ", line "
            + locator.getLineNumber());
      }

      return false;
    }
  }
}
