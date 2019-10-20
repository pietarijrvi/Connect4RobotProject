package behaviors;

import connect4.Communication;
import connect4.GameLogic;
import lejos.robotics.subsumption.Behavior;

/**
 * For receiving change of turn from PC
 * @author Pietari Järvi, Kim Widberg, Olli Kaivola, Jetro Saarti
 */
public class ReceiveTurnChange implements Behavior {

	private GameLogic gameLogic;
	private Communication comm;
	
	public ReceiveTurnChange(GameLogic gameLogic, Communication comm) {
		this.gameLogic = gameLogic;
		this.comm = comm;
	}
	
	/**
	 * Taking control when robots turn and robot is in start position
	 */
	@Override
	public boolean takeControl() {
		if(!gameLogic.getIsRobotsTurn() && gameLogic.inStartPosition()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Receiving turn change and setting to robots turn
	 */
	@Override
	public void action() {
		System.out.println("ReceiveTurnChange started");
		comm.receiveTurnChange();
		System.out.println("Turn change received");
		gameLogic.setIsRobotsTurn(true);
	}

	@Override
	public void suppress() {
		
	}

}
