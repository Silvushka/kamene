package puzzle.consoleui;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Savepoint;
import java.util.Iterator;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import puzzle.BestTimes;
import puzzle.BestTimes.PlayerTime;
import puzzle.UserInterface;
import puzzle.core.Banner;
import puzzle.core.Field;
import puzzle.core.GameState;
import puzzle.core.Rock;

public class ConsoleUI implements UserInterface {

	private Banner banner;

	private Field field;
	
	private BestTimes bestTimes;

	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	private GameState actualGameState;
	
	private static final String BEST_TIMES_FILE = System.getProperty("user.home") + System.getProperty("file.separator")
	+ "puzzle.bestTimes";

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
				System.out.println("Ukoncujem hru!");
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
			} else if (toLowerCase.equals("d")) {
				switchElements(Direction.RIGHT);
			}
		} else {
			throw new WrongFormatException("Neplatny vstup! Opakuj!");
		}

	}

	@Override
	public void newGameStarted(Field field) {
//		bestTimes = load();
		System.out.println("Welcome, " + System.getProperty("user.name") + "!");
		this.field = field;
		banner = field.getBanner();
		update();
		do {
			processInput();
			if (isSolved()) {
				System.out.println("Vyhral si! GRATULUJEM!");
//				save();
				System.exit(0);
			}
			update();
		} while (true);

	}

	@Override
	public void update() {
		banner.printBanner();
		field.printField();
		isSolved();
	}

	private boolean isSolved() {
		for (int i = 0; i < field.getRocks().size() - 1; i++) {
			if (field.getRocks().get(i).getRockNumber() == i + 1) {
			} else {
				return false;
			}
		}
		return true;
	}
	
	public void save() {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(BEST_TIMES_FILE));) {
			outputStream.writeObject(bestTimes.getBestTimes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BestTimes load() {
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(BEST_TIMES_FILE));) {
			BestTimes bestTimes = (BestTimes) inputStream.readObject();
			return bestTimes;
		} catch (FileNotFoundException e) {
			System.out.println("Cannot load from file (" + e.getMessage() + ")");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void switchElements(Direction direction) {
		Iterator<Rock> iterator = field.getRocks().iterator();
		Rock currentRock;
		Rock lastRock;
		Rock nextRock;
		for (int h = 0; h < field.getRocks().size(); h++) {
			if (iterator.hasNext()) {
				currentRock = iterator.next();
				if (currentRock.getRockNumber() == 0) {
					switch (direction) {
					case UP:
						field.getRocks().set(h, field.getRocks().get(h + 4));
						field.getRocks().set(h + 4, currentRock);
						break;
					case DOWN:
						nextRock = field.getRocks().get(h - 4);
						field.getRocks().set(h - 4, currentRock);
						field.getRocks().set(h, nextRock);
						break;
					case LEFT:
						field.getRocks().set(h, iterator.next());
						field.getRocks().set(h + 1, currentRock);
						break;
					case RIGHT:
						lastRock = field.getRocks().get(h - 1);
						field.getRocks().set(h, lastRock);
						field.getRocks().set(h - 1, currentRock);
						break;
					}
					stepsCounter++;
				}
			}
		}

	}

}
