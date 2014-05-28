package player;

import game.Board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import utility.ObjectCloner;

public class AIPlayerWithDegub implements Player {

	private static final int INFINITY = 1000000;
	private static final int SEARCH_DEPTH = 4;
	
	private ArrayList<String> moveList;
	private Board currentBoard;
	
	private LinkedHashMap<String, String> thoughtList;
	private int thoughtIndex;
	private int movesMade;
	
	public AIPlayerWithDegub() {
		this.movesMade = 1;
		thoughtList = new LinkedHashMap<String, String>();
		thoughtIndex = 0;
		
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
			this.thoughtList.put(Integer.toString(this.thoughtIndex), "Depth reached, board evaluated at: " + bestScore);
			thoughtIndex++;
			return bestScore;
		}
		
		if(maximizingPlayer == true){
			int childNo = 0;
			for(Board child : children){
				
				this.thoughtList.put(Integer.toString(this.thoughtIndex), "");
				alpha = Math.max(alpha, alphaBeta(child, depth - 1, alpha, beta, false));
				this.thoughtList.put(Integer.toString(this.thoughtIndex),
						"MAX" + (this.SEARCH_DEPTH - depth) +", CHILD" + childNo + " Alpha: " + alpha + ", Beta: " + beta);
				thoughtIndex++;
				if(beta <= alpha){
					break;
				}
				childNo++;
			}
			
			return alpha;
		}else{
			thoughtBoard.addTile();
			this.thoughtList.put(Integer.toString(this.thoughtIndex), "");
			beta = Math.min(beta, alphaBeta(thoughtBoard, depth - 1, alpha, beta, true));
			this.thoughtList.put(Integer.toString(this.thoughtIndex),
					"MIN" + (this.SEARCH_DEPTH - depth) + " Alpha: " + alpha + ", Beta: " + beta);
			thoughtIndex++;
			return beta;			
		}

	}
	
	public int miniMaxSearch(Board board, int depth, boolean humanPlayer){
		

		ArrayList<Board> children = createChildren(board);
		
		if(depth == 0 || children.isEmpty()){
			return evaluationFunction(board);
		}
		
		if(humanPlayer == true){
			
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
	
	public void showThoughtProcess() {
		for (String thought : this.thoughtList.values()) {
			System.out.println(thought);
		}
	}
	
	public void clearThoughtProcess() {
		this.thoughtList.clear();
	}

	private int evaluationFunction(Board in){		
		return in.getValue();
	}
	
	@Override
	public String askForMove() {
		Board thoughtBoard = (Board) ObjectCloner.deepClone(this.currentBoard);
		ArrayList<Board> firstChildren = createChildren(thoughtBoard);
		
		System.out.println(firstChildren.toString());
		
		int bestScore = 0;
		String nextMove = null;
		
		int childNo = 0;
		this.thoughtList.put("Header", "START MOVE " + this.movesMade);
		for (Board firstChild : firstChildren) {
			
			this.thoughtList.put("Seperator", "************************");
			this.thoughtList.put(Integer.toString(this.thoughtIndex), "");
			int score = alphaBeta(firstChild, SEARCH_DEPTH-1, -INFINITY, INFINITY, false);
			this.thoughtList.put(Integer.toString(this.thoughtIndex), 
					"MAX0, CHILD" + childNo + " Score = " + score);
			thoughtIndex++;
			if (score > bestScore) {
				bestScore = firstChild.getValue();
				nextMove = firstChild.directionPushed;
			}
			childNo++;
		}
		this.thoughtList.put("Footer", "END MOVE " + this.movesMade);
		this.movesMade++;
		return nextMove;
	}

	@Override
	public void presentBoard(Board board) {
		this.currentBoard = board;
	}
}