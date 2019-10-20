package connect4;

import lejos.utility.Delay;
import sensors.ColorTester;
import util.Point;

/**
 * PieceXYReadMove controls the movement logic and color sensor usage
 * (recognising colors and color changes). Used by other classes.
 *
 */
public class PieceXYReadMove {

	private MotorFunctions motorFunctions;
	private GameLogic gameLogic;
	private ColorTester colorTester;

	public PieceXYReadMove(MotorFunctions motorFunctions, GameLogic gameLogic, ColorTester colorTester) {
		this.motorFunctions = motorFunctions;
		this.gameLogic = gameLogic;
		this.colorTester = colorTester;
	}

	/**
	 * Moves the sensor (x- and y-position) for the specified amount of x- and y-steps (one
	 * step means going to the next game piece). 
	 * 
	 * @param steps includes the x- and y steps as coordinates
	 * @return last recognised color
	 */
	public int moveSensor(Point steps) {
		int sensorMotorSpeed = 20;
		int movementSpeed = 50;

		// deciding the correct x-movement direction
		boolean xDirectionForward = true;
		if (steps.x > 0) {
			xDirectionForward = true;
		} else {
			xDirectionForward = false;
		}
		
		// x-movement (wheels)
		motorFunctions.rotateMovementMotor(movementSpeed, xDirectionForward);

		// previously recognised color the is compared the new sensor readings
		int lastColor = colorTester.testColor(); 
		
		for (int i = 0; i < Math.abs(steps.x); i++) {
			// iteration includes color change to the game board and then recognising some other color (actual piece color)
			boolean foundNewSlot = false;
			while (!foundNewSlot) {
				int color = colorTester.testColor();
				if (color != lastColor) {
					switch (color) {
					case ColorTester.COLOR_BOARD:
						lastColor = color;
						break;
					// any color other than the board color counts as a step
					case ColorTester.COLOR_PLAYERPIECE:
					case ColorTester.COLOR_ROBOTPIECE:
					case ColorTester.COLOR_EMPTY:
						// avoiding some wrong readings
						if (lastColor != ColorTester.COLOR_BOARD) {
							break;
						}
						lastColor = color;
						System.out.println("Recognised color: " + color);

						// small delay to move the sensor to the middle of the piece
						Delay.msDelay(700);
						foundNewSlot = true;
						break;
					default:
						break;
					}
				}
			}
		}
		motorFunctions.stopMovement();

		// deciding the correct y-direction
		boolean yDirectionUp = true;
		if (steps.y > 0) {
			yDirectionUp = true;
		} else {
			yDirectionUp = false;
		}
		motorFunctions.rotateLifterMotor(sensorMotorSpeed, yDirectionUp);

		for (int i = 0; i < Math.abs(steps.y); i++) {
			boolean foundNewSlot = false;
			while (!foundNewSlot) {
				int color = colorTester.testColor();
				if (color != lastColor) {
					switch (color) {
					case ColorTester.COLOR_BOARD:
						lastColor = color;
						System.out.println("tunnistettu pelilauta");
						break;
					case ColorTester.COLOR_PLAYERPIECE:
					case ColorTester.COLOR_ROBOTPIECE:
					case ColorTester.COLOR_EMPTY:
						// avoiding faulty readings
						if (lastColor != ColorTester.COLOR_BOARD) {
							break;
						}
						lastColor = color;
						System.out.println("Tunnistettu vari: " + color);

						foundNewSlot = true;
						// small delay to move the sensor to the middle of the piece
						Delay.msDelay(400);
						break;
					default:
						break;
					}
				}
			}
		}
		motorFunctions.stopLifter();
		
		//updating the moved steps to the location
		gameLogic.locationChange(steps);

		//returns the last recognised color
		return lastColor;
	}
}
