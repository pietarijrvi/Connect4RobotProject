package model;

import util.Point;

public class Determinator {
	private Board game;			// Used to handle things with the game grid.
	private int[][] grid;		// Digital version of the game board.
	private int[] nextMoves;	// The next possible moves, used to to check which one is the best. Provided by the Board class
	private int bot;			// The number that indicates the bot's game pieces
	private int player;			// The number that indicates the player's game pieces
	private int botPegValue = 4;	// Value of the bot's color in the calculations
	private int playerPegValue = 3; // Value of the player's color in the calculations
	private int emptyPegValue = 0;  // Value of the empty spaces in the calculations
	private int power = 2;			// power^points 
	
	/**
	 * 
	 * @param board, a Board object that is used in the methods of this object.
	 * @param bot, the number that indicates the bot's game pieces.
	 * @param player, the number that indicates the bot's game pieces.
	 */
	public Determinator(Board board, int bot, int player) {
		this.game = board;
		this.bot = bot;
		this.player = player;
	}
	
	/**
	 * Checks if there are any rows of four on the board
	 * @return returns the number indicating the color of the row of four, returns 0 if there are no rows of four
	 */
	public int checkWins() {
		
		grid = game.getGrid();
		
		for (int x = 0; x < grid.length; x++) { //Goes through the entire width of the board
			
			for (int y = 0; y < grid[x].length; y++) { //Goes through the entire height of the board
				
				int pieceColor = grid[x][y]; //Save the value of the point that is used as the starting point for the calculations
				
				if (pieceColor != 0) { //If the point is empty, there's no chance of there being a row of 4 starting from that point
					
					//Directions indicated with cardinal directions
					int north = 1;
					int northeast = 1;
					int east = 1;
					int southeast = 1;
					
					for (int a = 1; a < 4; a++) { //Goes up to 3 pieces away from the starting point
						if (x <= grid.length - 4 && grid[x+a][y] == pieceColor) {
							east++;
						}
						if (y <= grid[x].length - 4 && grid[x][y+a] == pieceColor) {
							north++;
						}
						if (y <= grid[x].length - 4 && x <= grid.length - 4 && grid[x+a][y+a] == pieceColor) {
							northeast++;
						}
						if (y >= 3 && x <= grid.length - 4 && grid[x+a][y-a] == pieceColor) {
							southeast++;
						}
					}
					//If any reached 4 that means there's a four in a row and the game has ended
					if (north == 4 || east == 4 || northeast == 4 || southeast == 4) { 
						System.out.printf("win at (%d,%d)\n", x, y);
						System.out.printf("north: %d, northeast: %d, east: %d, southeast: %d\n", north, northeast, east, southeast);
						return pieceColor; //Color of the row of four
					}
				}
			}
		}
		return 0;
	}
	
	/**
	 * Calculates the next move based on the colors of the surrounding slots
	 * @return returns a point object that holds the move's X and Y values
	 */
	public Point getNextMove() {
		grid = game.getGrid();
		int bestMove = 0;
		
		nextMoves = game.getNextFreeSpaces();
		int[] movePoints = new int[7];
		
		
		for (int i = 0; i < nextMoves.length; i++) {
			int points = 0;
			if (nextMoves[i] != -1) {
				points += getHorizontalValue(i, nextMoves[i]);
				points += getVerticalValue(i, nextMoves[i]);
				points += getDiagonalRightValue(i, nextMoves[i]);
				points += getDiagonalLeftValue(i, nextMoves[i]);
				
				if (nextMoves[i] < grid[i].length - 1) {
					points -= getHorizontalValue(i, nextMoves[i] + 1)/3;
					points -= getVerticalValue(i, nextMoves[i] + 1)/3;
					points -= getDiagonalRightValue(i, nextMoves[i] + 1)/3;
					points -= getDiagonalLeftValue(i, nextMoves[i] + 1)/3;
				}
				points -= Math.abs(i-3)*2;

				movePoints[i] = points;
			}
			else movePoints[i] = -100000;
		}
		System.out.print(movePoints[0] + " ");
		for (int i = 1; i < movePoints.length; i++) {
			System.out.print(movePoints[i] + " ");
			if (movePoints[i] > movePoints[bestMove]) {
				bestMove = i;
			}
		}
		System.out.println("\n");
		Point nextMove = new Point(bestMove, nextMoves[bestMove]);
		
		return nextMove;
	}
	
	/**
	 * Calculates the point's horizontal value
	 * @param x the width coordinate of the board
	 * @param y the height coordinate of the board
	 * @return the calculated value
	 */
	private int getHorizontalValue(int x, int y) {
		
		int points = 0;
		boolean noEmpty = true;
		
		//Left side
		int read = x;
		if (read > 3) {
			read = 3;
		}
		
		int leftPoints = 0;
		int leftPeg = -1;
		int leftStreak = 0;
		for (int a = 1; a <= read; a++) {
			
			int spot = grid[x-a][y];
			
			if (spot != 0 && leftPeg == -1) {
				leftPeg = spot;
			}
			else if (spot == 0) {
				noEmpty = false;
			}
			
			if (spot == leftPeg) {
				if (spot == bot) {
					leftPoints += botPegValue;
					
				}
				else if (spot == player) {
					leftPoints += playerPegValue;
					
				}
				
				if (noEmpty) {
					leftStreak++;
				}
			}
			else if (spot == 0) {
				leftPoints += emptyPegValue;
			}
			
		}
		read = grid.length - 1 - x;
		if (read > 3) {
			read = 3;
		}
		
		//Right side
		int rightPoints = 0;
		int rightPeg = -1;
		int rightStreak = 0;
		for (int a = 1; a <= read; a++) {
			
			int spot = grid[x+a][y];
			
			if (spot != 0 && rightPeg == -1) {
				rightPeg = spot;
			}
			else if (spot == 0) {
				noEmpty = false;
			}
			
			if (spot == rightPeg) {
				if (spot == bot) {
					rightPoints += botPegValue;
					
				}
				else if (spot == player) {
					rightPoints += playerPegValue;
					
				}
				
				if (noEmpty) {
					rightStreak++;
					
				}
			}
			else if (spot == 0) {
				rightPoints += emptyPegValue;
					
			}
			
		}
		
		if (rightPeg == leftPeg && (noEmpty || rightStreak + leftStreak >= 3)) {
			points = (int)Math.pow(power, rightPoints + leftPoints);
		}
		else {
			points = (int)(Math.pow(power, leftPoints) + Math.pow(power, rightPoints));
		}
		return points;
	}
	
	/**
	 * Calculates the point's vertical value
	 * @param x the width coordinate of the board
	 * @param y the height coordinate of the board
	 * @return the calculated value
	 */
	private int getVerticalValue(int x, int y) {
		int points = 0;
		int firstPeg = -1;
		
		int read = y;
		if (read > 3) {
			read = 3;
		}
		
		for (int a = 1; a <= read; a++) {
			
			int spot = grid[x][y-a];
			
			if (spot != 0 && firstPeg == -1) {
				firstPeg = spot;
			}
			
			if (spot == firstPeg) {
				if (spot == bot) {
					points += botPegValue;
					
				}
				else if (spot == player) {
					points += playerPegValue;
					
				}
			}
		}
		
		points = (int)Math.pow(power, points);
		return points;
	}
	
	/**
	 * Calculates the point's diagonal value leaning to the right
	 * @param x the width coordinate of the board
	 * @param y the height coordinate of the board
	 * @return the calculated value
	 */
	private int getDiagonalRightValue(int x, int y) {
		
		int points = 0;
		boolean noEmpty = true;
		
		//Left side
		int read = y;
		if (x < y) {
			read = x;
		}
		if (read > 3) {
			read = 3;
		}
		
		int leftPoints = 0;
		int leftPeg = -1;
		int leftStreak = 0;
		for (int a = 1; a <= read; a++) {
			
			int spot = grid[x-a][y-a];
			
			if (spot != 0 && leftPeg == -1) {
				leftPeg = spot;
			}
			else if (spot == 0) {
				noEmpty = false;
			}
			
			if (spot == leftPeg) {
				if (spot == bot) {
					leftPoints += botPegValue;
					
				}
				else if (spot == player) {
					leftPoints += playerPegValue;
					
				}
				
				if (noEmpty) {
					leftStreak++;
				}
			}
			else if (spot == 0) {
				leftPoints += emptyPegValue;
			}
			
		}
		read = grid[x].length - 1 - y;
		if (grid.length - 1 - x < grid[x].length - 1 - y) {
			read = grid.length - 1 - x;
		}
		if (read > 3) {
			read = 3;
		}
		
		//Right side
		int rightPoints = 0;
		int rightPeg = -1;
		int rightStreak = 0;
		for (int a = 1; a <= read; a++) {
			
			int spot = grid[x+a][y+a];
			
			if (spot != 0 && rightPeg == -1) {
				rightPeg = spot;
			}
			else if (spot == 0) {
				noEmpty = false;
			}
			
			if (spot == rightPeg) {
				if (spot == bot) {
					rightPoints += botPegValue;
					
				}
				else if (spot == player) {
					rightPoints += playerPegValue;
					
				}
				
				if (noEmpty) {
					rightStreak++;
					
				}
			}
			else if (spot == 0) {
				rightPoints += emptyPegValue;
					
			}
			
		}
		
		if (rightPeg == leftPeg && (noEmpty || rightStreak + leftStreak >= 3)) {
			points = (int)Math.pow(power, rightPoints + leftPoints);
		}
		else {
			points = (int)(Math.pow(power, leftPoints) + Math.pow(power, rightPoints));
		}
		return points;
	}

	/**
	 * Calculates the point's diagonal value leaning to the left
	 * @param x the width coordinate of the board
	 * @param y the height coordinate of the board
	 * @return the calculated value
	 */
	private int getDiagonalLeftValue(int x, int y) {
		
		int points = 0;
		boolean noEmpty = true;
		
		//Left side
		int read = grid[x].length - 1 - y;
		if (x < read) {
			read = x;
		}
		if (read > 3) {
			read = 3;
		}
		
		int leftPoints = 0;
		int leftPeg = -1;
		int leftStreak = 0;
		for (int a = 1; a <= read; a++) {
			
			int spot = grid[x-a][y+a];
			
			if (spot != 0 && leftPeg == -1) {
				leftPeg = spot;
			}
			else if (spot == 0) {
				noEmpty = false;
			}
			
			if (spot == leftPeg) {
				if (spot == bot) {
					leftPoints += botPegValue;
					
				}
				else if (spot == player) {
					leftPoints += playerPegValue;
					
				}
				
				if (noEmpty) {
					leftStreak++;
				}
			}
			else if (spot == 0) {
				leftPoints += emptyPegValue;
			}
			
		}
		read = grid.length - 1 - x;
		if (y < read) {
			read = y;
		}
		if (read > 3) {
			read = 3;
		}
		
		//Right side
		int rightPoints = 0;
		int rightPeg = -1;
		int rightStreak = 0;
		for (int a = 1; a <= read; a++) {
			
			int spot = grid[x+a][y-a];
			
			if (spot != 0 && rightPeg == -1) {
				rightPeg = spot;
			}
			else if (spot == 0) {
				noEmpty = false;
			}
			
			if (spot == rightPeg) {
				if (spot == bot) {
					rightPoints += botPegValue;
					
				}
				else if (spot == player) {
					rightPoints += playerPegValue;
					
				}
				
				if (noEmpty) {
					rightStreak++;
				}
			}
			else if (spot == 0) {
				rightPoints += emptyPegValue;
					
			}
			
		}
		
		if (rightPeg == leftPeg && (rightStreak + leftStreak >= 3)) {
			points = (int)Math.pow(power, rightPoints + leftPoints);
		}
		else {
			points = (int)(Math.pow(power, leftPoints) + Math.pow(power, rightPoints));
		}
		return points;
	}
}
