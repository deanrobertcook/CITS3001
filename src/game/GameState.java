package game;

public class GameState {

	
	public int[] columnValues;
	public int[] rowValues;
	
	public boolean[] rowMoved;
	public boolean[] columnMoved;
	
	
	public GameState(){
		
		
	
	}
	
	public void hello(){
		
		System.out.println("Hello");
		
	}
	
	public int getValue(int[][] board){
		
		int value = 0;
		
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board.length; j++){
				
				value = value + board[i][j];
								
			}
		
		}
		
		return value;
		
	}
	
		
}
