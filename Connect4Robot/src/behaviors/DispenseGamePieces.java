package behaviors;

import connect4.GameLogic;
import connect4.MotorFunctions;
import connect4.PieceXYReadMove;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import sensors.ColorTester;
import sensors.TouchSensor;
import util.Point;

public class DispenseGamePieces implements Behavior {
	private volatile boolean suppressed = false;
	private PieceXYReadMove pieceXYReadMove;
	private MotorFunctions motorFunctions; // movement reference (motor functions)
	private TouchSensor feederEndButton;
	private GameLogic gameLogic;
	private ColorTester colorTester;

	public DispenseGamePieces(PieceXYReadMove pieceXYReadMove, MotorFunctions motorFunctions,
			TouchSensor feederEndButton, GameLogic gameLogic, ColorTester colorTester) {
		this.pieceXYReadMove = pieceXYReadMove;
		this.motorFunctions = motorFunctions;
		this.feederEndButton = feederEndButton;
		this.gameLogic = gameLogic;
		this.colorTester = colorTester;
	}

	@Override
	public boolean takeControl() {
		if (gameLogic.getIsRobotsTurn() && gameLogic.getCalculatedDropPoint() != null)
			return true;
		return false;
	}

	@Override
	public void action() {

		// TODO: siirtyy oikeaan kohtaan (pudotuspaikan Point(x,y) saadaan
		// tietokoneelta, liikutaan kohdalle ja nostetaan
		// anturi tarkkailemaan pudotusta. Liikutetaan dispencerMotoria, kunnes
		// huomataan värin vaihtuneen, vuoro päättyy (suppress)
		// HUOM: jos osuu kosketusanturiin ääripäässä, ilmoitetaan lataustarve ja
		// jatketaan pelaajan kuittauksen jälkeen
		// default behavior: ReturnToStart
		
		System.out.println("DispenseGamePieces started");

		Point target = gameLogic.getPathToCalculatedPoint();
		pieceXYReadMove.moveSensor(target);
		motorFunctions.rotateDispenserMotor(15, false);
		// TODO: maksimin tarkistus(touchSensor), thread
		while (colorTester.testColor() == ColorTester.COLOR_EMPTY){
			if (feederEndButton.isButtonPressed()) {
				motorFunctions.stopDispenser();
				motorFunctions.rotateDispenserMotor(50, true);
				Delay.msDelay(4000);
				motorFunctions.stopDispenser();
				while(!feederEndButton.isButtonPressed());
				Delay.msDelay(500);
				motorFunctions.rotateDispenserMotor(15, false);
			}
		}
		System.out.println("Pudotus tunnistettu!");
		motorFunctions.stopDispenser();

		// Liikutetaan anturia ylöspäin, jotta robotti mahtuu liikkumaan pelilaudan ohi
		if (gameLogic.getLocation().y < 2) {
			pieceXYReadMove.moveSensor(new Point(0, 2));
		}

		gameLogic.setCalculatedMove(null);
		gameLogic.setHasDroppedPiece(true);
	}

	@Override
	public void suppress() {
		suppressed = true;

	}

}
