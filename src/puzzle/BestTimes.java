package puzzle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

public class BestTimes implements Iterable<BestTimes.PlayerTime>, Serializable {

	private static final long serialVersionUID = 1L;

	private List<PlayerTime> playerTimes = new ArrayList<PlayerTime>();

	private BestTimes bestTimes = new BestTimes();

	private Formatter f;

	private Iterator<PlayerTime> iterator;

	public Iterator<PlayerTime> iterator() {
		return iterator = playerTimes.iterator();
	}

	public void addPlayerTime(String name, int time) {
		PlayerTime playerTime = new PlayerTime(name, time);
		playerTimes.add(playerTime);
		Collections.sort(playerTimes);
	}

	public String toString() {
		f = new Formatter();
		for (int i = 0; i < playerTimes.size(); i++) {
			f.format("%d. %s -> %d", i, playerTimes.get(i).getName(), playerTimes.get(i).getTime());
		}
		return f.toString();
	}

	public BestTimes getBestTimes() {
		return bestTimes;
	}

	public void reset() {
		for (int i = 0; i < playerTimes.size(); i++) {
			if (!playerTimes.isEmpty()) {
				playerTimes.remove(i);
			}
		}
	}
	


	public static class PlayerTime implements Comparable<PlayerTime> {

		private final String name;

		private final int time;

		public PlayerTime(String name, int time) {
			this.name = name;
			this.time = time;
		}

		public String getName() {
			return name;
		}

		public int getTime() {
			return time;
		}

		@Override
		public int compareTo(BestTimes.PlayerTime o) {
			if (this.getTime() < o.getTime()) {
				return -1;
			} else if (this.getTime() > o.getTime()) {
				return 1;
			} else
				return 0;
		}

	}
}
