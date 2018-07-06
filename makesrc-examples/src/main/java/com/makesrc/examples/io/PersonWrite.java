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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * Demonstrates how to quickly write a data structure (list) of data out to file quickly.  Useful
 * for proto-typing. Make sure to run this class first before running PersonRead.main().
 *
 * @author Kent Yang
 */
public class PersonWrite {
  public static void main(String[] args) {
    List<Person> list = new ArrayList<>();
    list.add(new Person("Kent", "Yang", "kent.yang@gmail.com", "888-8888"));
    list.add(new Person("Dave", "Yang", "dave.yang@gmail.com", "788-8888"));
    list.add(new Person("Mike", "Yang", "mike.yang@gmail.com", "688-8888"));
    list.add(new Person("John", "Yang", "John.yang@gmail.com", "588-8888"));
    list.add(new Person("Lisa", "Yang", "Lisa.yang@gmail.com", "388-8888"));

    try (ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream(
        new FileOutputStream("person.bin")))) {
      oos.writeObject(list);
      System.out.println("Successfully written person records to disk:");
      System.out.println(list);
    } catch (IOException e) {
      System.err.println("Failed to write person records out to file: " + e);
    }
  }
}
