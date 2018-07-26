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

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * This is a quick look at using lambdas for event handling rather than the typical use case where
 * we use it as part of the streams for queries.  There is only one line of code needed vs the
 * traditional Anonymous inner classes where we had a lot of extra boiler plate code.  Also we are
 * reminded of the best practice of keeping our Streams processing lean by moving multi-line
 * lambdas to a separate method that we can reference as part of the stream.  It keeps the stream
 * expression more concise and much more readable.
 *
 * @author Kent Yang
 */
public class SwingWithLambda {
  public static void main(String args[]) {
    JFrame app = new JFrame("Hello World");
    app.setSize(300, 200);
    JButton button = new JButton("Click me!");
    // ActionListener is a Functional Interface even if the documentation
    // doesn't have the @FunctionalInterface annotation.  ActionListener
    // only has 1 method.

    button.addActionListener(a -> System.out.println(a.getActionCommand()));
    app.add(button);
    app.addWindowListener(getCloseActionCallback());
    app.setVisible(true);
  }

  private static WindowAdapter getCloseActionCallback() {
    return new WindowAdapter() {
      @Override public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        System.exit(0);
      }
    };
  }
}
