package behaviors;

import lejos.hardware.Button;
import lejos.robotics.subsumption.Behavior;

/**
 * Behavior that has the highest priority; other behaviors get suppressed when
 * the emergency stop is activated.
 * 
 * @author jetro, pietari, kim, olli
 *
 */
public class EmergencyStop implements Behavior {

	public EmergencyStop() {
	}

	/**
	 * Behavior gets activated when the DOWN-button is pressed.
	 */
	@Override
	public boolean takeControl() {
		return Button.DOWN.isDown();
	}

	/**
	 * Emergency stop makes the robot stay still (other behaviors won't activate)
	 * until ENTER-button is pressed.
	 */
	@Override
	public void action() {
		System.out.print("EMERGENCY STOP");
		System.out.print("Press ENTER to continue");
		Button.ENTER.waitForPressAndRelease();
	}

	@Override
	public void suppress() {
	}

}
