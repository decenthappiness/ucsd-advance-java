package com.makesrc.examples.stream;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This is an example that shows that any Java collection type can be use as a stream.  We use
 * the good old System.getenv as data source generator and then shows how we can process a Set,
 * a List, and finally a Map of key value pairs.
 *
 * @author Kent Yang
 */
public class ShowSystemEnvironmentSettings {
  public static void main(String[] args) {
    // Get the System Environments.  Java 1 liner.
    Map<String, String> map = System.getenv();

    System.out.println("Create a stream from a Collection type Set:");
    Set<String> set = map.keySet();
    // Display Keys - TODO: ask class why keys are returned as a Set?
    set.stream().forEach(System.out::println);

    System.out.println("\n\nCreate a stream from a Collection type List:");
    List<String> list = new ArrayList<>(map.values());
    // Display Values - TODO: ask class why values are returned as a List;
    list.stream().forEach(System.out::println);

    System.out.println("\n\nCreate a stream from a Collection type Map:");
    map.entrySet().stream().forEach(System.out::println);
    map.entrySet().stream().
        forEach((a) -> System.out.printf("Key: %s Value: %s\n", a.getKey(), a.getValue()));
  }
}
