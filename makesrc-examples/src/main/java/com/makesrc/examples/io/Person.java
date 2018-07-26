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
package com.makesrc.examples.io;

import java.io.Serializable;
import java.util.Objects;

/**
 * Simple bean / data transfer Object for storing and passing data types within the particular
 * domain around.
 *
 * @author Kent Yang
 */
public class Person implements Serializable {

  static final long serialVersionUID = 1L;

  private String firstName;
  private String lastName;
  private String email;
  private String phone;

  public Person(String line) {
    line = line.trim();
    String[] array = line.split("[, ]+");
    assert(array.length > 4);
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
}
