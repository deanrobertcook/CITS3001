package player;
import game.Game;

import java.lang.Math;
import java.util.Vector;


public class AIPlayer implements Player {
	
	private static int INFINITY = 10000000;
	private Vector<String> moveList;
	private int depth;
	private int count;
	public Game aigame;
	public int[][] testBoard;		
	public AIPlayer(){
		
		this.depth = 50;
		this.count = 0;
		this.moveList = new Vector<String>();
		moveList.add("U");
		moveList.add("L");
		moveList.add("D");
		moveList.add("R");
		
		testBoard = new int[4][4];
		testBoard[2][3] = 5;
		
		this.aigame = new Game();
				
	}

	
	public void alphaBeta(int[][] startState){	//return moves
		
		maxValue(startState, -INFINITY, INFINITY);
				
	}
	
	//go down to terminal state then report a value
	
	public int maxValue(int[][] state, int alpha, int beta){ // pass in board, for each direction, evaluation function for value, account for illegal mvoes (currently gets stuck)
		
		if(count == depth){
					
			System.exit(0);
			
		}

		System.out.println("Maximising");
		int v = -INFINITY;		
		for(String direction : moveList){
			
			System.out.println("Moving: \t" + direction);
			int[][] childBoard = state;	
			
			if(aigame.pushEdge(direction, childBoard)){
				
				/*
				
				for (int i = 0; i < childBoard.length; i++) {
					for (int j = 0; j < childBoard[i].length; j++) {
						System.out.print(childBoard[i][j] + "\t");
					}
					System.out.println();
				}
				*/
				
				aigame.getValue(childBoard);
				
				v = Math.max(v, minValue(childBoard, alpha, beta, direction));			
				if(v <= beta){
					
					return v;
					
				}			
				alpha = Math.max(alpha, v);
							
			}
				
		}
		
		return v;
	}
	
	
	public int minValue(int[][] state, int alpha, int beta, String direction){	//this should be the computer's turn, ie, addTile 
		
		
		if(count == depth){
			
			System.exit(0);
			
		}
		count++;
		
		
		System.out.println("Minimising");
		int w = INFINITY;
		//for(String direction : moveList){
			
			System.out.println("Inserting: \t" + direction);
			int[][] childBoard = state;			
			
			
			if(aigame.addTile(direction, childBoard)){
			
				for (int i = 0; i < childBoard.length; i++) {
					for (int j = 0; j < childBoard[i].length; j++) {
						System.out.print(childBoard[i][j] + "\t");
					}
					System.out.println();
				}
				
				
			w = Math.min(w, maxValue(childBoard, alpha, beta));
			if(w <= alpha){
			
				return w;
				
			}			
			beta = Math.min(beta, w);
			
		//}
		

		}
		return w;
	}
	
	
	public void play(){
		
		boolean gameFinished = true;

		alphaBeta(aigame.board);
		
		while (!gameFinished) {
			play();
		}
	}
	

}
