package carte;

import java.util.List;

import entite.Direction;
import entite.Entity;
import entite.Team;
import exceptions.GameException;
import exceptions.NotDoableException;
import gui.GUI;
import personnages.Besace;
import personnages.Player;
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

	public void init(GUI userInterface) {
		Coordinates coord;
		Coordinates coordP1 = new Coordinates(2, 4);
		Coordinates coordP2 = new Coordinates(31, 15);
		map[2][4].setEntity(new Player(coordP1, this, new Besace(), Direction.SOUTH, 1, 1, 1, 1, 5, 1, 1, Team.ROUGE,
				new Base(coordP1, Team.ROUGE)));
		map[31][15].setEntity(new Player(coordP2, this, new Besace(), Direction.SOUTH, 1, 1, 1, 1, 5, 1, 1, Team.BLEU,
				new Base(coordP2, Team.BLEU)));
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				coord = new Coordinates(i, j);
				if (userInterface.isObstacle(coord)) {
					map[i][j].setEntity(new Obstacle(coord, this));
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

	public void Free(Coordinates coord) {
		map[coord.getX()][coord.getY()].FreeCell();
	}

	public void setEntity(Coordinates coord, Entity ent) {
		map[coord.getX()][coord.getY()].setEntity(ent);
	}

	public Cell getCell(Coordinates coord) {
		return map[coord.getX()][coord.getY()];
	}

	public Cell getCell(int x, int y) {
		return map[x][y];
	}

	public boolean isFree(Coordinates coord) {
		return map[coord.getX()][coord.getY()].isFree();
	}

	public List<Entity> getEntity(Coordinates coord) {
		return map[coord.getX()][coord.getY()].getListEntity();
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
	public List<Entity> getListEntity(Coordinates coord) {
		return map[coord.getX()][coord.getY()].getListEntity();
	}

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
	public Class<PickAble> pickableEntity(Coordinates coord) throws NotDoableException {
		List<Entity> l = map[coord.getX()][coord.getY()].getListEntity();
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
	public void freePick(Class<PickAble> ramasse, Coordinates coord) {
		List<Entity> l = map[coord.getX()][coord.getY()].getListEntity();
		int i = 0;
		while (i < l.size()) {
			if (l.get(i).getClass() == ramasse) {
				l.remove(i);
			}
		}

	}

	public boolean isReachable(Coordinates coord) {
		List<Entity> l = map[coord.getX()][coord.getY()].getListEntity();
		int i = 0;
		while (i < l.size()) {
			if (l.get(i).isCharacter() || l.get(i).isObstacle()) {
				return false;
			}
		}
		return true;
	}

	public boolean isReachable(int x, int y) {
		List<Entity> l = map[x][y].getListEntity();
		int i = 0;
		while (i < l.size()) {
			if (l.get(i).isCharacter() || l.get(i).isObstacle()) {
				return false;
			}
		}
		return true;
	}

}
