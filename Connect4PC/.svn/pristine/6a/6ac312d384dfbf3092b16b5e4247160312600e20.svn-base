package model;

public class Board {
	private int[][] grid;
	
	public Board() {
		grid = new int[7][6];
	}
	
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

	public int[][] getGrid() {
		return grid;
	}
	
	public boolean setPiece(int x, int player) {
		for (int y = 0; y < grid[x].length; y++) {
			if (grid[x][y] == 0) {
				grid[x][y] = player;
				return true;
			}
		}
		return false;
	}
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
