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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is just a simple example demonstrating how a REGEX can be used to validate a phone number.
 * Note that we create a constant (public static final) to hold the compiled REGEX so we don't
 * incur the cost of regenerating the REGEX syntax tree every time we need to use the REGEX. This
 * example also demonstrates creating groups to represent the different components of the phone
 * number (area code, prefix) so that it can be reformatted nicely into the proper display format.
 * Reformatting makes the data consistent for the backend data services and pretty from the users
 * viewing the data.
 *
 * @author Kent Yang
 */
public class MatchPhone {
  public static final Pattern PHONE_VALIDATOR =
      Pattern.compile("\\(?([0-9]{3})\\)?[- ]?(\\d{3})[- ]?([0-9]{4})\\b");

  public static void main(String args[]) {
    Matcher m = PHONE_VALIDATOR.matcher("8588675309");  // Jenny's number hee hee
    if(m.matches())
    System.out.println(m.replaceAll("($1) $2-$3"));
    m = PHONE_VALIDATOR.matcher("888-888-8888");  // Lucky number indeed
    if(m.matches())
    System.out.println(m.replaceAll("($1) $2-$3"));
    m = PHONE_VALIDATOR.matcher("444-444");  // Invalid phone number
    if(m.matches())
      System.out.println(m.replaceAll("($1) $2-$3"));
    else
      System.out.println("444-444 is invalid!");
  }
}
