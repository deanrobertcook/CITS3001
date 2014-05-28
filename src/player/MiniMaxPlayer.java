package player;

import game.Board;
import java.util.ArrayList;
import utility.ObjectCloner;

public class MiniMaxPlayer extends AIPlayer {
	private static final int INFINITY = 1000000;
	private static final int SEARCH_DEPTH = 4;
	
	private int miniMaxSearch(Board board, int depth, boolean humanPlayer){
		if(depth == 0){
			return evaluationFunction(board);
		}
		if(humanPlayer == true){
			ArrayList<Board> children = createChildren(board);
			if (children.isEmpty()) {
				return evaluationFunction(board);
			}
			int bestValue = -INFINITY;
			for(Board child : children){
				int value = miniMaxSearch(child, depth - 1, false);
				if(value > bestValue){
					bestValue = value;
				}
			}
			return bestValue;
		}else{
			
			int bestValue = INFINITY;
			board.addTile();
			int value = miniMaxSearch(board, depth - 1, true);
			bestValue = Math.min(bestValue, value);
			return bestValue;
		}
	}

	@Override
	protected int runAlgorithm(Board thoughtBoard) {
		return miniMaxSearch(thoughtBoard, SEARCH_DEPTH-1, false);
	}
	
	
}
