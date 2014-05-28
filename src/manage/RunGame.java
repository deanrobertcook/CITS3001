package manage;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import player.GreedyPlayer;
import player.AlphaBetaPlayer;
import player.HumanPlayer;
import player.MiniMaxPlayer;
import utility.RunTimer;
import utility.Scorer;
import game.Board;
import game.GameLoader;

public class RunGame {	
	public static void main(String[] args) {
		GameLoader loader = new GameLoader("B1.txt");
		
		Board mainBoard = new Board(loader.getGameBoardFromFile(), loader.getGameSequenceFromFile());
//		HumanPlayer player = new HumanPlayer();
		AlphaBetaPlayer player = new AlphaBetaPlayer();	
//		MiniMaxPlayer player = new MiniMaxPlayer();
//		AStarPlayer player = new AStarPlayer();
//		GreedyPlayer player = new GreedyPlayer();
		
		ArrayList<String> moves = new ArrayList<String>();
		
		RunTimer timer = new RunTimer();
		
		while (!mainBoard.gameFinished()) {
			player.presentBoard(mainBoard);
//			mainBoard.printOutBoardState();
			
			String playerMove = player.askForMove();
			System.out.println(playerMove);
			moves.add(playerMove);
			
			mainBoard.play(playerMove);
			mainBoard.printOutBoardState();
		}
		
		Scorer scorer = new Scorer(mainBoard.getValue(), moves, timer.lap());
		scorer.printStatsToScreen();
		scorer.saveStatsToFile("output.txt");
	}
}
