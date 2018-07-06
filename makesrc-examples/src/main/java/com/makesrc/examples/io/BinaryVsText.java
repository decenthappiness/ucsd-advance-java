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

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This is a simple example demonstrating how to use a binary
 * output stream vs a character base output writer.  It writes
 * the infamous CAFE BABE to a binary file but writes the
 * characters representing the number that is CAFE BABE.
 *
 * @author Kent Yang
 */
public class BinaryVsText {
  public static void main(String[] args) {
    // Note we are using try with resource here
    try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("number.bin"));
         PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("number.txt")))) {

      // write number as binary
      dos.writeLong(3405691582L);
      // write number as text
      pw.println(3405691582L);

      // normally we don't need to close because of AutoClose but we want to get the size of
      // of the file so we need to close it before executing method to get the size of file.
      dos.close();
      pw.close();

      // We write the files out to get an idea of the size of these 2 different types
      File file = new File("number.bin");
      System.out.println("Binary file size: " + file.length());
      file = new File("number.txt");
      System.out.println("Text file size: " + file.length());
    } catch (IOException e) {
      System.err.println("Failed to write files out for comparison: " + e);
    }
  }
}
