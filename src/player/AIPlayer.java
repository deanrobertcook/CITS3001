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

	
	public int alphaBeta(Board board, int depth, int alpha, int beta, boolean isPlayerHuman){
		
		
		
		int bestScore = 0;		
		ArrayList<Board> children = createChildren(board);
		
		if((children.isEmpty()) || (depth == 0)){
			
			bestScore = evaluationFunction(board);
			return bestScore;
		}
		
		if(isPlayerHuman == true){
			
			for(Board child : children){
				
				System.out.println("Moved " + child.directionPushed);
				child.printOutBoardState();
				System.out.println();
				alpha = Math.max(alpha, alphaBeta(child, depth - 1, alpha, beta, false));
				if(beta <= alpha){
					
					System.out.println("MAX PRUNE");
					break;
				}
				
			}
			
			return alpha;
		}else{
					
			System.out.println(board.peekNextSequence());
			board.addTile(board.getNextSeqence());
			System.out.println("Tile added " + board.directionPushed);
			board.printOutBoardState();
			System.out.println();
			
				beta = Math.min(beta, alphaBeta(board, depth - 1, alpha, beta, true));
				if(beta <= alpha){
					
					System.out.println("MIN PRUNE");
				}
				
			
			return beta;			
		}

	}
	
	public ArrayList<Board> createChildren(Board in) {

		ArrayList<Board> children = new ArrayList<Board>();

		for (String direction : moveList) {

			Board newBoard = (Board) ObjectCloner.deepClone(in);

			if (newBoard.pushEdge(direction)) {

				children.add(newBoard);

			}

		}

		return children;

	}

	public int evaluationFunction(Board in){		
		
		return in.getValue();
		
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