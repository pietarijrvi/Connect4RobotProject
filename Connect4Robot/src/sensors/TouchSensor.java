package sensors;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.NXTTouchSensor;

/**
 * Class used to retrieve touch sensor info
 *
 */
public class TouchSensor {
	private NXTTouchSensor touchSensor;
	
	public TouchSensor(Port touchSensorPort) {
		touchSensor = new NXTTouchSensor(touchSensorPort);
	}

	/**
	 * Is the button pressed down at the moment
	 * @return true if the button is pressed
	 */
	public boolean isButtonPressed() {
		float[] touchSample = {0};
		touchSensor.getTouchMode().fetchSample(touchSample, 0);
		
		if (touchSample[0] != 0) {
			return true;
		}
		return false;
	}
	
}
