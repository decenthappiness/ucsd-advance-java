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
package com.makesrc.examples.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

/**
 * Example of a lambda with no arguments using Runnable Single Abstract Methods (SAM).  Furthermore,
 * demonstrate how concurrent programming with worker threads calculating fibonacci using recursion.
 *
 * @author Kent Yang
 */
public class FibonacciUsingExecutor {

  public static int fibonacci(int n) {
    return (n == 0) ? 0 : (n == 1) ? 1 : fibonacci(n - 1) + fibonacci(n - 2);
  }

  public static void fibWithStream() {

    IntStream.rangeClosed(0, 10)
        .forEach((i) -> System.out.println("fibonacci(" + i + "): " + fibonacci(i)));
  }

  public static void fibWithExecutor() {
    ExecutorService executor = Executors.newCachedThreadPool();

    executor.submit(() -> System.out.println("fibonacci(0)" + fibonacci(0)));
    executor.submit(() -> System.out.println("fibonacci(0)" + fibonacci(0)));
    executor.submit(() -> System.out.println("fibonacci(1)" + fibonacci(1)));
    executor.submit(() -> System.out.println("fibonacci(2)" + fibonacci(2)));
    executor.submit(() -> System.out.println("fibonacci(3)" + fibonacci(3)));
    executor.submit(() -> System.out.println("fibonacci(4)" + fibonacci(4)));
    executor.submit(() -> System.out.println("fibonacci(5)" + fibonacci(5)));
    executor.submit(() -> System.out.println("fibonacci(6)" + fibonacci(6)));
    executor.submit(() -> System.out.println("fibonacci(7)" + fibonacci(7)));
    executor.submit(() -> System.out.println("fibonacci(8)" + fibonacci(8)));
    executor.submit(() -> System.out.println("fibonacci(9)" + fibonacci(9)));
    executor.submit(() -> System.out.println("fibonacci(10)" + fibonacci(10)));
  }

  public static void fibWithCallable() {
    //ExecutorService executor = Executors.newCachedThreadPool();
    ExecutorService executor = Executors.newFixedThreadPool(12);

    List<Callable<Integer>> taskList = new ArrayList<>();

    taskList.add(() -> {
      return fibonacci(0);
    });
    taskList.add(() -> {
      return fibonacci(1);
    });
    taskList.add(() -> {
      return fibonacci(2);
    });
    taskList.add(() -> {
      return fibonacci(3);
    });
    taskList.add(() -> {
      return fibonacci(4);
    });
    taskList.add(() -> {
      return fibonacci(5);
    });
    taskList.add(() -> {
      return fibonacci(6);
    });
    taskList.add(() -> {
      return fibonacci(7);
    });
    taskList.add(() -> {
      return fibonacci(8);
    });
    taskList.add(() -> {
      return fibonacci(9);
    });
    taskList.add(() -> {
      return fibonacci(10);
    });

    List<Future<Integer>> resultList = null;
    try {
      resultList = executor.invokeAll(taskList);
      resultList.stream().forEach(FibonacciUsingExecutor::accept);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {

    long time = System.currentTimeMillis();
    fibWithStream();
    System.out.println("fibWithStream took: " + (System.currentTimeMillis() - time) + " ms");
    time = System.currentTimeMillis();
    fibWithExecutor();
    System.out.println("fibWithExecutor took: " + (System.currentTimeMillis() - time) + " ms");
    time = System.currentTimeMillis();
    fibWithCallable();
    System.out.println("fibWithCallable took: " + (System.currentTimeMillis() - time) + " ms");
  }

  private static void accept(Future<Integer> f) {
    try {
      System.out.println("fibonacci value:" + f.get());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }
}
