package player;

import game.Board;

public interface Player {
	public String askForMove();
	public void presentBoard(Board board);
}
