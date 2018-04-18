package puzzle.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Iterator;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import puzzle.UserInterface;
import puzzle.core.Banner;
import puzzle.core.Field;
import puzzle.core.GameState;
import puzzle.core.Rock;

public class ConsoleUI implements UserInterface {

	private Banner banner;

	private Field field;

	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	private GameState actualGameState;

	public static int stepsCounter = 0;

	private enum Direction {
		UP, DOWN, LEFT, RIGHT

	}

	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	private void processInput() {
		System.out.println("ZADAJ: [Pre novu hru: new] [Pre koniec hry: exit]");
		System.out.println("Ovladanie hry:[pohyb hore: w][pohyb dole: s][pohyb dolava: a][pohyb doprava: d]");
		try {
			handleInput(readLine());
		} catch (WrongFormatException e) {
			System.out.println(e.getMessage());
		}

	}

	private void handleInput(String input) throws WrongFormatException {
		String toLowerCase = input.toLowerCase();
		Pattern pattern = Pattern.compile("(exit|new|d|s|w|a)");
		Matcher matcher = pattern.matcher(toLowerCase);
		if (matcher.matches()) {
			if (toLowerCase.equals("exit")) {
				System.out.println("Ukončujem hru!");
				System.exit(0);
			} else if (toLowerCase.equals("new")) {
				System.out.println("Spustam novu hru!");
				System.exit(0);
				newGameStarted(field);
			} else if (toLowerCase.equals("w")) {
				switchElements(Direction.UP);
			} else if (toLowerCase.equals("a")) {
				switchElements(Direction.LEFT);
			} else if (toLowerCase.equals("s")) {
				switchElements(Direction.DOWN);
				System.out.println("Idem dole");
			} else if (toLowerCase.equals("d")) {
				switchElements(Direction.RIGHT);
			}
		} else {
			throw new WrongFormatException("Neplatny vstup! Opakuj!");
		}

	}

	@Override
	public void newGameStarted(Field field) {
		System.out.println("Welcome, " + System.getProperty("user.name") + "!");
		this.field = field;
		banner = field.getBanner();
		update();
		do {
			processInput();
			update();
			if (actualGameState == GameState.SOLVED) {
				System.out.println("Vyhral si! GRATULUJEM!");
				System.exit(0);
			}
		} while (true);

	}

	@Override
	public void update() {
		banner.printBanner();
		field.printField();
		isSolved();

	}

	private void isSolved() {
		for (Iterator iterator = field.getRocks().iterator(); iterator.hasNext();) {
			Rock type = (Rock) iterator.next();
			Rock type2 = (Rock) iterator.next();
			// if (type.getRockNumber() > type2.getRockNumber()) {
			// actualGameState = GameState.SOLVED;
			// }
		}
	}

	public void switchElements(Direction direction) {
		Iterator<Rock> iterator = field.getRocks().iterator();
		Rock aktualnyKamen;
		Rock predchadzajuciKamen;
		Rock nasledujuciKamen;
		for (int h = 0; h < field.getRocks().size(); h++) {
			if (iterator.hasNext()) {
				aktualnyKamen = iterator.next();
				if (aktualnyKamen.getRockNumber() == 0) {
					switch (direction) {
					case UP: 
						field.getRocks().set(h, field.getRocks().get(h + 4));
						field.getRocks().set(h + 4, aktualnyKamen);
						break;
					case DOWN:
						nasledujuciKamen = field.getRocks().get(h - 4);
						field.getRocks().set(h - 4, aktualnyKamen);
						field.getRocks().set(h, nasledujuciKamen);
						break;
					case LEFT:
						field.getRocks().set(h, iterator.next());
						field.getRocks().set(h + 1, aktualnyKamen);
						break;
					case RIGHT:
						predchadzajuciKamen = field.getRocks().get(h - 1);
						field.getRocks().set(h, predchadzajuciKamen);
						field.getRocks().set(h - 1, aktualnyKamen);
						
						break;

					}
					stepsCounter++;
				}
			}
		}

	}

}
