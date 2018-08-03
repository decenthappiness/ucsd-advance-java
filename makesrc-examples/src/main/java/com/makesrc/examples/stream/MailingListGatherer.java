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

import com.makesrc.examples.io.Person;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This examples shows how we can quickly generate a mailing list using streams and a
 * reducer to collect all the emails into a single String.  The first method shows the
 * traditional reducer with the accumulator, the second shows reduce returning an
 * Optional object, the last shows the convenient reducer that collects email addresses
 * joined using specified delimiter.
 *
 * @author Kent Yang
 */
public class MailingListGatherer {
  public static void main(String[] args) {
    Person[] array =
        new Person[] {
            new Person("Kent", "Yang", "kent.yang@gmail.com", "111-1234"),
            new Person("David", "Yang", "david.yang@yahoo.com", "333-3333"),
            new Person("John", "Yang", "john.yang@hotmail.com", "555-5555"),
            new Person("Jay", "Leno", "jay_leno@nbc.com", "111-1111"),
            new Person("Joan", "Alberts", "jalberts@gmail.com", "222-2222"),
            new Person("David", "Letterman", "david.letterman@cbs.com", "222-2468"),
            new Person("Jenny", "Jenny", "jenny.jenny@hotmail.com", "867-5309"),
            new Person("Mike", "Trout", "bigFish27@angels.com", "272-7272")
        };

    Arrays.stream(array).map(Person::getEmail)
        .reduce((a, b) -> a.concat(b + ", ")).ifPresent(System.out::println);
    Optional<String> mailingList = Arrays.stream(array)
        .map(Person::getEmail)
        .reduce((a, b) -> a.concat(b + ", "));
    System.out.println("Mailing List 1: " + mailingList.orElse("none found!"));
    String mailingList2 = Arrays.stream(array)
        .map(Person::getEmail)
        .collect(Collectors.joining(", "));
    System.out.println("Mailing List 2 :" + mailingList2);
  }
}
