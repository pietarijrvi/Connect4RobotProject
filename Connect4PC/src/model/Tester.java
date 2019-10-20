package model;

import java.util.Scanner;

import util.Point;

/**
 * @author Kim Widberg, Jetro Saarti, Pietari Järvi, Olli Kaivola
 */
public class Tester {

	static Board board = new Board();
	static int bot = 1;
	static int player = 2;
	static Determinator det = new Determinator(board, bot, player);
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int playersMove;
		do {
			printBoard(board);
			System.out.println("Your move:");
			playersMove = scanner.nextInt();
		} while(playSequence(board, new Point(playersMove, 0)));
		
		printBoard(board);
		scanner.close();
		
	}
	
	public static boolean playSequence(Board board, Point playersMove) {
		board.setPiece(playersMove.x, player);
		Point nextMove = det.getNextMove();
		int winState = det.checkWins();
		
		switch(winState) {
		case 0:
			if(board.isFull()) {
				System.out.println("Draw!");
				return false;
			}
			board.setPiece(nextMove.x, bot);
			if (det.checkWins() == 1) {
				System.out.println("Bot wins!");
				return false;
			}
			return true;
		case 1:
			System.out.println("Bot wins!");
			return false;
		case 2:
			System.out.println("Player wins!");
			return false;
		}
		System.out.println("This should not be reached");
		return true;
		
		
	}
	
	/**
	 * Checks for wins on the board
	 */
	public void testCheckWins() {
		Board game = new Board();
		Determinator det = new Determinator(game, 1, 2);
		
		for (int i = 0; i < 23; i++) {
			while(!game.setPiece((int)(Math.random()*7), i%2+1) && !game.isFull());
		}
		
		printBoard(board);
		
		System.out.println(det.checkWins());
	}
	
	/**
	 * Prints the current board into console
	 */
	public static void printBoard(Board board) {
		int[][] grid = board.getGrid();
		for (int y = 5; y >= 0; y--) {
			for (int x = 0; x < 7; x++) {
				System.out.print(grid[x][y] + " ");
			}
			System.out.println();
		}
	}
}
