package player;

import game.Board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import utility.ObjectCloner;

public class AlphaBetaPlayer extends AIPlayer {

	private static final int INFINITY = 1000000;
	private static final int SEARCH_DEPTH = 4;

	private int alphaBeta(Board thoughtBoard, int depth, int alpha, int beta, boolean maximizingPlayer){
		int bestScore = 0;		
		
		if(depth == 0){
			bestScore = evaluationFunction(thoughtBoard);
			return bestScore;
		}
		
		if(maximizingPlayer == true){
			ArrayList<Board> children = createChildren(thoughtBoard);
			if(children.isEmpty()){
				bestScore = evaluationFunction(thoughtBoard);
				return bestScore;
			}
			for(Board child : children){
				alpha = Math.max(alpha, alphaBeta(child, depth - 1, alpha, beta, false));
				if(beta <= alpha){
					break;
				}
			}
			
			return alpha;
		}else{
			thoughtBoard.addTile();
			beta = Math.min(beta, alphaBeta(thoughtBoard, depth - 1, alpha, beta, true));
			return beta;			
		}

	}
	
	@Override
	protected int runAlgorithm(Board thoughtBoard) {
		return alphaBeta(thoughtBoard, SEARCH_DEPTH-1, -INFINITY, INFINITY, false);
	}
}