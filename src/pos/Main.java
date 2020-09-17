package pos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * APCS-A Spring Project
 * Arnav Guneta and Andrew Ferrar
 * Period 2
 * Johns Creek High School
 * POS System
 * 4/11/18
 */

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	// creates home window
	@Override public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
		primaryStage.setTitle("POS Home");
		primaryStage.setScene(new Scene(root, 720, 400));
		primaryStage.show();
	}
}
