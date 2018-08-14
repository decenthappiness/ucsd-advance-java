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
package com.makesrc.examples.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Objects;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.bind.annotation.XmlType;



/**
 * Simple bean / data transfer Object for storing and passing data types within the particular
 * domain but with the ability to output in XML it's state.
 *
 * @author Kent Yang
 */

// JAXB XML Data Binding - specifies the ordering of the properties in the
// target <sequence> element.
@XmlType
    (
        name = "PersonType", propOrder = {"firstName", "lastName", "email", "phone"}
    )
public class Person implements Serializable {

  static final long serialVersionUID = 1L;

  private String firstName = "";
  private String lastName = "";
  private String email = "";
  private String phone = "";

  public Person() {
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Person(String line) {
    line = line.trim();
    String[] array = line.split("[, ]+");
    assert (array.length > 4);

    this.firstName = array[0];
    this.lastName = array[1];
    this.email = array[2];
    this.phone = array[3];
  }

  public Person(String firstName, String lastName, String email, String phone) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
  }

  public static void main(String args[])
      throws IOException {
    Person me = new Person("Kent", "Yang",
        "Kent.Yang@gmail.com", "(858)888-8888");
    //me.toXml(); // example 4-1
    me.toXmlWithDom(); // example 4-2
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  @Override public int hashCode() {

    return Objects.hash(firstName, lastName, email, phone);
  }

  @Override public String toString() {
    final StringBuilder sb;
    sb = new StringBuilder("Person{");
    sb.append("firstName='").append(getFirstName()).append('\'');
    sb.append(", lastName='").append(getLastName()).append('\'');
    sb.append(", email='").append(getEmail()).append('\'');
    sb.append(", phone='").append(getPhone()).append('\'');
    sb.append('}');
    return sb.toString();
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return Objects.equals(firstName, person.firstName) &&
        Objects.equals(lastName, person.lastName) &&
        Objects.equals(email, person.email) &&
        Objects.equals(phone, person.phone);
  }

  public void toXml()
      throws IOException
  {
    String fileName = this.firstName + this.lastName + ".xml";
    File f = new File(fileName);

    if(f.exists())
      f.delete();

    PrintWriter pw =
        new PrintWriter(
            new BufferedWriter(
                new FileWriter(fileName)));

    StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    sb.append("\n<person>");
    sb.append("\n\t<firstName>\n\t\t" + this.firstName + "\n\t</firstName>");
    sb.append("\n\t<lastName>\n\t\t" + this.lastName + "\n\t</lastName>");
    sb.append("\n\t<email>\n\t\t" + this.email + "\n\t</email>");
    sb.append("\n\t<phone>\n\t\t" + this.phone + "\n\t</phone>");
    sb.append("\n</person>\n");

    pw.println(sb);
    pw.close();
  }

  public void toXmlWithDom()
      throws IOException {
    try {
      Document document =
          DocumentBuilderFactory.newInstance().
              newDocumentBuilder().newDocument();

      Element rootElement = document.createElement("person");
      document.appendChild(rootElement);

      Element element = document.createElement("firstName");
      element.appendChild(document.createTextNode(this.firstName));
      rootElement.appendChild(element);

      element = document.createElement("lastName");
      element.appendChild(document.createTextNode(this.lastName));
      rootElement.appendChild(element);

      element = document.createElement("email");
      // assign an attribute to the element
      element.setAttribute("type", "work");
      element.appendChild(document.createTextNode(this.email));
      rootElement.appendChild(element);

      element = document.createElement("phone");
      // assign an attribute to the element another way
      Attr attribute = document.createAttribute("type");
      attribute.setValue("cell");
      element.setAttributeNode(attribute);
      element.appendChild(document.createTextNode(this.phone));
      rootElement.appendChild(element);

      String fileName = this.firstName + this.lastName + ".xml";

      File f = new File(fileName);
      if (f.exists()) {
        f.delete();
      }

      // holds the current DOM Tree to load/transform
      Source source = new DOMSource(document);
      // holds the results of the transformation (XML)
      Result result = new StreamResult(f);
      // An instance of abstract class that can transform
      // a source tree into result tree (XML)
      Transformer transformer =
          TransformerFactory.newInstance().newTransformer();
      // Add capability to indent the output (make it pretty)
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      // Set the indention to 3 spaces (default is 0)
      transformer.setOutputProperty(
          "{http://xml.apache.org/xslt}indent-amount", "3");
      // Just do it!
      transformer.transform(source, result);
    } catch (ParserConfigurationException e) {
      System.err.println("Parser Configuration error. Can not serialize Object to XML.");
    } catch (TransformerConfigurationException e) {
      System.err.println("Transformer Configuration error. Can not serialize Object to XML.");
    } catch (TransformerException e) {
      System.err.println("Transformation error. Can not serialize Object to XML.");
    }
  }
}
