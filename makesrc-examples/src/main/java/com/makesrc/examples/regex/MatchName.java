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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a simple class to do a quick validation of names from the Person class using the
 * classes from the java REGEX package.
 *
 *
 * @author Kent Yang
 */
public class MatchName {
  public static void main(String args[]) {
    String nameToValidate = "John Smith";
    if (args.length > 0) {
      nameToValidate = args[0];
    }

    // Step 2 Get a Pattern Object, notice you do no instantiate
    Pattern p = Pattern.compile("^[a-zA-Z ]+");
    // Step 3 Pass String for matcher to match
    Matcher m = p.matcher(nameToValidate);
    // Step 4 Check match
    if (m.matches()) {
      System.out.println(nameToValidate + " is a valid name.");
    } else {
      System.out.println(nameToValidate + " is an invalid name.");
    }
  }
}
