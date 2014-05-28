package utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Class Scorer records the moves taken, the time taken, and the
 * final state of the board after a game and saves this information
 * to a text file, conforming to the project specs.
 * @author Dean
 *
 */
public class Scorer {
	public static final String OUTPUT_PATH = "gameOutputs/";
	public static final int LINE_LENGTH = 40;
	public String outputText;

	public Scorer(int score, ArrayList<String> moves, long time) {
		prepareText(score, moves, time);
	}

	private void prepareText(int score, ArrayList<String> moves, long time) {
		this.outputText = "";
		int lineNo = 1;

		while (!moves.isEmpty()) {
			if (lineNo == 1) {
				this.outputText += "Score: " + score + 
						", time: " + time + " milliseconds, number of moves: " + (moves.size()-1) + "\n";
			}
			
			if (lineNo >= 3) {
				int movesOutput = LINE_LENGTH;
				if (moves.size() < LINE_LENGTH) {
					movesOutput = moves.size();
				}
				for (int i = 0; i < movesOutput; i++) {
					if (moves.get(0) != null) {
						this.outputText += moves.get(0);
					}
					moves.remove(0);
				}
			}
			this.outputText += '\n';
			lineNo++;
		}
	}
	
	public void saveStatsToFile(String fileName) {
		File file = new File(OUTPUT_PATH + fileName);
		BufferedWriter outputStream;

		try {
			outputStream = new BufferedWriter(new PrintWriter(file));
			outputStream.write(this.outputText);
			outputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void printStatsToScreen() {
		System.out.println(this.outputText);
	}
}
