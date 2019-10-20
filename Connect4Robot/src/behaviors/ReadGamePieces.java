package behaviors;

import connect4.GameLogic;
import connect4.MotorFunctions;
import connect4.PieceXYReadMove;
import lejos.robotics.subsumption.Behavior;
import sensors.ColorTester;
import util.Point;

/**
 * Behaviour that controls reading the game pieces on the board (searching for
 * the player piece). The robot reads the slots that were empty before the
 * latest player move. The search finishes when the robot finds the piece. The
 * robot uses a color sensor to update its position info (color changes) and
 * recognising the player piece.
 *
 */
public class ReadGamePieces implements Behavior {

	private volatile boolean suppressed = false;

	private MotorFunctions motorFunctions;
	private GameLogic gameLogic;
	private PieceXYReadMove pieceXYReadMove;

	public ReadGamePieces(PieceXYReadMove pieceXYReadMove, GameLogic gameLogic) {
		this.pieceXYReadMove = pieceXYReadMove;
		this.gameLogic = gameLogic;
	}

	/**
	 * Taking control when it's robot's turn, the robot is waiting in the start
	 * location and the robot hasn't read the game board yet.
	 */
	@Override
	public boolean takeControl() {
		// otetaan kontrolli, kun robotin vuoro ja laudan luku kesken (pelattu nappula
		// löytämättä)
		if (gameLogic.getIsRobotsTurn() && !gameLogic.getGameBoardReadComplete() && gameLogic.inStartPosition()) {
			return true;
		}
		return false;
	}

	/**
	 * Retrieves the game board information (empty slots) and current position from
	 * the game logic. Continues searching until the player piece has been found and
	 * saves the piece coordinates to the game logic game board.
	 */
	@Override
	public void action() {
		while (!suppressed) {

			boolean newPieceFound = false;

			while (!newPieceFound) {
				// retrieving the x- and y-movement steps to the next empty slot
				Point stepsToNextEmpty = gameLogic.stepsToNextEmpty();

				int destinationColor = pieceXYReadMove.moveSensor(stepsToNextEmpty);
				if (destinationColor == ColorTester.COLOR_PLAYERPIECE
						|| destinationColor == ColorTester.COLOR_ROBOTPIECE) {
					newPieceFound = true;
					gameLogic.setPieceToCurrentLocation(destinationColor);
					gameLogic.setGameBoardReadComplete(true);
					suppressed = true;
				}
			}
		}
	}

	/**
	 * Lifter motor (color sensor y-position) stops when the action is suppressed
	 */
	@Override
	public void suppress() {
		motorFunctions.stopLifter();
		suppressed = true;
	}

}
