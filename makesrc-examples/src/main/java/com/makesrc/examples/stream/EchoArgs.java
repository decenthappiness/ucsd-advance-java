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

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * This program shows that variable length arguments are just syntatic sugar and that
 * a stream can be created from an array using Stream.of or Arrays.stream.  We also
 * show that String[] and String... are interchangeable.
 *
 * @author Kent Yang
 */
public class EchoArgs {

  private static void outputArgs(String[] array){
    System.out.println("outputArgs: Create a stream from variable length argument list:");
    Stream.of(array).map(s -> s + " ").forEach(System.out::print);

    System.out.println("\n\noutputArgs: Create a stream from any array:");
    Arrays.stream(array).map(s -> s.toUpperCase() + " ").forEach(System.out::print);

  }

  public static void main(String... args) {

    if (args.length == 0) {
      args = new String[] {"Hello", "World!", "Stream", "style."};
    }

    System.out.println("Create a stream from variable length argument list:");
    Stream.of(args).map(s -> s + " ").forEach(System.out::print);

    System.out.println("\n\nCreate a stream from any array:");
    Arrays.stream(args).map(s -> s.toUpperCase() + " ").forEach(System.out::print);

    outputArgs(args);
  }
}
