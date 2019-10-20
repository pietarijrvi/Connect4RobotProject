package connect4;

import behaviors.ReturnToStart;
import sensors.ColorTester;
import util.Point;

public class GameLogic {
	int columns = 7;
	int rows = 6;

	private int[][] gameGrid = new int[columns][rows];

	// aloituspiste, y-yläasennossa, jotta mahtuu liikkumaan aloituspisteeseen
	// (moottorin oltava ylä-asennossa)
	private Point currentLocation = new Point(0, 5);

	private boolean isRobotsTurn = true; //
	private boolean gameBoardReadComplete = false;

	private boolean dropPointReceived = false;

	private boolean hasDroppedPiece = true;
	private Point calculatedDropPoint = null;

	private Point playerMove = null;

	public boolean getDropPointReceived() {
		return dropPointReceived;
	}

	public void setDropPointReceived(boolean dropPointReceived) {
		if (dropPointReceived)
			System.out.println("dropPointReceived");
		else
			System.out.println("dropPointCleared");
		this.dropPointReceived = dropPointReceived;
	}

	public Point getPlayerMove() {
		Point p = playerMove;
		playerMove = null;
		return p;
	}

	public boolean getIsRobotsTurn() {
		return isRobotsTurn;
	}

	public void setIsRobotsTurn(boolean b) {
		if (b)
			System.out.println("Robot turn!");
		else
			System.out.println("Player turn!");
		isRobotsTurn = b;
	}

	public boolean inStartPosition() {
		if (currentLocation.x < 0)
			return true;
		return false;
	}

	public void setCalculatedMove(Point point) {
		calculatedDropPoint = point;
		// tallennetaan taulukkoon tietokoneelta saatu uusi siirto
		if (point != null) {
			gameGrid[point.x][point.y] = ColorTester.COLOR_ROBOTPIECE;
		}
	}

	public Point getCalculatedDropPoint() {
		return calculatedDropPoint;
	}

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

	public boolean getGameBoardReadComplete() {
		return gameBoardReadComplete;
	}

	public void setGameBoardReadComplete(boolean completed) {
		gameBoardReadComplete = completed;
	}

	public GameLogic() {
		gameGrid = new int[columns][rows];
	}

	public void setLocation(Point point) {
		currentLocation = point;
	}

	public void locationChange(Point point) {
		currentLocation.x += point.x;
		currentLocation.y += point.y;
	}

	public Point getLocation() {
		return currentLocation;
	}

	public void setPieceToCurrentLocation(int color) {
		gameGrid[currentLocation.x][currentLocation.y] = color;
	}

	// etsii seuraavan tyhj�n slotin nykyisest� sijainnista eteenp�in.
	// l�hdett�v�
	// t�ss� vaiheessa nollasta, eli nollaus ennen t�t�. palautetaan
	// ensimmäinen löydetty tyhjä
	private Point checkNextEmptySlot() {

		for (int i = currentLocation.x + 1; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				if (gameGrid[i][j] == 0) {
					Point nextEmptyPoint = new Point(i, j);
					System.out.println("Next empty slot: " + nextEmptyPoint);
					return nextEmptyPoint;
				}
			}
		}
		System.out.println("No piece ggwp");
		return null;
	}

	// "askeleet" seuraavaan tarkistettavaan pisteeseen anturin nykyisest�
	// sijainnista
	public Point stepsToNextEmpty() {
		Point xySteps = new Point();
		Point nextEmpty = null;
		try {
			nextEmpty = checkNextEmptySlot();

			xySteps.x = nextEmpty.x - currentLocation.x;
			xySteps.y = nextEmpty.y - currentLocation.y;
			System.out.println("Steps to next empty slot: " + xySteps);
		} catch (Exception e) {
			System.out.println("CurrentLocation: " + currentLocation.x + " " + currentLocation.y);
			System.out.println("nextEmpty: " + nextEmpty.x + " " + nextEmpty.y);
		}
		return xySteps;
	}

	public boolean getHasDroppedPiece() {
		return hasDroppedPiece;
	}

	public void setHasDroppedPiece(boolean hasDroppedPiece) {
		this.hasDroppedPiece = hasDroppedPiece;
	}

}
