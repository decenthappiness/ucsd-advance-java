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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * This class demonstrates how to implement the Disk Usage command capability from Unix in Java.
 * It uses a loop to iterate through the file system as oppose to recursion.  Loops offers the
 * best performance.
 *
 * @author Kent Yang
 */
public class DiskUsageLoops {
  public static void main(String[] args) {
    String startingDir = ".";
    if(args.length > 0) {
      startingDir = args[0];
    }

    File startingDirFile = new File(startingDir);
    if(startingDirFile.isFile()) {
      System.out.printf("%-10d     %s\n", startingDirFile.length(),
          startingDirFile.getAbsoluteFile());
      return;
    }

    List<File> dirToProcess = new ArrayList<>();
    dirToProcess.add(new File(startingDir));

    long sum = 0;

    while(!dirToProcess.isEmpty()) {
      // first in, first out
      File nextDir = dirToProcess.remove(0);
      File[] files = nextDir.listFiles();

      for(File f : files) {
        if (f.isDirectory())
          dirToProcess.add(f);
        else {
          sum += f.length();
          System.out.printf("%-10d     %s\n", f.length(), f.getAbsoluteFile());
        }
      }
    }
    System.out.printf("%-10d     %s\n", sum, startingDir);
  }
}
