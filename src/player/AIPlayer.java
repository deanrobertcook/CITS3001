package player;

import java.util.ArrayList;

import utility.ObjectCloner;
import game.Board;

public abstract class AIPlayer implements Player {

	protected ArrayList<String> moveList;
	protected Board currentBoard;
	
	public AIPlayer() {		
		moveList = new ArrayList<String>();
			moveList.add("U");
			moveList.add("R");
			moveList.add("L");
			moveList.add("D");
	}
	
	protected abstract int runAlgorithm(Board thoughtBoard);
	
	protected int evaluationFunction(Board in){			
		int value = in.getValue()*in.getEmptyCells() - in.calculateClusteringScore();
		return value;
	}
	
	protected ArrayList<Board> createChildren(Board in) {
		ArrayList<Board> children = new ArrayList<Board>();
		
		for (String direction : moveList) {
			Board newBoard = (Board) ObjectCloner.deepClone(in);
			if (newBoard.pushEdge(direction)) {
				children.add(newBoard);
			}
		}
		return children;
	}
	
	@Override
	public String askForMove() {
		Board thoughtBoard = (Board) ObjectCloner.deepClone(this.currentBoard);
		ArrayList<Board> firstChildren = createChildren(thoughtBoard);
		
		int bestScore = 0;
		String nextMove = null;
		
		for (Board firstChild : firstChildren) {
			int score = runAlgorithm(firstChild);
			if (score > bestScore) {
				bestScore = evaluationFunction(firstChild);
				nextMove = firstChild.directionPushed;
			}
		}
		return nextMove;
	}

	@Override
	public void presentBoard(Board board) {
		this.currentBoard = board;
	}

}
