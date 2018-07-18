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
package com.makesrc.examples.regex;

/**
 * This is a really simple of how to perform a literal replace with REGEX literal match.
 * Although the literal replace cause the sentence to no longer exercise the full alphabet set,
 * this example always takes me back fond memories of middle school typing class.
 *
 * @author Kent Yang
 */
public class ReplaceFox {
  public static void main(String args[])
  {
    // String provide an easy way to search and replace portions of a String of Text
    // Match: all literal occurances of the word fox with kangaroo
    System.out.println("The quick brown fox jumps over the lazy dog.".replaceAll("fox",
        "kangaroo"));
  }
}
