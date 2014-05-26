package player;

import game.Board;
import java.util.ArrayList;
import utility.ObjectCloner;

public class AIPlayer implements Player {

	private static final int INFINITY = 1000000;
	private static final int SEARCH_DEPTH = 6;
	
	private ArrayList<String> moveList;
	
	private Board currentBoard;
	private int currentScore;
	
	public AIPlayer() {
		moveList = new ArrayList<String>();
			moveList.add("U");
			moveList.add("R");
			moveList.add("L");
			moveList.add("D");
	}

	private int alphaBeta(Board thoughtBoard, int depth, int alpha, int beta, boolean maximizingPlayer){
		int bestScore = 0;		
		ArrayList<Board> children = createChildren(thoughtBoard);
		
		if((children.isEmpty()) || (depth == 0)){
			bestScore = evaluationFunction(thoughtBoard);
			return bestScore;
		}
		
		if(maximizingPlayer == true){
			for(Board child : children){
				alpha = Math.max(alpha, alphaBeta(child, depth - 1, alpha, beta, false));
				if(beta <= alpha){
					break;
				}
			}
			
			return alpha;
		}else{
			thoughtBoard.addTile(thoughtBoard.getNextSeqence());			
			beta = Math.min(beta, alphaBeta(thoughtBoard, depth - 1, alpha, beta, true));				
			
			return beta;			
		}

	}
	
	private ArrayList<Board> createChildren(Board in) {
		ArrayList<Board> children = new ArrayList<Board>();
		
		for (String direction : moveList) {
			Board newBoard = (Board) ObjectCloner.deepClone(in);
			if (newBoard.pushEdge(direction)) {
				children.add(newBoard);
			}
		}
		return children;
	}

	private int evaluationFunction(Board in){		
		return in.getValue();
	}
	
	@Override
	public String askForMove() {
		Board thoughtBoard = (Board) ObjectCloner.deepClone(this.currentBoard);
		ArrayList<Board> firstChildren = createChildren(thoughtBoard);
		
		int bestScore = 0;
		String nextMove = null;
		
		for (Board firstChild : firstChildren) {
			int score = alphaBeta(firstChild, SEARCH_DEPTH, INFINITY, -INFINITY, true);
			if (score > bestScore) {
				bestScore = firstChild.getValue();
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