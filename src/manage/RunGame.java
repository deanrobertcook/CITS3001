package manage;

import java.util.ArrayList;

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
		HumanPlayer player = new HumanPlayer();
		
		//Testing
		AIPlayer ai = new AIPlayer(mainBoard);	
		ai.presentBoard(mainBoard.board);
		
		RunTimer runner = new RunTimer();
		System.out.println(ai.alphaBeta(mainBoard, 6, -INFINITY, INFINITY, true));
		System.out.println(runner.lap());		
		
		/*
		ArrayList<Board> test = new ArrayList<Board>();

		test = ai.createChildren(mainBoard);
		
		
		System.out.println(test.toString());
		
		for(Board board : test){
			
			board.printOutBoardState();
			System.out.println();
			
		}		
		*/
		//Testing complete
		
		
		/*
		while (!mainBoard.gameFinished()) {
			player.presentBoard(mainBoard.getBoardState()); //show the player the board
			mainBoard.play(player.askForMove()); //get the players move
		}
		*/
		
//		AIPlayer AI = new AIPlayer();
//		AI.aigame.printOutBoardState();
//		System.out.println();
//		System.out.println();
//		AI.play();
		
		//test
		//game.printOutBoardState();
		//game.play();
	}
}
