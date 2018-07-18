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
import java.util.ArrayList;
import java.util.List;

/**
 * This class demonstrates how to implement the Disk Usage command capability from Unix in Java.
 * It uses recursion to naturally traverse the file system (tree structure)  The only concern with
 * loops is that for deeply nested trees there is a potential for stackoverflows.
 *
 * @author Kent Yang
 */
public class DiskUsageRecursive {

  public static long diskUsage(File startingDir) throws IOException {
    File [] files = startingDir.listFiles();
    long sum = 0;
    for(File f : files) {
      if(f.isDirectory()) {
        sum += diskUsage(f); // recursive call
      }
      else {
        sum += f.length();
        System.out.printf("%-10d     %s\n", f.length(),
            f.getCanonicalFile());
      }
    }
    return sum;
  }

  public static void main(String[] args) {
    String startingDir = ".";
    if(args.length > 0) {
      startingDir = args[0];
    }

    try {
      File startingDirFile = new File(startingDir);
      if(startingDirFile.isFile()) {
        System.out.printf("%-10d     %s\n", startingDirFile.length(),
            startingDirFile.getCanonicalFile());
        return;
      }

      var sum = diskUsage(startingDirFile);
      System.out.printf("%-10d     %s\n", sum, startingDir);
    } catch(IOException x) {
      System.err.println("Failed to process DiskUsageRecursive request: " + x);
    }
  }
}
