package carte;

import java.util.List;

import entite.Direction;
import entite.Entity;
import entite.Team;
import exceptions.GameException;
import exceptions.NotDoableException;
import gui.GUI;
import operateur.Action;
import personnages.Player;
import personnages.Robot;
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

	public void initMap(GUI userInterface) {
		map[2][4].setEntity(new Player(2, 4, this, Direction.NORTH, 1, 1, 1, 1, 5, 1, 1, Team.ROUGE));
		map[31][15].setEntity(new Player(31, 15, this, Direction.NORTH, 1, 1, 1, 1, 5, 1, 1, Team.BLEU));
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

	public Cell getCell(int x, int y) {
		return map[x][y];
	}

	public List<Action> pathExists(Robot r, int xa, int ya) {
		Cell destination = r.entityMap.getCell(xa, ya);
		//List<Action> path = Dijkstra.dijkstra(GraphMap.,destination);
		
		return null;
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

}
