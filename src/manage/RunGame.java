package manage;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import player.AIPlayer;
import player.HumanPlayer;
import utility.RunTimer;
import utility.Scorer;
import game.Board;
import game.GameLoader;

public class RunGame {	
	public static void main(String[] args) {
		GameLoader loader = new GameLoader("ThreesInput.txt");
		
		Board mainBoard = new Board(loader.getGameBoardFromFile(), loader.getGameSequenceFromFile());
		//HumanPlayer player = new HumanPlayer();
		AIPlayer player = new AIPlayer();	
		
		Scorer scorer = new Scorer("outputFile.txt");
		ArrayList<String> moves = new ArrayList<String>();
		
		RunTimer timer = new RunTimer();
		
		while (!mainBoard.gameFinished()) {
			player.presentBoard(mainBoard);
			
			String playerMove = player.askForMove();
			moves.add(playerMove);
			
			mainBoard.play(playerMove);
		}
		scorer.saveStatsToFile(mainBoard.getValue(), moves, timer.lap());
	}
}
