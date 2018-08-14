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
 * Simple demonstration of using SAX api to pull a targeted element in the XML.
 * In this case we want to pull all the email addresses from the sample
 * XML document to generate a mailing list.
 *
 * @author Kent Yang
 */

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class GenMailingList extends org.xml.sax.helpers.DefaultHandler {
  private StringBuffer buffer = new StringBuffer();

  public static void main(String[] args)
      throws IOException, SAXException, ParserConfigurationException {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser parser = factory.newSAXParser();
    GenMailingList handler = new GenMailingList();
    parser.parse(new File("addressbook.xml"), handler);
  }

  public void startDocument() {
    System.out.println("Start processing...");
  }

  public void characters(char[] ch, int start, int length) {
    buffer.append(ch, start, length);
  }

  public void startElement(String uri, String localName,
      String qname, Attributes attributes) {
    buffer.setLength(0);
  }

  public void endElement(String uri, String localName, String qname) {
    if (qname.equals("email")) {
      System.out.println(buffer.toString().trim());
    }
  }

  public void endDocument() {
    System.out.println("End processing...");
  }
}


