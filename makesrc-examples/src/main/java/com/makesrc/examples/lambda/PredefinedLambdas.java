package com.makesrc.examples.lambda;
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

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * This great little example brings it home by showing each of the major category of Functional
 * interfaces defined in java.util.function package.  They are consumer, predicate, supplier,
 * function and operator.  After explaining all of these in class we can example of how it can be
 * applied.  Makes understanding streams so much easier now that we understand the type of
 * functional interfaces we can use as lambdas in streams.
 *
 * @author Kent Yang
 */
public class PredefinedLambdas {
  public static void main(String[] args) {

    final String[] array =
        new String[] {"The", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog."};

    // Consumer take a single parameter and return no result (void)
    System.out.println("\nShowing Built-in Consumer type Lambda:");
    Consumer<String> consumer =  s -> System.out.printf("%6s ", s);
    Arrays.stream(array).forEach(consumer);
    Arrays.stream(array).forEach(s -> System.out.printf("%6s ", s));


    System.out.println("\nShowing Built-in Predicate type Lambda:");
    Predicate<String> predicate = s -> s.equals("fox");
    Arrays.stream(array).filter(predicate).forEach(consumer);

    System.out.println("\nShowing Built-in Supplier type Lambda:");
    Supplier<String> supplier = () -> ZonedDateTime.now().toString();
    Stream.generate(supplier).limit(3).forEach(consumer);

    System.out.println("\nShowing Built-in Function type Lambda:");
    Function<String, String> function = s -> s.toLowerCase();
    Arrays.stream(array).map(function).forEach(consumer);

    System.out.println("\nShowing Built-in Operator type Lambda:");
    UnaryOperator<String> operator = s -> s.toUpperCase();
    Arrays.stream(array).map(operator).forEach(consumer);
  }
}
