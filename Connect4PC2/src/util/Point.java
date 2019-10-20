package util;

import java.io.Serializable;

/**
 * Coordinates for gamepieces
 */
public class Point implements Serializable {
	public int x;
	public int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point() {
		this.x = 0;
		this.y = 0;
	}
	
	@Override
	public String toString() {
		return "X: " + x + " Y: " + y;
	}
}
