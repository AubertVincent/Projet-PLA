package carte;

import entite.Team;
import gui.GUI;
import moteurDuJeu.Engine;
import moteurDuJeu.Test;

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
		map[engine.getPlayer(Team.ROUGE).getX()][engine.getPlayer(Team.ROUGE).getY()]
				.setEntity(engine.getPlayer(Team.ROUGE));
		map[engine.getPlayer(Team.BLEU).getX()][engine.getPlayer(Team.BLEU).getY()]
				.setEntity(engine.getPlayer(Team.BLEU));
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (userInterface.isObstacle(i, j)) {
					map[i][j].setEntity(new Obstacle(i, j, this));
				}
			}
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

}
