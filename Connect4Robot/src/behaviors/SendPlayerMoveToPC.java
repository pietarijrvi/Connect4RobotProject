package behaviors;

import connect4.Communication;
import connect4.GameLogic;
import lejos.robotics.subsumption.Behavior;
import util.Point;

/**
 * Behaviour that communicates with the pc program; after reading a player game
 * piece (player's last move), send the piece location to the PC program.
 *
 */
public class SendPlayerMoveToPC implements Behavior {
	private Communication comm = new Communication();
	private GameLogic gameLogic;

	public SendPlayerMoveToPC(GameLogic gameLogic, Communication comm) {
		this.gameLogic = gameLogic;
		this.comm = comm;
	}

	/**
	 * Takes control when the robot has read the new piece (action just once)
	 */
	@Override
	public boolean takeControl() {
		if (gameLogic.getGameBoardReadComplete() && gameLogic.getPlayerMove() != null) {
			return true;
		}
		return false;
	}

	/**
	 * Action sends the player piece coordinates to the connected PC
	 */
	@Override
	public void action() {
		Point point = gameLogic.getPlayerMove();
		comm.sendDropPoint(point);
		// preparing to get the next drop point, value is used just once
		gameLogic.setDropPointReceived(false);
	}

	@Override
	public void suppress() {

	}

}
