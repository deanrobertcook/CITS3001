package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

public class GameLoader {
	public static final String INPUT_PATH = "gameInputs/";
	public File file;

	public GameLoader(String gameInputFileName) {
		file = new File(INPUT_PATH + gameInputFileName);
	}

	public int[][] getGameBoardFromFile() {
		int[][] inputBoard = new int[4][4];
		BufferedReader inputStream;

		try {
			inputStream = new BufferedReader(new FileReader(file));

			String line;
			int lineNo = 1;

			while ((line = inputStream.readLine()) != null) {
				if (lineNo >= 3 && lineNo < 7) {
					int rowIndex = 0;
					int columnIndex = lineNo - 3;

					String nextTile = "";

					for (int i = 0; i < line.length(); i++) {
						if (line.charAt(i) == ' ' && line.charAt(i-1) != ' ') {
							inputBoard[columnIndex][rowIndex] = Integer
									.parseInt(nextTile);
							rowIndex++;
							nextTile = "";
						} else {
							nextTile += line.charAt(i);
						}
					}
				}
				lineNo++;
			}

			inputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return inputBoard;

	}

	public LinkedBlockingQueue<String> getGameSequenceFromFile() {
		LinkedBlockingQueue<String> inputSequence = new LinkedBlockingQueue<String>();
		BufferedReader inputStream;

		try {
			inputStream = new BufferedReader(new FileReader(file));

			String line;
			int lineNo = 1;

			while ((line = inputStream.readLine()) != null) {
				if (lineNo >= 8) {
					String nextSequence = "";
					
					//System.out.println("LAST CHARACTER OF LINE " + );
					for (int i = 0; i < line.length(); i++) {
						if (line.charAt(i) == ' ') {
							inputSequence.add(nextSequence);
							nextSequence = "";
						} else {
							nextSequence += line.charAt(i);
						}
					}
					inputSequence.add(nextSequence);
				}
				lineNo++;
			}

			inputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inputSequence;
	}
}
