package utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

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
	public File file;

	public Scorer(String gameOutputFileName) {
		file = new File(OUTPUT_PATH + gameOutputFileName);
	}

	public void saveStatsToFile(int score, ArrayList<String> moves, long time) {
	//public void saveStatsToFile(ArrayList<String> moves) {
		BufferedWriter outputStream;

		try {
			outputStream = new BufferedWriter(new PrintWriter(file));
			
			int lineNo = 1;

			while (!moves.isEmpty()) {
				if (lineNo == 1) {
					outputStream.write("Score: " + score + 
							", time: " + time + " milliseconds, number of moves: " + moves.size() + "\n");
				}
				
				if (lineNo >= 3) {
					int movesOutput = LINE_LENGTH;
					if (moves.size() < LINE_LENGTH) {
						movesOutput = moves.size();
					}
					for (int i = 0; i < movesOutput; i++) {
						if (moves.get(0) != null) {
							outputStream.write(moves.get(0));
						}
						moves.remove(0);
					}
				}
				//System.out.println();
				outputStream.write('\n');
				lineNo++;
			}
			outputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
