package com.makesrc.examples.concurrent;
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


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A producer consumer example showing aspects of using the java concurrent library for
 * writing multithreaded app without all the synchronization blocks and wait notify
 * scheme.
 *
 *
 * @author Kent Yang
 */
public class ProducerConsumerBlockingQ
    implements Runnable {
  private BlockingQueue<String> mailBox = new LinkedBlockingQueue<String>();

  public static void main(String args[]) {
    ProducerConsumerBlockingQ wne = null;
    final Thread service = new Thread(wne = new ProducerConsumerBlockingQ());
    service.start();

    final Thread sender = new Thread(new ProducerQ(wne));
    sender.start();
    final Thread sender1 = new Thread(new ProducerQ(wne));
    sender1.start();
    final Thread sender2 = new Thread(new ProducerQ(wne));
    sender2.start();
  }

  public void run() {
    try {
      while (!Thread.currentThread().isInterrupted()) { // run forever until interrupted!
        List<String> processList = new ArrayList<String>();
        mailBox.drainTo(processList);
        for (String s : processList)
          System.out.println("Clear message: " + s);
        processList.clear();
      }
      System.out.println("Thread interrupted.  Exiting service without exception...");
    } finally {
    }
  }

  void addMessage(String msg) {
    try {
      System.out.println("Putting a message in the processors queue:" + msg);
      this.mailBox.put(msg);
    } catch (InterruptedException e) {
      //thread interrupted via thread.interrupt()
      System.out.println("Thread interrupted.");
    } finally {
    }
  }
}

class ProducerQ
    implements Runnable {
  private ProducerConsumerBlockingQ wne = null;

  ProducerQ(ProducerConsumerBlockingQ processor) {
    wne = processor;
  }

  public void run() {
    int counter = 0;

    try {
      while (!Thread.currentThread().isInterrupted()) {  // run forever until interrupted!
        counter++;
        wne.addMessage("Add new message " + counter + " seconds.");
        Thread.currentThread().sleep(1000);
      }
      System.out.println("ProducerQ Thread interrupted.  Exiting service without exception...");
    } catch (InterruptedException e) {
      // thread interrupted via thread.interrupt()
      System.out.println("ProducerQ Thread interrupted.  Exiting service...");
    }
  }
}


