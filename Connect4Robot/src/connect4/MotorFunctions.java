package connect4;

import lejos.hardware.device.DeviceIdentifier;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

/**
 * Class that controls the motor functions; moving, turning and stopping the
 * motors.
 * 
 * @author jetro, kim, pietari, olli
 *
 */
public class MotorFunctions {
	private BaseRegulatedMotor movementMotor;
	private BaseRegulatedMotor dispenserMotor;
	private BaseRegulatedMotor colorSensorLifterMotor;

	private Port motorPort; // left motor port

	public MotorFunctions(Port motorPort, Port dispenserMotorPort, Port colorSensorLifterMotorPort) {
		this.motorPort = motorPort;
		this.movementMotor = new EV3LargeRegulatedMotor(motorPort);
		this.dispenserMotor = new EV3MediumRegulatedMotor(dispenserMotorPort);
		this.colorSensorLifterMotor = new EV3MediumRegulatedMotor(colorSensorLifterMotorPort);
	}
	
	public void rotateDegreesLifterMotor(int degrees) {
		colorSensorLifterMotor.rotate(degrees, true);
	}

	public void rotateMovementMotor(int motorSpeed, boolean moveForwards) {
		rotateMotor(movementMotor, motorSpeed, moveForwards);
	}

	public void rotateDispenserMotor(int motorSpeed, boolean moveForwards) {
		rotateMotor(dispenserMotor, motorSpeed, moveForwards);

	}
	
	public void rotateLifterMotor(int motorSpeed, boolean moveForwards) {
		rotateMotor(colorSensorLifterMotor, motorSpeed, moveForwards);
	}
	
	private void rotateMotor(BaseRegulatedMotor motor,int motorSpeed, boolean moveForwards) {
		motor.setSpeed(motorSpeed);
		if (moveForwards) {
			motor.forward();
		} else {
			motor.backward();
		}
	}
	
	

	/**
	 * Stops motor
	 */
	public void stopMovement() {
		movementMotor.stop();
	}
	
	public void stopDispenser() {
		dispenserMotor.stop();
	}
	
	public void stopLifter() {
		colorSensorLifterMotor.stop();
	}

	/**
	 * Checks if DeviceIdentifier detects motor is connected to port.
	 * 
	 * @return true if motor port is connected
	 */
	public boolean testMotorPorts() {
		// Message that is returned if no motors are connected to ports
		String message = "NONE:NONE";
		// Closing motors so DeviceIdentifier can access the ports
		movementMotor.close();
		DeviceIdentifier motorID1 = new DeviceIdentifier(motorPort);
		boolean testOk = false;

		if (motorID1.getDeviceSignature(false).equals(message)) {
			System.out.print("not connected");
		} else {
			testOk = true;
		}
		motorID1.close();
		// Reopening motors for movement
		movementMotor = new EV3LargeRegulatedMotor(motorPort);
		return testOk;
	}
}
