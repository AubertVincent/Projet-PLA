package carte;

import java.util.List;

import entite.Entity;
import entite.Team;
import exceptions.GameException;
import exceptions.NotDoableException;
import gui.GUI;
import moteurDuJeu.Engine;
import personnages.Player;
import personnages.Robot;
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
		Coordinates coordinates;
		map[engine.getPlayer(Team.ROUGE).getCoordinates().getX()][engine.getPlayer(Team.ROUGE).getCoordinates().getY()]
				.setEntity(engine.getPlayer(Team.ROUGE));
		map[engine.getPlayer(Team.BLEU).getCoordinates().getX()][engine.getPlayer(Team.BLEU).getCoordinates().getY()]
				.setEntity(engine.getPlayer(Team.BLEU));
		// Set the obstacles on the map
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				coordinates = new Coordinates(i, j);
				if (userInterface.isObstacle(coordinates)) {
					map[i][j].setEntity(new Obstacle(coordinates, this));
				}
			}
		}
		// Once obstacles are set,
		// Set random operator at random places
		int randomX = (int) (Math.random() * (width));
		int randomY = (int) (Math.random() * (height));
		Coordinates randomCoordinates = new Coordinates(randomX, randomY);
		PickAble newPickAble;
		for (int i = 0; i < nbrOperatorInit; i++) {
			while (!map[randomX][randomY].isFree()) {
				randomX = (int) (Math.random() * (width));
				randomY = (int) (Math.random() * (height));
				randomCoordinates = new Coordinates(randomX, randomY);
			}
			newPickAble = PickAble.randomPickable((int) ((int) 1 + (Math.random() * (9))), randomCoordinates, this);
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

	public void Free(Coordinates coordinates) {
		map[coordinates.getX()][coordinates.getY()].FreeCell();
	}

	public void setEntity(Coordinates coordinates, Entity entity) {
		map[coordinates.getX()][coordinates.getY()].setEntity(entity);
	}

	public Cell getCell(Coordinates coordinates) {
		return map[coordinates.getX()][coordinates.getY()];
	}

	public Cell getCell(int x, int y) {
		return map[x][y];
	}

	public boolean isFree(Coordinates coordinates) {
		return map[coordinates.getX()][coordinates.getY()].isFree();
	}

	public List<Entity> getEntity(Coordinates coordinates) {
		return map[coordinates.getX()][coordinates.getY()].getListEntity();
	}

	public List<Entity> getPickAbleList(int x, int y) {
		return map[x][y].getPickAbleList();
	}

	public List<Entity> getPickAbleList(Coordinates coordinates) {
		return map[coordinates.getX()][coordinates.getY()].getPickAbleList();
	}

	// public void print() {
	// for (int i = 0; i < width; i++) {
	// for (int j = 0; j < height; j++) {
	// if (map[i][j].getListEntity().size() != 0) {
	// System.out.println(
	// "case :" + i + ',' + j + " " +
	// map[i][j].getListEntity().get(0).getClass().toString());
	//
	// }
	// }
	// }
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
	public List<Entity> getListEntity(Coordinates coordinates) {
		return map[coordinates.getX()][coordinates.getY()].getListEntity();
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
	public Class<PickAble> pickableEntity(Coordinates coordinates) throws NotDoableException {
		List<Entity> l = map[coordinates.getX()][coordinates.getY()].getListEntity();
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
	public void freePick(Class<PickAble> ramasse, Coordinates coordinates) {
		List<Entity> l = map[coordinates.getX()][coordinates.getY()].getListEntity();
		int i = 0;
		while (i < l.size()) {
			if (l.get(i).getClass() == ramasse) {
				l.remove(i);
			}
		}

	}

	public boolean isReachable(Coordinates coordinates) {
		List<Entity> l = map[coordinates.getX()][coordinates.getY()].getListEntity();
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

	public boolean isPickAble(Coordinates coordinates) {
		List<Entity> EntityList = map[coordinates.getX()][coordinates.getY()].getListEntity();
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

	public void movePlayer(Player player, Coordinates newCoordinates) {
		this.Free(player.getCoordinates());
		this.setEntity(newCoordinates, player);

	}

	public void moveRobot(Robot robot, Coordinates newCoordinates) {
		this.Free(robot.getCoordinates());
		this.setEntity(newCoordinates, robot);

	}

}
