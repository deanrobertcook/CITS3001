package player;

import java.util.ArrayList;

import utility.UserInputReader;

public class HumanPlayer implements Player {
	UserInputReader inputReader;
	
	public HumanPlayer () {
		ArrayList<String> validInputs = new ArrayList<String>();
		validInputs.add("L");
		validInputs.add("l");
		validInputs.add("R");
		validInputs.add("r");
		validInputs.add("U");
		validInputs.add("u");
		validInputs.add("D");
		validInputs.add("d");
		this.inputReader = new UserInputReader(validInputs);	
	}
	
	@Override
	public String askForMove() {
		String direction = inputReader.
				getUserInput("Please push the tiles either Left, Right, Up or Down (type L,R,U, or D)");
		
		direction = direction.toUpperCase();
		return direction;
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
