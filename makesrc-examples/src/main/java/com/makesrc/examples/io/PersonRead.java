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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Demonstrates how to quickly read a data structure (list) of data out to file quickly.  Useful
 * for proto-typing.
 *
 * @author Kent Yang
 */
public class PersonRead {

  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    try (ObjectInputStream ois =
             new ObjectInputStream(new GZIPInputStream(new FileInputStream("person.bin")))) {
      List<Person> list = (List<Person>) ois.readObject();

      System.out.println("Successfully read person records from disk:");
      System.out.println(list);
    } catch (IOException | ClassNotFoundException e) {
      System.err.println("Failed to read person records from file: " + e);
    }
  }
}
