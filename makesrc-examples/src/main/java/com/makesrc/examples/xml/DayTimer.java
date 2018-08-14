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
 * TODO:
 *
 * @author Kent Yang
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JAXB XML Data Binding root element of the generated XML document
 */
@XmlRootElement(name = "addressBook")
public class DayTimer {

  private static Person author = new Person("Kent",
      "Yang",
      "Kent.Yang@gmail.com",
      "858-829-6201");
  /*
   * All of the value elements of the contacts Map will be wrapped
   * by a list element called 'contacts' instead of the default.
   */
  @XmlElementWrapper(name = "persons")
  private Map<String, Person> contacts;
  private Person current;

  public DayTimer() {
    contacts = new HashMap<String, Person>();
  }

  public Person getAuthor() {
    return author;
  }

  public void add(Person p) {

    if (!contacts.containsKey(p.getEmail())) {
      contacts.put(p.getEmail(), p);
    }
  }

  public void empty() {
    contacts.clear();
  }

  public Person[] getContacts() {
    return contacts.values().toArray(new Person[0]);
  }

  public Person getCurrent() {
    return current;
  }

  public void setCurrent(Person current) {
    this.current = current;
  }

  public Person[] find(String lastName) {
    ArrayList<Person> foundList = new ArrayList<Person>();
    for (Person person : contacts.values()) {
      if (person.getLastName() == lastName) {
        foundList.add(person);
      }
    }
    return foundList.toArray(new Person[0]);
  }

  public Person findByEmail(String email) {
    return contacts.get(email);
  }

  public int getSize() {
    return contacts.size();
  }

  public Person[] getSortedListByFirstName() {
    FirstNameComparator fnc = new FirstNameComparator();
    return getSortedList(fnc);
  }

  public Person[] getSortedListByLastName() {
    LastNameComparator lnc = new LastNameComparator();
    return getSortedList(lnc);
  }

  public void remove(String email) {
    contacts.remove(email);
  }

  private Person[] getSortedList(Comparator<Person> c) {
    Person[] sortedList = new Person[contacts.size()];
    sortedList = (Person[]) contacts.values().toArray(new Person[0]);
    Arrays.sort(sortedList, c);
    return sortedList;
  }

  class FirstNameComparator implements Comparator<Person> {
    public int compare(Person p1, Person p2) {
      return p1.getFirstName().compareTo(p2.getFirstName());
    }
  }

  class LastNameComparator implements Comparator<Person> {
    public int compare(Person p1, Person p2) {
      return p1.getLastName().compareTo(p2.getLastName());
    }
  }
}

