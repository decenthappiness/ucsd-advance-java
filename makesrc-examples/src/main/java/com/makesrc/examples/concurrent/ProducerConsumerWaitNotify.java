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

/**
 * A producer consumer example showing how to the primitive wait notify scheme.
 *
 * @author Kent Yang
 */

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumerWaitNotify
    implements Runnable {
  private List<String> mailBox = new ArrayList<String>();

  public static void main(String args[]) {
    ProducerConsumerWaitNotify wne = null;
    final Thread service = new Thread(wne = new ProducerConsumerWaitNotify());
    service.start();

    new Thread(new Producer(wne), "producer 1").start();
    new Thread(new Producer(wne), "producer 2").start();
    new Thread(new Producer(wne), "producer 3").start();
  }

  public void run() {
    try {
      synchronized (this) {
        while (!Thread.currentThread().isInterrupted()) { // run forever until interrupted!
          for (String s : mailBox)
            System.out.println("Consumer clear message: " + s);

          mailBox.clear();
          this.wait();
        }
        System.out.println("Thread interrupted.  Exiting service without exception...");
      }
    } catch (InterruptedException e) {
      // thread interrupted via thread.interrupt()
      System.out.println("Thread interrupted.  Exiting service...");
    }
  }

  void addMessage(String msg) {
    //synchronized (this) {
      System.out.println(Thread.currentThread().getName() +
          " putting a message in the consumer queue:" + msg);
      this.mailBox.add(msg);
      this.notify();
    //}
  }
}

class Producer
    implements Runnable {
  private ProducerConsumerWaitNotify wne = null;

  Producer(ProducerConsumerWaitNotify processor) {
    wne = processor;
  }

  public void run() {
    int counter = 0;

    try {
      while (!Thread.currentThread().isInterrupted()) {  // run forever until interrupted!
        counter++;
        wne.addMessage(Thread.currentThread().getName() +
            " add new message " + counter + " seconds.");
        Thread.currentThread().sleep(500);
      }
      System.out.println("Producer Thread interrupted.  Exiting service without exception...");
    } catch (InterruptedException e) {
      // thread interrupted via thread.interrupt()
      System.out.println("Producer Thread interrupted.  Exiting service...");
    }
  }
}

