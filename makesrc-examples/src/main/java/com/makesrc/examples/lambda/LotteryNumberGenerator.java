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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

/**
 * This is another example that demonstrates the power of lambdas.  Note in core Java programming
 * class, we solve the Lottery Number homework assignment using a much more straight forward
 * approach using loops.  The lambda approach is much more readable and reduces down to a few lines
 * of code (5).  We are also demonstrating how to create our custom Functional Interface with
 * generics; namely the RangedRandom interface with generic support.
 *
 * @author Kent Yang
 */
public class LotteryNumberGenerator {
  public static void main(String[] args) {

    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    RangedRandom<Integer, Integer, Integer> lotto =
        (lower, upper) -> ((int) (Math.random() * (upper - lower + 1))) + lower;

    System.out.print(
        "Your lucky SuperLotto Plus numbers for " + dateFormat.format(LocalDate.now()) + " are:");
    IntStream.generate(() -> lotto.generate(1, 47))
        .distinct()
        .limit(5)
        .sorted()
        .forEach(n -> System.out.printf("%3d", n));
    // Note: we caould do this all on one line of code if Super Lotto doesn't keep changing the
    // the rules all the time.
    System.out.println(" with MEGA number: " + lotto.generate(1, 27));
  }
}
