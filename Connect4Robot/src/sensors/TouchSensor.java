package sensors;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.NXTTouchSensor;

public class TouchSensor {
	private NXTTouchSensor touchSensor;
	
	public TouchSensor(Port touchSensorPort) {
		touchSensor = new NXTTouchSensor(touchSensorPort);
	}

	public boolean isButtonPressed() {
		float[] touchSample = {0};
		touchSensor.getTouchMode().fetchSample(touchSample, 0);
		
		if (touchSample[0] != 0) {
			return true;
		}
		return false;
	}
	
}
