package connect4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import lejos.hardware.Sound;
import util.Point;

/**
 * Class contains methods for controlling the data streams (sending and
 * receiving data) between the robot and PC (robot side)
 * 
 * @author Pietari J�rvi, Olli Kaivola, Jetro Saarti, Kim Widberg
 *
 */
public class Communication {

	private ServerSocket serveri;
	private Socket s;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	private ObjectOutputStream oos = null;
	private ObjectInputStream oin = null;

	/**
	 * Opens server socket connection and data streams and waits for the PC to
	 * connect
	 */
	public void openConnection() {
		try {
			serveri = new ServerSocket(1111);
			System.out.println("waiting for connection");
			Sound.beep();
			s = serveri.accept();
			in = new DataInputStream(s.getInputStream());
			out = new DataOutputStream(s.getOutputStream());
			oos = new ObjectOutputStream(out);
			oin = new ObjectInputStream(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("CONNECTED");
	}

	/**
	 * Waits for the start command from PC
	 */
	public void waitForGoCommand() {
		try {
			in.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives point coordinates sent from the PC
	 * 
	 * @return
	 */
	public Point receiveDropPoint() {
		Point point = new Point(0, 0);
		try {
			point = (Point) oin.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return point;
	}

	/**
	 * Sends game piece point (player move) to the PC
	 * 
	 * @param point x- and y-coordinates
	 */
	public void sendDropPoint(Point point) {
		try {
			oos.writeObject(point);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Signalling the PC that the the turn has changed
	 */
	public void sendTurnChange() {
		try {
			out.writeBoolean(true);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Listening for the turn change signal
	 * @return
	 */
	public boolean receiveTurnChange() {
		boolean turnChange = false;
		try {
			turnChange = in.readBoolean();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return turnChange;
	}
}
