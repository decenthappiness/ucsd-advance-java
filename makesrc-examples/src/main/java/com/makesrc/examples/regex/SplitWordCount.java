package com.makesrc.examples.regex;
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

/**
 * This is a simple example of how to use REGEX character sets to match multiple delimiters to
 * tokenize a string.  To tokenize a string is something that is very commonly done in programming.
 * This example also shows how to convert any array to a stream for processing and how to use
 * the peek as a way to inject an identity check for debugging our stream processing.  We use
 * count as the terminal function to count the number of words.  Pretty nifty.
 *
 * @author Kent Yang
 */
public class SplitWordCount {
  public static void main(String args[]) {
    // String Provide an easy way to split a strinig into tokens
    // which you do quite often in programming.
    // Match: Character class include space, periods and -
    // So tokens will be divided between space, periods and dashes (3 characters)
    String array[] = "I am Sam. I am Sam. Sam-I-Am."
        .split("[. -]");
    System.out.println("Total: " + Arrays.stream(array)
        .filter(w -> w.length() > 0) // we need to filter out empty strings
        .peek(System.out::println)  // just for grins, we print out each word
        .count());
  }
}
