package carte;

import java.util.ArrayList;
import java.util.List;

import entite.Team;
import gui.GUI;
import moteurDuJeu.Engine;
import moteurDuJeu.Test;
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

	// Used for test delete when it's over
	public void init(Test test) {
		map[5][10].setEntity(test.getRobot(Team.ROUGE));
		map[5][11].setEntity(test.getRobot(Team.BLEU));
		map[5][12].setEntity(new Obstacle(5, 5, this));
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
		this.getCell(character.getX(), character.getY()).isFree();
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

	// TODO find cell
	public static void nearestFreeCell(int x, int y) {
		// Cell freeCell = getCell(x, y);
		int distance = 3;
		for (int i = distance; i >= -distance; i--) {
			if (i > 0) {
				for (int j = -Math.abs(distance - i); j <= Math.abs(distance - i); j++) {
					System.out.println("Je vise la case : " + i + " ;" + j);
				}
			} else {
				for (int j = distance + i; j >= -(distance + i); j--) {
					System.out.println("Je vise la case : " + i + " ;" + j);
				}
			}
		}
		// return freeCell;
	}

	public GUI getGUI() {
		return this.userInterface;
	}

}
