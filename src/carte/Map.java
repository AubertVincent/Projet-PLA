package carte;

import java.util.List;

import entite.Entity;
import entite.Team;
import exceptions.GameException;
import exceptions.NotDoableException;
import gui.GUI;
import moteurDuJeu.Engine;
import moteurDuJeu.Test;
import pickable.PickAble;

public class Map {

	private int width = 34;
	private int height = 18;

	public final Cell[][] map = new Cell[width][height];

	public Map() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				map[i][j] = new Cell(i, j);
			}
		}
	}

	// Used for test delete when it's over
	public void init(Test test) {
		map[5][10].setEntity(test.getRobot(Team.ROUGE));
		map[5][11].setEntity(test.getRobot(Team.BLEU));
		map[5][12].setEntity(new Obstacle(5, 5, this));
	}

	public void init(GUI userInterface, Engine engine) {
		map[2][4].setEntity(engine.getPlayer(Team.ROUGE));
		map[31][15].setEntity(engine.getPlayer(Team.BLEU));
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (userInterface.isObstacle(i, j)) {
					map[i][j].setEntity(new Obstacle(i, j, this));
				}
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

	public void setEntity(int x, int y, Entity ent) {
		map[x][y].setEntity(ent);
	}

	public Cell getCell(int x, int y) {
		return map[x][y];
	}

	public boolean isFree(int x, int y) {
		return map[x][y].isFree();
	}

	public List<Entity> getEntity(int x, int y) {
		return map[x][y].getListEntity();
	}

	// private void printMap() {
	// for (int i = 0; i < width; i++) {
	// for (int j = 0; j < height; j++) {
	// System.out.println("case :" + i + ',' + j + " " + map[i][j].isFree());
	// }
	// }
	// }

	// public static void main(String[] args) {
	// Map ma_map = new Map();
	// Map.initMap();
	// Map.printMap();
	//
	// }

	/**
	 * return the list of the entities present on the cell(x,y)
	 * 
	 * @param x
	 *            x coordinate on the map
	 * @param y
	 *            y coordinate on the map
	 * @return the list of the entities present on the cell
	 */
	public List<Entity> getListEntity(int x, int y) {
		return map[x][y].getListEntity();
	}

	/**
	 * return the class of an entity present on the cell
	 * 
	 * @param x
	 *            x coordinate on the map
	 * @param y
	 *            y coordinate on the map
	 * @return the class of the first pickAble object
	 * @throws GameException
	 */
	@SuppressWarnings("unchecked")
	public Class<PickAble> pickableEntity(int x, int y) throws NotDoableException {
		List<Entity> l = map[x][y].getListEntity();
		int i = 0;
		while (i < l.size()) {
			if (l.get(i).isPickAble()) {
				return ((Class<PickAble>) l.get(i).getClass());
			}
		}
		throw new NotDoableException("Rien à ramasser ici");
	}

	/**
	 * Take out the object of the cell
	 * 
	 * @param ramasse
	 * @param x
	 *            x coordinate on the map
	 * @param y
	 *            y coordinate on the map
	 */
	public void freePick(Class<PickAble> ramasse, int x, int y) {
		List<Entity> l = map[x][y].getListEntity();
		int i = 0;
		while (i < l.size()) {
			if (l.get(i).getClass() == ramasse) {
				l.remove(i);
			}
		}

	}

	// FIXME i is never incremented
	public boolean isReachable(int x, int y) {
		List<Entity> l = map[x][y].getListEntity();
		int i = 1;
		while (i < l.size()) {
			boolean b = l.get(i).isCharacter() || l.get(i).isObstacle();
			if (b) {
				return false;
			}
		}
		return true;
	}

}
