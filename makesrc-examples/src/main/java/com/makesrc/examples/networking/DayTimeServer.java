package com.makesrc.examples.networking;
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
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Demonstrates how to implement a server/service that provide the DayTime service on port 13.
 * Complete with shutdown to properly shutdown the service cleanly.
 *
 * @author Kent Yang
 */
public class DayTimeServer implements Runnable {

  public void run() {
    // we are going to use the standard DayTime Service port
    // because we are going to simulate a time service from our PC
    try (ServerSocket serverSocket = new ServerSocket(13)) {
      System.out.println("Started makesrc DayTime Server...");
      while (!Thread.currentThread().isInterrupted()) { // run forever until interrupted!
        try{
          serverSocket.setSoTimeout(500);
          Socket socket = serverSocket.accept(); // blocking call
          new Thread(this.new GenTimeWorker(socket)).start();
        } catch (SocketTimeoutException se) {
          System.out.print(".");
        } catch (IOException ie) {
          System.err.println("Failed to create client connection.");
        }
      }
      System.out.println("Server exiting cleanly...");
    } catch (IOException e) {
      System.err.println("Failed to create service, port not available.");
      e.printStackTrace();
    }
  }

  public static void main(String args[]) {
    final Thread service = new Thread(new DayTimeServer());
    service.start();

    // anonymous shutdown hook
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println(
          "Caught <ctrl-c>, shutting down DayTime Service!");
      service.interrupt();
      try {
        // Wait for cleanup to complete
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }));
  }

  private class GenTimeWorker implements Runnable {
    GenTimeWorker(Socket s) {
      this.socket = s;
    }

    public void run() {
      try (PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true)) {
        System.out.println("s");
        out.println(
            java.util.Calendar.getInstance().getTime().toString());
        out.flush();
        this.socket.close();
      }
      catch( IOException e)
      {
        e.printStackTrace();
      }
    }
    private Socket socket;
  }
}
