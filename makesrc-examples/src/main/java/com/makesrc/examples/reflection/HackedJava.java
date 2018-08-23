package com.makesrc.examples.reflection;
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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Demonstrates reflection and how something that is declared private can be modified and
 * hacked by changing the accessibility.
 *
 * @author Kent Yang
 */
public class HackedJava {

  public static void main(String[] args) {

    String string = "Hello World!";
    Class clazz = string.getClass();

    Constructor[] constructors = clazz.getDeclaredConstructors();
    System.out.println("Display all constructors: ");
    Arrays.stream(constructors).forEach(System.out::println);

    Field[] fields = clazz.getDeclaredFields();
    System.out.println("Display all fields: ");
    Arrays.stream(fields).forEach(System.out::println);

    Method[] methods = clazz.getDeclaredMethods();
    System.out.println("\nDisplay all methods: ");
    Arrays.stream(methods).forEach(System.out::println);

    try {
      Field field = clazz.getDeclaredField("value");
      field.setAccessible(true);
      System.out.println("Field: " + field);
      //byte[] valArray = (byte[]) field.getGenericType();
      field.set(string, "Hacked =)".getBytes());
      System.out.println("Hacked String: " + string);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}
