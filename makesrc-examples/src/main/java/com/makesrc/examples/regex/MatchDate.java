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
 * This is just a quick example demonstrating how tot quickly check that a String matches a
 * regular expression for formatted date of mm-yy.  The key is that REGEX ors ! need to be part
 * of a group ().
 *
 * @author Kent Yang
 */
public class MatchDate {
  public static void main(String args[]) {
    // String provide an easy way to match a String of Text
    // Match: character class to match anything except a question mark
    // one or more times.  Then make sure there is a question mark at the end
    System.out.println(
        "Date (mm-yy) is Christmas? " + "12-25".matches("(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])"));
  }
}
