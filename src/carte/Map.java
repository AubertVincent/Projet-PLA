package carte;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import entite.Entity;
import entite.GameException;
import operateur.Operator;
import pickable.PickAble;

public class Map {

	private static final int nbrOpInit = 128; // une chance sur 4 de trouver un
												// opérateur sur une cellule
	private static final int width = 32;
	private static final int height = 16;

	public final Cell[][] map = new Cell[height][width];

	public Cell[][] getMap() {
		return map;
	}

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

	public Cell getCell(int x, int y) {
		return map[x][y];
	}

	public boolean isFree(int x, int y) {
		return map[x][y].isFree();
	}

	/**
	 * return the list of the entities present on the cell(x,y)
	 * @param x x coordinate on the map
	 * @param y y coordinate on the map
	 * @return the list of the entities present on the cell
	 */
	public List<Entity> getListEntity(int x, int y) {
		return map[x][y].getListEntity();
	}

	/**
	 * return the class of an entity present on the cell
	 * @param x x coordinate on the map
	 * @param y y coordinate on the map
	 * @return the class of the first pickAble object
	 * @throws GameException
	 */
	@SuppressWarnings("unchecked")
	public Class<PickAble> pickableEntity(int x, int y) throws GameException {
		List<Entity> l = map[x][y].getListEntity();
		int i = 0;
		while (i < l.size() - 1) {
			if (l.get(i).isPickAble()) {
				return ((Class<PickAble>) l.get(i).getClass());
			}
		}
		throw new GameException("Rien à ramasser ici");
	}


	/**
	 * Take out the object of the cell
	 * @param ramasse
	 * @param x x coordinate on the map
	 * @param y y coordinate on the map
	 */
	public void freePick(Class<PickAble> ramasse, int x, int y) {
		List<Entity> l = map[x][y].getListEntity();
		int i = 0;
		while (i < l.size() - 1) {
			if (l.get(i).getClass() == ramasse){
				l.remove(i);
			}
		}

	}

}