package carte;

import personnages.Robot;
import java.util.List;

import entite.Direction;
import entite.Entity;
import gui.GUI;
import personnages.Player;

public class Map {

	//private static final int nbrOpInit = 128; // une chance sur 4 de trouver un
												// opérateur sur une cellule
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

	public void initMap(GUI guy) {
		map[2][4].setEntity(new Player(2, 4, Direction.NORTH, 1, 1, 1, 1, 5, 1));
		map[31][15].setEntity(new Player(31, 15, Direction.NORTH, 1, 1, 1, 1, 5, 1));
		// Test Création de robot
		map[10][10].setEntity(new Robot(10, 10, Direction.NORTH, 1, 1, 1, 1, 5, 1));
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (guy.isObstacle(GUI.cellToPixelX(i), GUI.cellToPixelY(j))) {
					map[i][j].setEntity(new Obstacle(i, j));
				//	System.out.println("Cette case contient un obstacle : " + i + ";" + j);
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

	public void Add(int x, int y, Entity ent) {
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

//	private void printMap() {
//		for (int i = 0; i < width; i++) {
//			for (int j = 0; j < height; j++) {
//				System.out.println("case :" + i + ',' + j + " " + map[i][j].isFree());
//			}
//		}
//	}

	// public static void main(String[] args) {
	// Map ma_map = new Map();
	// Map.initMap();
	// Map.printMap();
	//
	// }

}
