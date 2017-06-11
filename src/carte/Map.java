package carte;

import java.util.List;

import entite.Entity;
import entite.Team;
import exceptions.GameException;
import exceptions.NotDoableException;
import gui.GUI;
import moteurDuJeu.Engine;
import pickable.PickAble;

public class Map {

	private int nbrOperatorInit = 128;
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

	public void init(GUI userInterface, Engine engine) {
		map[engine.getPlayer(Team.ROUGE).getX()][engine.getPlayer(Team.ROUGE).getY()]
				.setEntity(engine.getPlayer(Team.ROUGE));
		map[engine.getPlayer(Team.BLEU).getX()][engine.getPlayer(Team.BLEU).getY()]
				.setEntity(engine.getPlayer(Team.BLEU));
		// Set the obstacles on the map
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (userInterface.isObstacle(i, j)) {
					map[i][j].setEntity(new Obstacle(i, j, this));
				}
			}
		}
		// Once obstacles are set,
		// Set random operator at random places
		int randomX = (int) (Math.random() * (width));
		int randomY = (int) (Math.random() * (height));
		PickAble newPickAble;
		for (int i = 0; i < nbrOperatorInit; i++) {
			while (!map[randomX][randomY].isFree()) {
				randomX = (int) (Math.random() * (width));
				randomY = (int) (Math.random() * (height));
			}
			newPickAble = PickAble.randomPickable((int) ((int) 1 + (Math.random() * (9))), randomX, randomY, this);
			map[randomX][randomY].setEntity(newPickAble);
			randomX = (int) (Math.random() * (width));
			randomY = (int) (Math.random() * (height));
		}
	}

	public int mapHeight() {
		return this.height;
	}

	public int mapWidth() {
		return this.width;
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

	private void printMap() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (map[i][j].getListEntity().size() != 0) {
					System.out.println(
							"case :" + i + ',' + j + " " + map[i][j].getListEntity().get(0).getClass().toString());

				}
			}
		}
	}

	private void testObstacle() {
		// Once obstacles are set,
		// Set random operator at random places
		int randomX = (int) (Math.random() * (width));
		int randomY = (int) (Math.random() * (height));
		PickAble newPickAble;
		for (int i = 0; i < nbrOperatorInit; i++) {
			while (!map[randomX][randomY].isFree()) {
				randomX = (int) (Math.random() * (width));
				randomY = (int) (Math.random() * (height));
			}
			newPickAble = PickAble.randomPickable((int) ((int) 1 + (Math.random() * (9))), randomX, randomY, this);
			map[randomX][randomY].setEntity(newPickAble);
			randomX = (int) (Math.random() * (width));
			randomY = (int) (Math.random() * (height));
		}
	}

	public static void main(String[] args) {
		Map ma_map = new Map();
		ma_map.testObstacle();
		ma_map.printMap();

	}

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

	public boolean isPickAble(int x, int y) {
		List<Entity> EntityList = map[x][y].getListEntity();
		boolean allIsPickAble = true;
		if (!EntityList.equals(null)) {
			for (Entity e : EntityList) {
				if (!e.isPickAble()) {
					return false;
				}
			}
		}
		return allIsPickAble;
	}

}
