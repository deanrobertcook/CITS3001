package player;

import game.Board;
import java.util.ArrayList;
import utility.ObjectCloner;

public class AIPlayer implements Player {

	private ArrayList<String> moveList;
	private Board board;

	public AIPlayer(Board inBoard) {

		board = inBoard;
		moveList = new ArrayList<String>();
		moveList.add("U");
		moveList.add("R");
		moveList.add("L");
		moveList.add("D");

	}

	
	//Need to test cloning for gameSequence 
	public ArrayList<Board> creatChildren(Board in) {

		ArrayList<Board> children = new ArrayList<Board>();

		for (String direction : moveList) {

			Board newBoard = (Board) ObjectCloner.deepClone(in);

			if (newBoard.pushEdge(direction)) {

				newBoard.printOutBoardState();
				System.out.println();
				children.add(newBoard);

			}

		}

		return children;

	}

	@Override
	public String askForMove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void presentBoard(int[][] boardState) {

		for (int i = 0; i < boardState.length; i++) {
			for (int j = 0; j < boardState[i].length; j++) {
				System.out.print(boardState[i][j] + "\t");
			}
			System.out.println();
		}

	}

}