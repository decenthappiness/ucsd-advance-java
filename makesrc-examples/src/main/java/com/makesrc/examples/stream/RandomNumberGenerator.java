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
 * This example demonstrates the traditional random number generator that we developed in a
 * prior class verses using the streams approach.  Not only can we generate random numbers
 * in a specified range, but we can get an array of random numbers in the quantity we ask
 * for all in a single line of code.
 *
 * @author Kent Yang
 */
public class RandomNumberGenerator {

  // Old school way (Java 2) was to iterate through a loop and repeatedly call this method
  public static int generateRandomValue(int lowerBound, int upperBound) {
    return lowerBound +
        (int) (Math.random() * (1 +  upperBound - lowerBound));
  }

  // New school Stream approach simply get all numbers you need returned in an array
  public static int[] getRandomNumbers(final int lower, final int upper, final int size) {

    return Stream.generate(Math::random)
        .mapToInt(n -> (int) (n * (upper - lower + 1)) + lower)
        .distinct()
        .limit(size)
        .sorted()
        .toArray();
  }

  public static void main(String args[]) {
    // how the rules have changed over the years
    System.out.println(new StringBuilder().append("California Super Lotto Plus numbers: ")
        .append(Arrays.toString(getRandomNumbers(1, 47, 5)))
        .append(" MEGA Number: " + generateRandomValue(1, 27))
        .toString());
  }
}
