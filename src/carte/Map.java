package carte;

import java.util.concurrent.ThreadLocalRandom;

import operateur.Operator;

public class Map {

	private static final int nbrOpInit = 128; // une chance sur 4 de trouver un
												// opérateur sur une cellule
	private static final int width = 32;
	private static final int height = 16;

	public static final Cell[][] map = new Cell[height][width];

	public Map() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				map[i][j] = new Cell(i, j);
			}
		}
	}

	public void initCarte() {
		int randomLine;
		int randomColumn;
		int i = 0;
		while (i < nbrOpInit) {
			randomLine = ThreadLocalRandom.current().nextInt(0, height);
			randomColumn = ThreadLocalRandom.current().nextInt(0, width);
			if (map[randomLine][randomColumn].isFree()) {
				map[randomLine][randomColumn].setEntity(Operator.randomOp());
				i++;
			}
		}
	}

	public boolean isEmpty() {

		boolean my_bool = true;
		for (int i = 0; i < height && my_bool; i++) {
			for (int j = 0; j < width && my_bool; j++) {
				my_bool = map[i][j].isEmpty();
			}
		}
		return my_bool;
	}

}
