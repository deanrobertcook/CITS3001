package game;

import java.util.concurrent.LinkedBlockingQueue;

public class GameLegacy {
	
	public int[][] board; // board refers to the 4x4 board used in the game
	private LinkedBlockingQueue<String> gameSequence;
	
	boolean shiftLeftPossible = false;
	boolean shiftRightPossible = false;
	boolean shiftUpPossible = false;
	boolean shiftDownPossible = false;
	
	private void shiftCheckColumn() { // legacy function, may be necessary for
		// AI

		for (int i = 0; i < 4; i++) {

			for (int j = 1; j < 4; j++) {

				if ((this.board[j - 1][i] == 0) && (this.board[j][i] != 0)) {

					System.out.println("COLUMNSEARCH | Found 0 at: " + (j - 1)
							+ ", " + i);
					this.shiftUpPossible = true;

				} else if (this.board[j - 1][i] == this.board[j][i]
						&& (this.board[j - 1][i] != 1
								&& this.board[j - 1][i] != 2 && this.board[j - 1][i] != 0)) {

					System.out.println("COLUMNSEARCH | Found double number at "
							+ (j - 1) + ", " + i + " and " + j + ", " + i);
					this.shiftUpPossible = true;
					this.shiftDownPossible = true;

				} else if ((this.board[j - 1][i] == 1 && this.board[j][i] == 2)
						|| (this.board[j - 1][i] == 2 && this.board[j][i] == 1)) {

					System.out
							.println("COLUMNSEARCH | Found 1 or 2 adjacent to another at "
									+ (j - 1) + ", " + i);
					this.shiftUpPossible = true;
					this.shiftDownPossible = true;

				} else if (this.board[j - 1][i] != 0 && this.board[j][i] == 0) {

					System.out.println("COLUMNSEARCH | Found 0 at " + j + ", "
							+ i);
					this.shiftDownPossible = true;

				}

			}

		}

	}
	
	private void shiftCheckRow() { // legacy function, may be necessary for AI

		for (int i = 0; i < 4; i++) {

			for (int j = 1; j < 4; j++) {

				if ((this.board[i][j - 1] == 0) && (this.board[i][j] != 0)) {

					System.out.println("ROWSEARCH | Found 0 at: " + i + ", "
							+ (j - 1));
					this.shiftLeftPossible = true;

				} else if (this.board[i][j - 1] == this.board[i][j]
						&& (this.board[i][j - 1] != 1
								&& this.board[i][j - 1] != 2 && this.board[i][j - 1] != 0)) {

					System.out.println("ROWSEARCH | Found double number at "
							+ (i) + ", " + (j - 1) + " and " + i + ", " + j);
					this.shiftLeftPossible = true;
					this.shiftRightPossible = true;

				} else if ((this.board[i][j - 1] == 1 && this.board[i][j] == 2)
						|| (this.board[i][j - 1] == 2 && this.board[i][j] == 1)) {

					System.out
							.println("ROWSEARCH | Found 1 or 2 adjacent to another at "
									+ (i) + ", " + (j - 1));
					this.shiftLeftPossible = true;
					this.shiftRightPossible = true;

				} else if (this.board[i][j - 1] != 0 && this.board[i][j] == 0) {

					System.out
							.println("ROWSEARCH | Found 0 at " + i + ", " + j);
					this.shiftRightPossible = true;

				}

			}

		}

	}
}
