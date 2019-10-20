package view;

import application.CommunicationPC;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import model.CommunicatorTask;
import model.Determinator;
import util.Point;

public class GameOverviewController {

	private int turnNumber;

	@FXML
	private GridPane pane;

	@FXML
	private Circle circle;

	@FXML
	private TextField turns;

	@FXML
	private Button resetButton;

	@FXML
	private TextField currentTurn;
	
	private CommunicationPC comm;

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 */
	public GameOverviewController() {
		comm = new CommunicationPC();

	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		currentTurn.setText("Player");
		CommunicatorTask communicatorTask = new CommunicatorTask(comm, this);
		Thread communication = new Thread(communicatorTask);
		communication.start();
	}

	
	public void addPlayerPiece(Point point) {
		int x = point.x;
		int y = point.y;
		System.out.println("TESTI 1 -  x: " + x + " y: " + y);
		Circle circle = new Circle();
		pane.add(circle, y, x);
		System.out.println("TESTI 2 - x: " + x + " y: " + y);
		circle.setCenterX(100.0f);
		circle.setCenterY(100.0f);
		circle.setRadius(40.0f);
		circle.setFill(Paint.valueOf("YELLOW"));
		GridPane.setFillWidth(circle, true);
		GridPane.setFillHeight(circle, true);
		System.out.println("added player piece");
		turnNumber++;
	}
	
	public void addRobotPiece(Point point) {
		int x = point.x;
		int y = point.y;
		
		Circle circle = new Circle();
		pane.add(circle, y, x);
		circle.setCenterX(100.0f);
		circle.setCenterY(100.0f);
		circle.setRadius(40.0f);
		circle.setFill(Paint.valueOf("DODGERBLUE"));
		GridPane.setFillWidth(circle, true);
		GridPane.setFillHeight(circle, true);
		System.out.println("added robot piece");
		turnNumber++;
	}
	

	@FXML
	private void changeTurn() {
		comm.sendTurnChange();
		currentTurn.setText("Robot");
	}

	@FXML
	private void reset() {
		Node node = pane.getChildren().get(0);
		pane.getChildren().clear();
		pane.getChildren().add(0, node);
		turnNumber = 0;
		turns.setText("" + turnNumber);
	}

	public void setMainApp(Main main) {

	}
}