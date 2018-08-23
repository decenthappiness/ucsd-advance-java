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
package com.makesrc.plugin;

import com.makesrc.examples.reflection.IPlugin;

/**
 * Simple class that implements the IPlugin interface from another module.  This is 
 * used to demonstrate how a 3rd party developer can developed to an interface and then
 * deploy the jar to a plugin framework that can load the class dynamically, create 
 * an instance and then execute it like a plugin.
 *
 * @author Kent Yang
 */
public class HelloPlugin implements IPlugin {
   public HelloPlugin() {}

   public void display() {
      System.out.println("Hello World! from Plugin.");
   }
}

