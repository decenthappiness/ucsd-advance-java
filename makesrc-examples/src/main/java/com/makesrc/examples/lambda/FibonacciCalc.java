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
package com.makesrc.examples.lambda;

/**
 * Example of a lambda with no arguments using Runnable Single Abstract Methods (SAM).  Furthermore,
 * demonstrate how concurrent programming with worker threads calculating fibonacci using recursion.
 *
 * @author Kent Yang
 */
public class FibonacciCalc {

  public static int fibonacci(int n) {
    return (n == 0) ? 0 : (n == 1) ? 1 : fibonacci(n - 1) + fibonacci(n - 2);
  }

  public static void main(String[] args) {
    new Thread(() -> System.out.println("fibonacci(0)" + fibonacci(0))).run();
    new Thread(() -> System.out.println("fibonacci(1)" + fibonacci(1))).run();
    new Thread(() -> System.out.println("fibonacci(2)" + fibonacci(2))).run();
    new Thread(() -> System.out.println("fibonacci(3)" + fibonacci(3))).run();
    new Thread(() -> System.out.println("fibonacci(4)" + fibonacci(4))).run();
    new Thread(() -> System.out.println("fibonacci(5)" + fibonacci(5))).run();
    new Thread(() -> System.out.println("fibonacci(6)" + fibonacci(6))).run();
    new Thread(() -> System.out.println("fibonacci(7)" + fibonacci(7))).run();
    new Thread(() -> System.out.println("fibonacci(8)" + fibonacci(8))).run();
    new Thread(() -> System.out.println("fibonacci(9)" + fibonacci(9))).run();
    new Thread(() -> System.out.println("fibonacci(10)" + fibonacci(10))).run();
  }
}
