package application;

import java.io.IOException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.CommunicatorTask;
import view.GameOverviewController;

/**
 * @author Pietari Järvi, Jetro Saarti, Kim Widberg, Olli Kaivola
 */
public class Main extends Application {
	private Stage primaryStage;
	private AnchorPane gameOverview;
	
	/**
	 * Runs the application and sets the stage
	 */
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("ConnectFour");
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(850);
		showGameOverview();
	}
	
	/**
	 * Sets the game UI to the stage
	 */
	public void showGameOverview() {
		try {
			// Load layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/view/GameOverview.fxml"));
			gameOverview = (AnchorPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(gameOverview);
			primaryStage.setScene(scene);
			primaryStage.show();

			// Give the controller access to the main app.
			GameOverviewController controller = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the main stage.
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
	
		launch(args);
	}

}
