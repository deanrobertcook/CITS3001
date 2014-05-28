package player;

import java.util.ArrayList;

import utility.ObjectCloner;
import game.Board;

public class GreedyPlayer extends AIPlayer {

	private static int DEPTH = 6;
	
	private int greedySearch(Board thoughtBoard, int depth) {
		int bestScore = 0;
		if (depth <= 0) {
			bestScore = evaluationFunction(thoughtBoard);
		} else {
			ArrayList<Board> children = this.createChildren(thoughtBoard);
			for (Board child : children) {
				int tempScore = greedySearch(child, depth - 1);
				if (tempScore > bestScore) {
					bestScore = tempScore;
				}
			}
		}
		return bestScore;
	}

	@Override
	protected int runAlgorithm(Board thoughtBoard) {
		return greedySearch(thoughtBoard, DEPTH-1);
	}
	
}
