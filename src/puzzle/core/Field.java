package puzzle.core;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Field {

	private final List<Rock> rocks;

	private final List<Rock> test = new ArrayList<>();

	private final int rowCount;

	private final int columnCount;

	private final int[] arrayOfInts;

	private static Banner banner;

	private GameState state = GameState.PLAYING;

	public Field(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		rocks = new ArrayList<>();
		arrayOfInts = new int[(rowCount * columnCount) - 1];
//		fillWithRocks();
		banner = new Banner();
		 test.add(new Rock(1));
		 test.add(new Rock(2));
		 test.add(new Rock(3));
		 test.add(new Rock(4));
		 test.add(new Rock(5));
		 test.add(new Rock(6));
		 test.add(new Rock(7));
		 test.add(new Rock(8));
		 test.add(new Rock(9));
		 test.add(new Rock(10));
		 test.add(new Rock(11));
		 test.add(new Rock(12));
		 test.add(new Rock(13));
		 test.add(new Rock(14));
		 test.add(new Rock(15));
		 test.add(new Rock(0));

	}

	public List<Rock> getRocks() {
		return test;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	private List<Rock> fillWithRocks() {
		Iterator<Rock> iterator;
		Random random = new Random();
		boolean adding = true;
		int numberToAdd;
		int addingNumber;
		for (int i = 0; i < arrayOfInts.length; i++) {
			numberToAdd = random.nextInt(16);
			if (numberToAdd == 0) {
				adding = false;
			} else {
				addingNumber = numberToAdd;
			}
			for (int j = 0; j < arrayOfInts.length; j++) {
				if (arrayOfInts[j] == numberToAdd) {
					adding = false;
				}
			}
			if (adding) {
				arrayOfInts[i] = numberToAdd;
				adding = true;
			} else {
				adding = true;
				i--;
			}
		}
		for (int l = 0; l < arrayOfInts.length; l++) {
			rocks.add(new Rock(arrayOfInts[l]));
		}
		rocks.add(new Rock(0));
		return rocks;
	}

	public void printField() {
		int numberOfRows = rowCount * columnCount;
		for (Rock rock : this.getRocks()) {
			if ((numberOfRows % 4) == 0) {
				System.out.println();
				System.out.print("	" + rock.getRockNumber());
				numberOfRows--;
			} else {
				System.out.print("	" + rock.getRockNumber());
				numberOfRows--;
			}
		}
		System.out.println();
		System.out.println();
	}

	public static Banner getBanner() {
		return banner;
	}

	public GameState getState() {
		return state;
	}

}
