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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * This class demonstrates how to implement the Disk Usage command capability from Unix in Java
 * using the latest Streams API.  It follows the best practices of pulling the lambda functions
 * out into a separate method and then using a method reference in the chained stream call to
 * keep it concise.
 *
 * Run Unix Command: du -b -a -c to confirm.  The -b is used to denote size
 * in bytes, -a is used to show all files (not just directories (default) and -c
 * will show the total.
 *
 * @author Kent Yang
 */
public class DiskUsageStream {

  private static long getSize(Path p) {
    long size = 0;
    try {
      if (!Files.isDirectory(p)) {
        size = Files.size(p);
      }
    } catch (IOException e) {
      System.err.println("Failed to process DiskUsageStream request: " + e);
    }
    return size;
  }

  private static void displayItemSize(Path p) {
    try {
      if (!Files.isDirectory(p)) {
        System.out.printf("%-10d     %s\n", Files.size(p), p);
      }
    } catch (IOException e) {
      System.err.println("Failed to process DiskUsageStream request: " + e);
    }
  }

  public static void main(String[] args) {
    String startingDir = ".";
    if (args.length > 0) {
      startingDir = args[0];
    }

    try {
      Path startingPath = Paths.get(startingDir);
      if (!Files.isDirectory(startingPath)) {
        System.out.printf("%-10d     %s\n", Files.size(startingPath),
            startingPath);
        return;
      }
    } catch (IOException x) {
      System.err.println("Failed to process DiskUsageStream request: " + x);
    }

    try (Stream<Path> entries = Files.walk(Paths.get(startingDir))) {
      long totalSize =
          entries.peek(DiskUsageStream::displayItemSize).mapToLong(DiskUsageStream::getSize).sum();
      System.out.printf("%d\t%s\n", totalSize, startingDir);
    } catch (IOException e) {
      System.err.println("Failed to process DiskUsageStream request: " + e);
    }
  }
}
