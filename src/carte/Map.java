package carte;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import entite.Direction;
import entite.Entity;
import operateur.Operator;
import personnages.Player;

public class Map {

	private static final int nbrOpInit = 128; // une chance sur 4 de trouver un
												// opérateur sur une cellule
	private static final int width = 34;
	private static final int height = 20;

	public static final Cell[][] map = new Cell[width][height];

	public Map() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				map[i][j] = new Cell(i, j);
			}
		}
	}

	public static void initMap() {
		int randomLine;
		int randomColumn;
		int i = 0;
		map[4][2].setEntity(new Player(5, 12, Direction.NORTH, 1, 1, 1, 1, 5, 1));
		map[30][15].setEntity(new Player(30, 15, Direction.NORTH, 1, 1, 1, 1, 5, 1));
//		while (i < nbrOpInit) {
//			randomLine = ThreadLocalRandom.current().nextInt(0, height);
//			randomColumn = ThreadLocalRandom.current().nextInt(0, width);
//			if (map[randomLine][randomColumn].isFree()) {
//				map[randomLine][randomColumn].setEntity(Operator.randomOp());
//				i++;
//			}
//		}
	}

	public static boolean isEmpty() {

		boolean my_bool = true;
		for (int i = 0; i < height && my_bool; i++) {
			for (int j = 0; j < width && my_bool; j++) {
				my_bool = map[i][j].isEmpty();
			}
		}
		return my_bool;
	}

	public static void Free(int x, int y) {
		map[x][y].FreeCell();
	}

	public static void Add(int x, int y, Entity ent) {
		map[x][y].setEntity(ent);
	}

	public static Cell getCell(int x, int y) {
		return map[x][y];
	}

	public static boolean isFree(int x, int y) {
		return map[x][y].isFree();
	}

	public static List<Entity> getEntity(int x, int y) {
		return map[x][y].getListEntity();
	}
	
	
	private static void printMap(){
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				System.out.println("case :" +i + ','+j+" "+ map[i][j].isFree());
			}
			//System.out.println("\n");
		}
	}
	public static void main(String[] args) {
		Map ma_map = new Map();
		Map.initMap();
		Map.printMap();
		
		
	}
	
	
}
