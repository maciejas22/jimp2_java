package GUI;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
 
public class App extends Application {
  @Override
  public void start(Stage primaryStage) {

    try {
      AnchorPane root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
      Scene scene = new Scene(root);
      primaryStage.setTitle("Graph Analyzer vlo.2");
      primaryStage.setScene(scene);
      primaryStage.show();
    } catch (IOException e) { }

  }
 
 public static void main(String[] args) {
        launch(args);
    }
}
