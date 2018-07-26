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

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 * This class is a JavaFx example that shows how to handle events using 2 types of event handling,
 * one with an anonymous inner class and another using lambda.  Of course the lambda button is
 * more concise and less coding.  Lambda is the newer approach but we also want to demonstrate the
 * scoping differences between a lambda and anonymous inner class.  Anonymous inner class creates
 * a new scope vs lambda.  Not the point of this example but we also want to point out how similar
 * it is to Swing JavaFx is and note the differences.  No main method is necessary unless we want
 * to handle command line options.  Instead we start a Fx app in the start method.  There are so
 * many things we can point out during class such as how JavaFx can support both a UI layout tool
 * like scene builder and programmatically in code like we did using Swing.
 *
 * @author Kent Yang
 */
public class HelloWorldFx extends Application implements EventHandler<ActionEvent>{

  @Override
  public void start(Stage primaryStage) throws Exception{
    primaryStage.setTitle("Hello World FX!");

    FlowPane flowPane = new FlowPane(8, 8);
    flowPane.getChildren().add(genButtonAIC("Anonymous Inner Class handle click me!"));
    flowPane.getChildren().add(genButtonLambda("Lambda handle click me!               "));
    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(flowPane);
    primaryStage.setScene(new Scene(borderPane, 600, 400));

    primaryStage.show();
  }

  private Button genButtonLambda(String label) {
    Button button = new Button(label);
    button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    button.setOnAction(e -> System.out.println(label));

    return button;
  }

  private Button genButtonAIC(String label) {
    Button button = new Button(label);
    button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

    // Using anonymous inner classes
    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent event) {
        System.out.println(label);
      }
    });
    // Using the main application class as the even handler.
    //button.setOnAction(this);
    return button;
  }

  // From main class has access to the stateful info of main class
  @Override
  public void handle(ActionEvent event) {
    System.out.println("From main class!");
  }

}
