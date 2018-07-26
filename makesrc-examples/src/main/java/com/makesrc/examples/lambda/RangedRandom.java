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

import java.util.stream.Stream;

/**
 * We define our own functional interface to better represent our domain of generating a random
 * ranged of values confined to a range.  This is a very useful functional interface as an
 * entire new class of random number generator can be creaated to serve the goal of solving
 * the random number generation problem in a very generic way.  For instance we can use this class
 * in numeric ranges but also double ranges or even String ranges.  For instance, a range of words
 * in the dictionary between 2 words.  There are such a wide range of applications.
 *
 * @author Kent Yang
 */
@FunctionalInterface
public interface RangedRandom<L, U, R> {
  public R generate(L lowerBound, U upperBound);
}
