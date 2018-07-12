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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * This class demonstrates how to plugin into the File IO Framework
 * and add a Decorator that supports logging data to logger.  You
 * get a quick intro to logging API as well as learning about the
 * Decorator design pattern.
 *
 * @author Kent Yang
 */

public class LoggerInputStream extends FilterInputStream {
  // Use the static method to get a logger object capable of logging
  private static Logger logger =
      Logger.getLogger(LoggerInputStream.class.getName());

  public LoggerInputStream(InputStream is) {
    super(is);
  }

  public static void main(String[] args) {
    try (InputStream is = new LoggerInputStream(
        new BufferedInputStream(
                LoggerInputStream.class.getResourceAsStream("/addressbook.txt")))) {

      byte[] buffer = new byte[120];
      for (int offset = 0, bytesRead = 0; bytesRead >= 0; ) {
        bytesRead = is.read(buffer, 0, buffer.length);
        // We can do whatever we need to do to process the buffer
        // but we simply iterate through the file to demonstrate
        // a custom logging input stream (a decorator)
      }
      is.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public int read()
      throws IOException {
    int c = super.read();
    logger.info("" + (char) c);
    return c;
  }

  public int read(byte[] b, int offset, int len)
      throws IOException {
    int bytesRead = super.read(b, offset, len);
    StringBuffer sb = new StringBuffer();

    for (int i = offset; i < offset + bytesRead; i++) {
      sb.append((char) b[i]);
    }

    // Default only messages log at the info level or
    // higher will appear in the console output
    // Call the logger's set level to change it
    logger.info(sb.toString()); // here is where we log

    return bytesRead;
  }
}
