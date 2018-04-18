package puzzle;

import puzzle.consoleui.ConsoleUI;
import puzzle.core.Banner;
import puzzle.core.Field;

public class Puzzle {

	private UserInterface userInterface;

	public static Banner banner;

	private static Puzzle instance;

	public Puzzle() {
		instance = this;
		Field field = new Field(4, 4);
		userInterface = new ConsoleUI();
		userInterface.newGameStarted(field);
	}

	public static void main(String[] args) {
		new Puzzle();

	}

	private static Puzzle getInstance() {
		if (instance == null) {
			new Puzzle();
		}
		return instance;
	}

	

}
