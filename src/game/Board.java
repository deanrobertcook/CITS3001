package game;

import java.io.Serializable;
import java.util.concurrent.LinkedBlockingQueue;

public class Board implements Serializable {
	GameLoader loader;

	public int[][] board; // board refers to the 4x4 board used in the game
	private LinkedBlockingQueue<String> gameSequence;

	private boolean gameRunning;
	private boolean[] columnMoved;
	private boolean[] rowMoved;

	public Board(int[][] board, LinkedBlockingQueue<String> sequence) {

		this.board = board;
		this.gameSequence = sequence;

		columnMoved = new boolean[4];
		rowMoved = new boolean[4];

		this.gameRunning = true;
	}

	public boolean gameFinished() {
		return !this.gameRunning;
	}

	// Push an edge, returns true if a legal move was made, returns false if an
	// illegal move was made. AddTile must be called before next PushEdge call
	// to function properly.
	public boolean pushEdge(String direction) {

		boolean legalMove = false;

		setMovedFalse(columnMoved);
		setMovedFalse(rowMoved);

		if (peekNextSequence() == 0) {
			System.out.println("Out of Tiles!");
			return false;
		}

		switch (direction) {
		case "U":

			for (int j = 0; j < 4; j++) {

				for (int i = 0; i < 3; i++) {

					if ((board[i][j] == 0) && (board[i + 1][j] != 0)) {

						board[i][j] = board[i][j] + board[i + 1][j];
						board[i + 1][j] = 0;
						columnMoved[j] = true;

					}
					if ((board[i][j] == 1 && board[i + 1][j] == 2)
							|| (board[i][j] == 2 && board[i + 1][j] == 1)) {

						board[i][j] = board[i][j] + board[i + 1][j];
						board[i + 1][j] = 0;
						columnMoved[j] = true;

					}
					if ((board[i][j] == board[i + 1][j])
							&& ((board[i][j] != 0) && (board[i][j] != 1) && (board[i][j] != 2))) {

						board[i][j] = board[i][j] + board[i + 1][j];
						board[i + 1][j] = 0;
						columnMoved[j] = true;

					}

				}

			}

			break;
		case "D":

			for (int j = 0; j < 4; j++) {

				for (int i = 3; i > 0; i--) {

					if (((board[i][j] == 0) && (board[i - 1][j] != 0))) {

						board[i][j] = board[i][j] + board[i - 1][j];
						board[i - 1][j] = 0;
						columnMoved[j] = true;

					}
					if ((board[i][j] == 1 && board[i - 1][j] == 2)
							|| (board[i][j] == 2 && board[i - 1][j] == 1)) {

						board[i][j] = board[i][j] + board[i - 1][j];
						board[i - 1][j] = 0;
						columnMoved[j] = true;

					}
					if ((board[i][j] == board[i - 1][j])
							&& ((board[i][j] != 0) && (board[i][j] != 1) && (board[i][j] != 2))) {

						board[i][j] = board[i][j] + board[i - 1][j];
						board[i - 1][j] = 0;
						columnMoved[j] = true;

					}

				}

			}

			break;
		case "L":

			for (int i = 0; i < 4; i++) {

				for (int j = 0; j < 3; j++) {

					if ((board[i][j] == 0) && (board[i][j + 1] != 0)) {

						board[i][j] = board[i][j] + board[i][j + 1];
						board[i][j + 1] = 0;
						rowMoved[i] = true;

					}
					if ((board[i][j] == 1 && board[i][j + 1] == 2)
							|| (board[i][j] == 2 && board[i][j + 1] == 1)) {

						board[i][j] = board[i][j] + board[i][j + 1];
						board[i][j + 1] = 0;
						rowMoved[i] = true;

					}
					if ((board[i][j] == board[i][j + 1])
							&& ((board[i][j] != 0) && (board[i][j] != 1) && (board[i][j] != 2))) {

						board[i][j] = board[i][j] + board[i][j + 1];
						board[i][j + 1] = 0;
						rowMoved[i] = true;

					}

				}

			}

			break;
		case "R":

			for (int i = 0; i < 4; i++) {

				for (int j = 3; j > 0; j--) {

					if ((board[i][j] == 0) && (board[i][j - 1] != 0)) {

						board[i][j] = +board[i][j - 1];
						board[i][j - 1] = 0;
						rowMoved[i] = true;

					}
					if ((board[i][j] == 1 && board[i][j - 1] == 2)
							|| (board[i][j] == 2 && board[i][j - 1] == 1)) {

						board[i][j] = board[i][j] + board[i][j - 1];
						board[i][j - 1] = 0;
						rowMoved[i] = true;

					}
					if ((board[i][j] == board[i][j - 1])
							&& ((board[i][j] != 0) && (board[i][j] != 1) && (board[i][j] != 2))) {

						board[i][j] = board[i][j] + board[i][j - 1];
						board[i][j - 1] = 0;
						rowMoved[i] = true;

					}

				}

			}

			break;
		}

		for (boolean value : columnMoved) {

			if (value) {
				// System.out.println("Column moved true");
				legalMove = true;
			}

		}

		for (boolean value : rowMoved) {

			if (value) {
				// System.out.println("Row moved true");
				legalMove = true;
			}

		}

		return legalMove;
		// return(addTile(direction, board));

	}

	// Add a tile to the board. Returns true if a legal move was made, returns
	// false if an illegal move was made
	public boolean addTile(String direction, int[][] board, int nextTile) {

		boolean legalMoveMade = true;
		int lowValue = 10000000;
		int lowColumn = 0;
		boolean moved = false;

		switch (direction) {

		case "U":
			for (int i = 0; i < 4; i++) {

				if (this.columnMoved[i] == true) {

					moved = true;
					int presentValue = 0;

					for (int j = 0; j < 4; j++) {

						presentValue = presentValue + this.board[j][i];

					}
					if (presentValue < lowValue) {

						lowValue = presentValue;
						lowColumn = i;

					}
				}

			}

			if (moved) {
				this.board[3][lowColumn] = nextTile;
			}

			break;
		case "D":

			for (int i = 0; i < 4; i++) {

				if (this.columnMoved[i] == true) {

					moved = true;
					int presentValue = 0;

					for (int j = 0; j < 4; j++) {

						presentValue = presentValue + this.board[j][i];

					}

					if (presentValue <= lowValue) {

						lowValue = presentValue;
						lowColumn = i;

					}

				}

			}

			if (moved) {
				this.board[0][lowColumn] = nextTile;
			}

			break;
		case "L":

			for (int i = 0; i < 4; i++) {

				if (this.rowMoved[i] == true) {

					moved = true;
					// System.out.println("Examining row: " + i);
					int presentValue = 0;

					for (int j = 0; j < 4; j++) {

						presentValue = presentValue + this.board[i][j];

					}

					if (presentValue <= lowValue) {

						lowValue = presentValue;
						lowColumn = i;

					}
				}

			}

			// System.out.println("Lowest row is " + lowColumn + " at value " +
			// lowValue);

			if (moved) {
				this.board[lowColumn][3] = nextTile;
			}

			break;
		case "R":

			for (int i = 0; i < 4; i++) {

				if (this.rowMoved[i] == true) {

					moved = true;
					// System.out.println("Examining row: " + i);
					int presentValue = 0;

					for (int j = 0; j < 4; j++) {

						presentValue = presentValue + this.board[i][j];

					}

					if (presentValue < lowValue) {

						lowValue = presentValue;
						lowColumn = i;

					}
				}

			}

			// System.out.println("Lowest row is " + lowColumn + " at value " +
			// lowValue);

			if (moved) {
				this.board[lowColumn][0] = nextTile;
			}

			break;
		}

		if (lowValue == 10000000) {

			System.out.println("Illegal move attempted!");
			legalMoveMade = false;

		}

		return legalMoveMade;

	}

	private void setMovedFalse(boolean[] in) {

		for (int i = 0; i < 4; i++) {
			in[i] = false;
		}

	}

	public void play(String direction) {

		if (pushEdge(direction)) {

			addTile(direction, board, getNextSeqence());

		} else {
			gameRunning = false;
		}
	}

	public void printOutBoardState() {
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				System.out.print(this.board[i][j] + "\t");
			}
			System.out.println();
		}
	}

	public int[][] getBoardState() {
		return this.board;
	}

	public int getTileState(int column, int row) {
		return this.board[column][row];
	}

	public int getNextSeqence() {
		String nextSequence = this.gameSequence.poll();
		return (nextSequence != null) ? Integer.parseInt(nextSequence) : 0;
	}

	public int peekNextSequence() {
		String peek = this.gameSequence.peek();
		return (peek != null) ? Integer.parseInt(peek) : 0;
	}

	public int getValue(int[][] inBoard) {

		int value = 0;
		for (int i = 0; i < inBoard.length; i++) {
			for (int j = 0; j < inBoard.length; j++) {
				value = value + inBoard[i][j];
			}
		}
		return value;
	}
}
