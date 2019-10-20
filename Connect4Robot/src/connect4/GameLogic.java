package connect4;

import sensors.ColorTester;
import util.Point;

/**
 * GameLogic class contains the game board, robot position and the current
 * situation of the game. Used by behaviours.
 *
 */
public class GameLogic {
	private int columns = 7; // game board columns
	private int rows = 6; // game board rows

	// Game board that contains the information about the pieces in the slots
	// (empty, player piece or robot piece).
	private int[][] gameGrid = new int[columns][rows];

	// Starting position; y-position (color sensor lifter) starts in top position
	private Point currentLocation = new Point(0, 5);

	// Player's turn by default
	private boolean isRobotsTurn = false;

	// Has the robot read the game board (read once per turn)
	private boolean gameBoardReadComplete = false;

	// Has the robot received a drop point from PC (received once per turn)
	private boolean dropPointReceived = false;

	private Point calculatedDropPoint = null;

	// Has the robot dropped its game piece (dropped once per turn)
	private boolean hasDroppedPiece = true;

	// Calculated place where the robot drops its game piece
	private Point calculatedMovePoint = null;

	// Coordinates of the last (new) player piece that the robot has read
	private Point playerMove = null;

	public GameLogic() {
		gameGrid = new int[columns][rows];
	}

	/**
	 * Has the robot received the drop point this turn
	 * 
	 * @return
	 */
	public boolean getDropPointReceived() {
		return dropPointReceived;
	}

	/**
	 * Sets the info about the drop point
	 * 
	 * @param dropPointReceived
	 */
	public void setDropPointReceived(boolean dropPointReceived) {
		this.dropPointReceived = dropPointReceived;
	}
	
	/**
	 * Returns calculated drop point
	 * @return calculatedDropPoint
	 */
	public Point getCalculatedDropPoint() {
		return calculatedDropPoint;
	}
	
	/**
	 * returns path to calculated point
	 * @return p - coordinates to calculated point
	 */
	public Point getPathToCalculatedPoint() {
		Point p = null;
		try {
			int x = calculatedDropPoint.x - currentLocation.x;
			int y = calculatedDropPoint.y - currentLocation.y;
			p = new Point(x, y);
		} catch (Exception e) {
			System.out.println("getCalculatedMove() KABOOM");
		}
		return p;
	}

	/**
	 * Returns the last player move
	 * 
	 * @return coordinates the the last player move
	 */
	public Point getPlayerMove() {
		Point p = playerMove;
		playerMove = null;
		return p;
	}

	/**
	 * Saves the last player move after reading the value
	 * 
	 * @param playerMove
	 */
	public void setPlayerMove(Point playerMove) {
		this.playerMove = playerMove;
	}

	/**
	 * Gets the turn information. True when robot's turn.
	 * 
	 * @return
	 */
	public boolean getIsRobotsTurn() {
		return isRobotsTurn;
	}

	/**
	 * Sets the turn information (player or robot). True when it's robot's turn.
	 * 
	 * @param b
	 */
	public void setIsRobotsTurn(boolean b) {
		isRobotsTurn = b;
	}

	/**
	 * Is the robot in start position?
	 * 
	 * @return
	 */
	public boolean inStartPosition() {
		if (currentLocation.x < 0)
			return true;
		return false;
	}

	/**
	 * Saving the calculated next robot move (from PC), also saving the move to the
	 * game board grid.
	 * 
	 * @param point
	 */
	public void setCalculatedMove(Point point) {
		calculatedMovePoint = point;
		gameGrid[point.x][point.y] = ColorTester.COLOR_ROBOTPIECE;
	}

	/**
	 * Returns the next drop point (calculated move).
	 * 
	 * @return coordinates of the move
	 */
	public Point getCalculatedMove() {
		int x = calculatedMovePoint.x - currentLocation.x;
		int y = calculatedMovePoint.y - currentLocation.y;
		return new Point(x, y);
	}

	/**
	 * Has the robot read the game board this turn
	 * 
	 * @return true if the game board has been read
	 */
	public boolean getGameBoardReadComplete() {
		return gameBoardReadComplete;
	}

	/**
	 * Set the game board read status
	 * 
	 * @param completed true if the board has been read
	 */
	public void setGameBoardReadComplete(boolean completed) {
		gameBoardReadComplete = completed;
	}

	/**
	 * Sets the current location of the robot
	 * 
	 * @param point
	 */
	public void setLocation(Point point) {
		currentLocation = point;
	}

	/**
	 * Updates the robot location
	 * 
	 * @param point the amount of x and y change as x,y coordinate
	 */
	public void locationChange(Point point) {
		currentLocation.x += point.x;
		currentLocation.y += point.y;
	}

	/**
	 * Robot's current location (x,y)
	 * 
	 * @return
	 */
	public Point getLocation() {
		return currentLocation;
	}

	/**
	 * Has the robot dropped its game piece this turn
	 * 
	 * @return true if the piece has been dropped
	 */
	public boolean getHasDroppedPiece() {
		return hasDroppedPiece;
	}

	/**
	 * Set the status of dropped piece.
	 * 
	 * @param hasDroppedPiece true if the piece has been dropped
	 */
	public void setHasDroppedPiece(boolean hasDroppedPiece) {
		this.hasDroppedPiece = hasDroppedPiece;
	}

	/**
	 * Sets a game piece to the current location of the robot.
	 * 
	 * @param color robot or player
	 */
	public void setPieceToCurrentLocation(int color) {
		gameGrid[currentLocation.x][currentLocation.y] = color;
	}

	/**
	 * Returns the next slot that was empty (before the latest player move) from the
	 * current position of the robot.
	 * 
	 * @return
	 */
	private Point checkNextEmptySlot() {
		Point nextEmptyPoint = null;
		for (int i = currentLocation.x + 1; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				if (gameGrid[i][j] == 0) {
					nextEmptyPoint = new Point(i, j);
					System.out.println("Next empty slot: " + nextEmptyPoint);
					return nextEmptyPoint;
				}
			}
		}
		return nextEmptyPoint;
	}

	/**
	 * Steps (x,y) from the current position of the robot to the next point that was
	 * empty before the last player move
	 * 
	 * @return
	 */
	public Point stepsToNextEmpty() {
		Point nextEmpty = checkNextEmptySlot();
		Point xySteps = new Point();

		xySteps.x = nextEmpty.x - currentLocation.x;
		xySteps.y = nextEmpty.y - currentLocation.y;
		System.out.println("Steps to next empty slot: " + xySteps);

		return xySteps;
	}

}
