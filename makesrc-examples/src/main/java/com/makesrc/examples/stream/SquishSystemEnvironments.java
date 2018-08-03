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
package com.makesrc.examples.stream;

import java.util.Arrays;
import java.util.Map;

/**
 * This example demonstrates how to use the flatmap as oppose to a regular map which transform
 * a stream of one type to another.  However, if the type that you are transposing is also a
 * stream, list or array type, flatmap can help you flatten it out into a single stream.
 *
 * @author Kent Yang
 */
public class SquishSystemEnvironments {
  public static void main(String[] args) {
    Map<String, String> mapEnvSettings = System.getenv();

    mapEnvSettings
        .entrySet()
        .stream()
        .flatMap(e -> Arrays.stream(new String[] {e.getKey(), e.getValue()}))
        .forEach(System.out::println);
  }
}
