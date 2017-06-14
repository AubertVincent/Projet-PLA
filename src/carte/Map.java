package carte;

import java.util.ArrayList;
import java.util.List;

import entite.Entity;
import entite.Team;
import gui.GUI;
import moteurDuJeu.Engine;
import personnages.Character;
import personnages.Robot;
import pickable.PickAble;

public class Map {

	private int nbrOperatorInit = 128;
	private int width = 34;
	private int height = 18;

	public final Cell[][] map = new Cell[width][height];

	private List<PickAble> pickableList;

	private GUI userInterface;

	public Map(GUI userInterface) {
		this.pickableList = new ArrayList<PickAble>();
		this.userInterface = userInterface;
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
					this.setEntity(new Obstacle(i, j, this));
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
			this.setEntity(newPickAble);
			this.addPickAble(newPickAble);
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

	public Cell getCell(int x, int y) {
		return map[x][y];
	}

	// public List<Action> pathExists(Robot r, int xa, int ya) {
	// Cell destination = r.entityMap.getCell(xa, ya);
	// //List<Action> path = Dijkstra.dijkstra(GraphMap.,destination);
	//
	// return null;
	// }

	public void moveCharacter(Character character, int newX, int newY) {
		this.getCell(character.getX(), character.getY()).FreeCell();
		this.getCell(newX, newY).setEntity(character);
	}

	public void moveRobot(Robot player, int newX, int newY) {
		this.getCell(player.getX(), player.getY()).isFree();
		this.getCell(newX, newY).setEntity(player);
	}

	public List<PickAble> getPickAbleList() {
		return this.pickableList;
	}

	public void addPickAble(PickAble pickable) {
		pickableList.add(pickable);
	}

	public void setEntity(Entity entity) {
		this.getCell(entity.getX(), entity.getY()).setEntity(entity);
	}

	public Cell nearestFreeCell(int x, int y) {
		Cell freeCell = getCell(x, y);
		int distance = 1;
		while (!freeCell.isReachable()) {
			for (int i = distance; i >= -distance && !freeCell.isReachable(); i--) {
				if (i > 0) {
					for (int j = -Math.abs(distance - i); j <= Math.abs(distance - i) && !freeCell.isReachable(); j++) {
						if ((x + i >= 0 && x + i < mapWidth()) && (y + j >= 0 && y + j < mapHeight())) {
							if (getCell(x + i, y + j).isReachable()) {
								freeCell = getCell(x + i, y + j);
							}
						}
					}
				} else {
					for (int j = distance + i; j >= -(distance + i) && !freeCell.isReachable(); j--) {
						if ((x + i >= 0 && x + i < mapWidth()) && (y + j >= 0 && y + j < mapHeight())) {
							if (getCell(x + i, y + j).isReachable()) {
								freeCell = getCell(x + i, y + j);
							}
						}
					}
				}
			}
			distance++;
		}
		return freeCell;
	}

	public GUI getGUI() {
		return this.userInterface;
	}

	public List<PickAble> getPickAbleList(Character character) {
		// TODO Auto-generated method stub
		return this.getCell(character.getX(), character.getY()).getPickAbleList();
	}

	public void removePickAble(Entity e) {
		this.pickableList.remove(e);
	}

	public void remove(Entity entity) {
		entity.getCell().remove(entity);
	}

	public void initExploration(GUI userInterface2) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (userInterface.isObstacle(i, j)) {
					this.setEntity(new Obstacle(i, j, this));
				}
			}
		}

	}

}
