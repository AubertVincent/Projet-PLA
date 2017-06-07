package carte;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import entite.Entity;
import operateur.Operator;
import pickable.PickAble;

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

	public void initMap() {
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

	public void Free(int x, int y) {
		map[x][y].FreeCell();
	}

	public void Add(int x, int y, Entity ent) {
		map[x][y].setEntity(ent);
	}

	public static Cell getCell(int x, int y) {
		return map[x][y];
	}

	public static boolean isFree(int x, int y) {
		return map[x][y].isFree();
	}

	//return the list of the entities present on the cell(x,y)
	public static List<Entity> getListEntity(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	//return the class of an entity present on the cell
	public static Class<PickAble> pickableEntity(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	//Take out the object of the cell
	public static void freePick(Class<PickAble> ramasse, int x, int y) {
		// TODO Auto-generated method stub
		
	}

}