package behaviors;

import connect4.Communication;
import connect4.GameLogic;
import connect4.MotorFunctions;
import lejos.robotics.subsumption.Behavior;
import sensors.TouchSensor;
import util.Point;

/**
 * Robot returns to the start location next to the game board. Robot moves until
 * the touch sensor (signalling the correct location) is reached.
 *
 */
public class ReturnToStart implements Behavior {

	private volatile boolean suppressed = false;
	private MotorFunctions motorFunctions;
	private TouchSensor startPositionButton;
	private GameLogic gameLogic;
	private Communication comm;

	public ReturnToStart(MotorFunctions motorFunctions, TouchSensor startPositionButton, GameLogic gameLogic,
			Communication comm) {
		this.motorFunctions = motorFunctions;
		this.startPositionButton = startPositionButton;
		this.gameLogic = gameLogic;
		this.comm = comm;
	}

	/**
	 * Taking control when the robot has dropped its game piece and finishing its
	 * turn.
	 */
	@Override
	public boolean takeControl() {
		if (gameLogic.getHasDroppedPiece())
			return true;
		return false;
	}

	/**
	 * The robot moves sideways until the touch sensor is reached. Turn changes from
	 * the robot to the player when the sensor is reached.
	 */
	@Override
	public void action() {

		suppressed = false;
		int movementSpeed = 200;
		while (!suppressed) {
			motorFunctions.rotateMovementMotor(movementSpeed, false);
			// monitoring the button (touch sensor) press
			while (!startPositionButton.isButtonPressed())
				;
			motorFunctions.stopMovement();
			System.out.println("Liikuttu aloituspisteeseen -1");

			// updating the robot location
			gameLogic.setLocation(new Point(-1, gameLogic.getLocation().y));

			gameLogic.getHasDroppedPiece();

			// communicating with the connected PC that the robot has finished its turn
			comm.sendTurnChange();
			gameLogic.setIsRobotsTurn(false);

			suppressed = true;
		}
	}

	@Override
	public void suppress() {
		suppressed = true;

	}

}
