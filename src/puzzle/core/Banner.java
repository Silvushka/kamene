package puzzle.core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import puzzle.consoleui.ConsoleUI;

public class Banner {

	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	private long startMillis;

	public Banner() {
		startMillis = System.currentTimeMillis();
	}

	public int getPlayingSeconds() {
		return (int) (System.currentTimeMillis() - startMillis) / 1000;
	}

	public void printBanner() {
		System.out.print("Aktualny cas: ");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		System.out.print("Dlzka hry: " + getPlayingSeconds() + "	");
		System.out.println("Pocet spravenych tahov: " + ConsoleUI.stepsCounter);
	}

}
