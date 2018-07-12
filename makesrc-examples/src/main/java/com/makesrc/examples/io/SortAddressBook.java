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
package com.makesrc.examples.io;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This is a sample driver show 2 approaches to processing data; one using the good old
 * Buffered and File Reader from Java IO and an alternative showing the new and improve
 * java.nio.Files class added in Java 8.
 *
 * @author Kent Yang
 */
public class SortAddressBook {
  private static final FirstNameComparator FIRSTNAMECOMPARATOR = new FirstNameComparator();

  public static void main(String[] args) throws IOException, URISyntaxException {
    // Step 1: Read Text From File
    List<String> textData = TextFileUtils.readTextFile("/addressbook.txt");

    // Step 2: Process Text Data
    textData = textData.stream().sorted(new FirstNameComparator()).collect(Collectors.toList());
    // Collections.sort(textData, FIRSTNAMECOMPARATOR);

    // Step 3: Write Text Data toFile
    TextFileUtils.writeTextFile(textData, "sortedAddressBook.txt");

    // Same processing using the NIO Files class added in Java 8
    // Step 1: Read Text file using Files as a stream
    try (Stream<String> stream = Files.lines(
        Paths.get((TextFileUtils.class.getResource("/addressbook.txt")).toURI()))) {
      // Step 2: Process Text Data
      textData = stream.sorted(FIRSTNAMECOMPARATOR).collect(Collectors.toList());
      // Step 3: Write Text Data toFile
      TextFileUtils.writeTextFile(textData, "sortedStreamAddressBook.txt");
    }
  }
}

class FirstNameComparator implements Comparator<String> {
  public int compare(String s1, String s2) {
    String[] st1 = s1.split(" ");
    String[] st2 = s2.split(" ");

    String firstName1 = st1[0].toUpperCase();
    String lastName1 = st1[1].toUpperCase();
    String firstName2 = st2[0].toUpperCase();
    String lastName2 = st2[1].toUpperCase();
    if (!(firstName1.equals(firstName2))) {
      return firstName1.compareTo(firstName2);
    } else {
      return lastName1.compareTo(lastName2);
    }
  }
}
