package model;
/**
 * @author Pietari Järvi, Kim Widberg, Olli Kaivola, Jetro Saarti
 */
public class Board {
	private int[][] grid;
	
	/**
	 * Constructor, creates a new board
	 */
	public Board() {
		grid = new int[7][6];
	}
	
	/**
	 * Returns free spaces on the board
	 * @return indexes - indexes of free spaces on board
	 */
	public int[] getNextFreeSpaces() {
		int[] indexes = new int[7];
		for (int x = 0; x < grid.length; x++) {
			indexes[x] = -1;
			for (int y = 0; y < grid[x].length; y++) {
				if (grid[x][y] == 0) {
					indexes[x] = y;
					break;
				}
			}
		}
		return indexes;
	}
	
	/**
	 * @return grid
	 */
	public int[][] getGrid() {
		return grid;
	}
	
	/**
	 * Sets gamepiece to the board
	 */
	public boolean setPiece(int x, int player) {
		for (int y = 0; y < grid[x].length; y++) {
			if (grid[x][y] == 0) {
				grid[x][y] = player;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return true if board is full
	 */
	public boolean isFull() {
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				if (grid[x][y] == 0) {
					return false;
				}
			}
		}
		
		return true;
	}
}
