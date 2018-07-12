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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * This class demonstrate a quick framework utility class for reading a text file
 * in and returning List of lines or as a Stream of lines.  It also handles
 * writing a collection of Strings back out to a text files using Java IO.
 *
 * @author Kent Yang
 */
public final class TextFileUtils {

  // Note private constructor so only static utility methods from this class can
  // be called.
  private TextFileUtils() {
  }

  public static List<String> readTextFile(String inputFile) {
    List<String> list = new ArrayList<>();
    try (BufferedReader in = new BufferedReader(
        new InputStreamReader(TextFileUtils.class.getResourceAsStream(inputFile)))) {

      for (String line = null; (line = in.readLine()) != null; ) {
        list.add(line);
      }
    } catch (IOException e) {
      System.err.println("Failed to process input file." + e);
    }
    return list;
  }

  public static Stream<String> readTextFileAsStream(String inputFile) {
    try (Stream<String> stream = Files.lines(
        Paths.get((TextFileUtils.class.getResource(inputFile)).toURI()))) {
      return stream;
    } catch (IOException | URISyntaxException e) {
      System.err.println("Failed to process input file." + e);
    }
    return Stream.empty();
  }

  public static void writeTextFile(Collection<String> c, String outputFile) {
    try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)))) {
      for (String str : c) {
        out.println(str);
      }
    } catch (IOException e) {
      System.err.println("Failed to process output file" + e);
    }
  }
}
