package behaviors;

import connect4.Communication;
import connect4.GameLogic;
import lejos.robotics.subsumption.Behavior;
import util.Point;

/**
 * Behaviour that controls receiving messages from the connected PC. Robot
 * starts listening for the calculated next move when it's robot's turn and
 * saves the received coordinates.
 *
 */
public class ReceiveRobotMove implements Behavior {

	private Communication comm = new Communication();
	private GameLogic gameLogic;

	public ReceiveRobotMove(GameLogic gameLogic, Communication comm) {
		this.gameLogic = gameLogic;
		this.comm = comm;
	}

	/**
	 * Taking control when it's robot's turn and it hasn't received a drop point
	 * yet. Top priority behaviour.
	 */
	@Override
	public boolean takeControl() {
		if (gameLogic.getIsRobotsTurn() && !gameLogic.getDropPointReceived()) {
			return true;
		}
		return false;
	}

	/**
	 * Listening for the calculated point and saving it. Action is performed just once
	 * each turn.
	 */
	@Override
	public void action() {
		Point p = comm.receiveDropPoint();
		gameLogic.setCalculatedMove(p);
		gameLogic.setDropPointReceived(true);
	}

	@Override
	public void suppress() {

	}

}
