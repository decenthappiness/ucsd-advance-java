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

import java.util.stream.Stream;

/**
 * This is a Java 1 liner solution to the famous FizzBuzz problem used to filter out 99.5% of
 * programming interviewees.
 *
 * @author Kent Yang
 */
public class FizzBuzz {
  public static void main(String[] args) {
    Stream.iterate(0, n -> n <= 50, n -> n + 1).forEach(n -> System.out.println(
        (n % 15 == 0) ? "FizzBuzz" : (n % 3 == 0) ? "Fizz" : (n % 5 == 0) ? "Buzz" : ""+n));
  }
}
