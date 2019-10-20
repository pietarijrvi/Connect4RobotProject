package behaviors;

import connect4.Communication;
import connect4.GameLogic;
import lejos.robotics.subsumption.Behavior;
import util.Point;

public class ReceiveRobotMove implements Behavior {

	private Communication comm = new Communication();
	private GameLogic gameLogic;

	public ReceiveRobotMove(GameLogic gameLogic, Communication comm) {
		this.gameLogic = gameLogic;
		this.comm = comm;
	}

	@Override
	public boolean takeControl() {
		if (gameLogic.getIsRobotsTurn() && !gameLogic.getDropPointReceived() && gameLogic.getGameBoardReadComplete()) {
			return true;
		}
		return false;
	}

	@Override
	public void action() {
		System.out.println("ReceiveRobotMove started");
		Point p = comm.receiveDropPoint();
		System.out.println("Move received - x:" + p.x + " y: "+p.y);
		gameLogic.setCalculatedMove(p);
		gameLogic.setDropPointReceived(true);
		gameLogic.setGameBoardReadComplete(false);
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

}
