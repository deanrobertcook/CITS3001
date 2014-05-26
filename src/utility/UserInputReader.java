package utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class UserInputReader {
	public ArrayList<String> validInputs;
	
	public UserInputReader (ArrayList<String> validInputs) {
		this.validInputs = validInputs;
	}
	
	public String getUserInput(String promptMessage) {
		System.out.print(promptMessage + ": ");

	    //  open up standard input
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	    String word = null;

	    //  read the username from the command-line; need to use try/catch with the
	    //  readLine() method
	    try {
	       word = br.readLine();
	    } catch (IOException ioe) {
	       System.out.println("IO error trying to read your name!");
	       System.exit(1);
	    }
	    
	    if (word.equals("exit")) {
			System.exit(0);
		}
	    
	    if (!validInputs.contains(word)) {
	    	System.out.println(word + " is not a valid input.");
	    	word = this.getUserInput(promptMessage);
		}
	    return word;
	}
}
