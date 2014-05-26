package manage;

import player.AIPlayer;
import player.HumanPlayer;
import utility.RunTimer;
import game.Board;
import game.GameLoader;

public class RunGame {
	
	private static int INFINITY = 1000000;
	
	public static void main(String[] args) {
		GameLoader loader = new GameLoader("ThreesInput.txt");
		
		Board mainBoard = new Board(loader.getGameBoardFromFile(), loader.getGameSequenceFromFile());
		//HumanPlayer player = new HumanPlayer();
		
		AIPlayer ai = new AIPlayer();	
		
		while (!mainBoard.gameFinished()) {
			System.out.println();
			
			ai.presentBoard(mainBoard);
			
			String aiMove = ai.askForMove();
			System.out.println("AI decides to move: " + aiMove);
			mainBoard.play(aiMove);
			
			mainBoard.printOutBoardState();
		}
		System.out.println("Game Over! Your score was: " + mainBoard.getValue());
	}
}
