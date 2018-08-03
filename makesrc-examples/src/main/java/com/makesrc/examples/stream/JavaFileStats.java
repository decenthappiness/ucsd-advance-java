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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LongSummaryStatistics;

/**
 * This application demonstrates one of the more powerful reducers that can return an
 * Object summarizing all the statics from traversing the specified file system and using
 * file size as a source for data.
 *
 * @author Kent Yang
 */
public class JavaFileStats {
  public static void main(String[] args) throws IOException {
    Path dir = Paths.get("..");
    if (args.length > 0) {
      dir = Paths.get(args[0]);
    }

    System.out.println("File:" + dir.toAbsolutePath());

    LongSummaryStatistics stats =
        Files.walk(dir).mapToLong(JavaFileStats::applyAsLong).summaryStatistics();

    System.out.println("Max:     " + stats.getMax());
    System.out.println("Min:     " + stats.getMin());
    System.out.println("Average: " + stats.getAverage());
    System.out.println("Count:   " + stats.getCount());
    System.out.println("Sum:     " + stats.getSum());
  }

  private static long applyAsLong(Path path) {
    long size = 0;
    try {
      size = Files.size(path);
    } catch (IOException e) {
      System.err.println("Invalid Path:" + path);
    }
    return size;
  }
}
