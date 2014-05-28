package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class BoardOld implements Serializable {
	GameLoader loader;

	public int[][] board; // board refers to the 4x4 board used in the game
	private LinkedBlockingQueue<String> gameSequence;
	public String directionPushed;

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
		
		if (direction == null) {
			return false;
		}
		
		this.directionPushed = direction;
		boolean legalMove = false;

		setMovedFalse(columnMoved);
		setMovedFalse(rowMoved);

		if (peekNextSequence() == 0) {
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
				legalMove = true;
			}
		}
		for (boolean value : rowMoved) {
			if (value) {
				legalMove = true;
			}
		}
		return legalMove;
	}
	
	private boolean oneLessThanTwo(ArrayList<Integer> one, ArrayList<Integer> two) {
		for (int i = 0; i < one.size(); i++) {
			if (one.get(i).intValue() <= two.get(i).intValue()) {
				return true;
			} else if (one.get(i).intValue() > two.get(i).intValue()) {
				return false;
			}
		}
		return false;
	}
	
	private boolean arrayNotChanged(ArrayList<Integer> in) {		
		for (Integer integer : in) {
			if (integer.intValue() != 1000000) {
				return false;
			}
		}
		return true;
	}

	// Add a tile to the board. Returns true if a legal move was made, returns
	// false if an illegal move was made
	public boolean addTile() {
		int nextTile = this.getNextSeqence();
		boolean legalMoveMade = true;
		//int lowValue = 10000000;
		ArrayList<Integer> lowestTuple = new ArrayList<Integer>();
			lowestTuple.add(1000000);
			lowestTuple.add(1000000);
			lowestTuple.add(1000000);
			lowestTuple.add(1000000);
		int lowColumn = 0;
		boolean moved = false;

		switch (this.directionPushed) {
		case "U":
			for (int i = 0; i < 4; i++) {
				if (this.columnMoved[i] == true) {
					moved = true;
//					String presentValue = "";
					ArrayList<Integer> presentTuple = new ArrayList<Integer>();
					for (int j = 0; j < 4; j++) {
						//presentValue = this.board[j][i] + presentValue;
						presentTuple.add(0, this.board[j][i]);
					}
					//System.out.println(presentValue);
					//int presentInt = Integer.parseInt(presentValue);
//					System.out.println(presentTuple.toString());
//					if (presentInt < lowValue) {
//						lowValue = presentInt;
//						lowColumn = i;
//					}
//					System.out.println(presentTuple.toString());
					if (this.oneLessThanTwo(presentTuple, lowestTuple)) {
						lowestTuple = presentTuple;
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
//					String presentValue = "";
					ArrayList<Integer> presentTuple = new ArrayList<Integer>();
					for (int j = 0; j < 4; j++) {
						presentTuple.add(this.board[j][i]);
					}
					//System.out.println(presentValue);
//					System.out.println(presentTuple.toString());
//					int presentInt = Integer.parseInt(presentValue);
//					if (presentInt <= lowValue) {
//						lowValue = presentInt;
//						lowColumn = i;
//					}
					if (this.oneLessThanTwo(presentTuple, lowestTuple)) {
						lowestTuple = presentTuple;
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
//					String presentValue = "";
					ArrayList<Integer> presentTuple = new ArrayList<Integer>();
					for (int j = 0; j < 4; j++) {
						//presentTuple.add(this.board[i][j]);
						presentTuple.add(0, this.board[i][j]);
					}
//					System.out.println(presentValue);
//					int presentInt = Integer.parseInt(presentValue);
//					if (presentInt <= lowValue) {
//						lowValue = presentInt;
//						lowColumn = i;
//					}
//					System.out.println(presentTuple.toString());
					if (this.oneLessThanTwo(presentTuple, lowestTuple)) {
						lowestTuple = presentTuple;
						lowColumn = i;
					}
				}
			}
			if (moved) {
				this.board[lowColumn][3] = nextTile;
			}

			break;
		case "R":
			for (int i = 0; i < 4; i++) {
				if (this.rowMoved[i] == true) {
					moved = true;
//					String presentValue = "";
					ArrayList<Integer> presentTuple = new ArrayList<Integer>();
					for (int j = 0; j < 4; j++) {
						presentTuple.add(this.board[i][j]);
					}
					//System.out.println(presentValue);
//					int presentInt = Integer.parseInt(presentValue);
//					if (presentInt < lowValue) {
//						lowValue = presentInt;
//						lowColumn = i;
//					}
//					System.out.println(presentTuple.toString());
					if (this.oneLessThanTwo(presentTuple, lowestTuple)) {
						lowestTuple = presentTuple;
						lowColumn = i;
					}
				}
			}
			if (moved) {
				this.board[lowColumn][0] = nextTile;
			}
			break;
		}
//		if (lowValue == 10000000) {
//			System.out.println("Illegal move attempted!");
//			legalMoveMade = false;
//		}
		if (arrayNotChanged(lowestTuple)) {
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
			addTile();
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

	public int getValue() {

		int value = 0;
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board.length; j++) {

				int tileValue = this.board[i][j];
				if (tileValue == 1 || tileValue == 2) {
					value++;
				}
				if (tileValue > 2) {
					int tempVal;
					tempVal = (int)(Math.log(tileValue/3)/Math.log(2));
					tempVal = tempVal + 1;
					tempVal = (int)Math.pow(3, tempVal);
					value = value + tempVal;
				}
			}
		}
		return value;
	}
	
	public int getEmptyCells(){
		
		int number = 0;
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				if(board[i][j] == 0){
					number = number + 1;
				}
			}
		
		}
		return number;
	}

	public int calculateClusteringScore() {
        int clusteringScore=0;
        
        int[] neighbors = {-1,0,1};
        
        for(int i=0;i<board.length;++i) {
            for(int j=0;j<board.length;++j) {
                if(board[i][j]==0) {
                    continue; 
                }
                
                int numOfNeighbors=0;
                int sum=0;
                for(int k : neighbors) {
                    int x=i+k;
                    if(x<0 || x>=board.length) {
                        continue;
                    }
                    for(int l : neighbors) {
                        int y = j+l;
                        if(y<0 || y>=board.length) {
                            continue;
                        }
                        
                        if(board[x][y]>0) {
                            ++numOfNeighbors;
                            sum+=Math.abs(board[i][j]-board[x][y]);
                        }
                        
                    }
                }
                
                clusteringScore+=sum/numOfNeighbors;
            }
        }
        
        return clusteringScore;
    }
}
