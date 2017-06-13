package operateur;

import java.util.ArrayList;
import java.util.List;

import carte.Cell;
import carte.Map;
import entite.Direction;
import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import pickable.PickExplore;

public class Explore extends Movement {

	public Explore() {
		super();
	}

	@Override
	protected boolean isDoable(Robot r) {

		// We have to check if there is at least one reachable cell around this
		// robot
		// (North, South, Est, West)
		int x = r.getX();
		int y = r.getY();
		Map myMap = r.getEntityMap();

		if (x < r.getEntityMap().mapWidth() && myMap.getCell(x + 1, y).isReachable()) {
			return true;
		}
		if (x > 0 && myMap.getCell(x - 1, y).isReachable()) {
			return true;
		}
		if (y > 0 && myMap.getCell(x, y - 1).isReachable()) {
			return true;
		}
		if (y < r.getEntityMap().mapHeight() && myMap.getCell(x, y + 1).isReachable()) {
			return true;
		}
		return false;
	}

	// int x = r.getX();
	// int y = r.getY();
	// Map myMap = r.getEntityMap();
	// boolean isInCorner = ((x == 0 && y == 0) || (x == 0 && y ==
	// myMap.mapHeight() - 1)
	// || (x == myMap.mapWidth() - 1 && y == 0) || (x == myMap.mapWidth() - 1 &&
	// y == myMap.mapHeight() - 1));
	// if (x == 0 && y == 0 && !myMap.getCell(x, y + 1).isReachable() &&
	// !myMap.getCell(x + 1, y).isReachable()) {
	// return false;
	// } else if (x == myMap.mapWidth() - 1 && y == 0 && !myMap.getCell(x, y +
	// 1).isReachable()
	// && !myMap.getCell(x - 1, y).isReachable()) {
	// return false;
	// } else if (x == myMap.mapWidth() - 1 && y == myMap.mapHeight() - 1 &&
	// !myMap.getCell(x, y - 1).isReachable()
	// && !myMap.getCell(x - 1, y).isReachable()) {
	// return false;
	// } else if (x == 0 && y == myMap.mapHeight() - 1 && !myMap.getCell(x, y -
	// 1).isReachable()
	// && !myMap.getCell(x + 1, y).isReachable()) {
	// return false;
	// } else if (!isInCorner && x == 0 && !myMap.getCell(x, y -
	// 1).isReachable()
	// && !myMap.getCell(x, y + 1).isReachable() && !myMap.getCell(x + 1,
	// y).isReachable()) {
	// return false;
	// } else if (!isInCorner && y == 0 && !myMap.getCell(x + 1,
	// y).isReachable()
	// && !myMap.getCell(x, y + 1).isReachable() && !myMap.getCell(x - 1,
	// y).isReachable()) {
	// return false;
	// } else if (!isInCorner && y == myMap.mapHeight() - 1 && !myMap.getCell(x
	// + 1, y).isReachable()
	// && !myMap.getCell(x, y - 1).isReachable() && !myMap.getCell(x - 1,
	// y).isReachable()) {
	// return false;
	// } else if (!isInCorner && x == myMap.mapWidth() - 1 && !myMap.getCell(x,
	// y - 1).isReachable()
	// && !myMap.getCell(x, y + 1).isReachable() && !myMap.getCell(x - 1,
	// y).isReachable()) {
	// return false;
	// } else {
	// return true;
	// }
	// }

	// if (!(r.getEntityMap().getCell(r.getX() + 1, r.getY()).isReachable())
	// && !(r.getEntityMap().getCell(r.getX() - 1, r.getY()).isReachable())
	// && !(r.getEntityMap().getCell(r.getX(), r.getY() + 1).isReachable())
	// && !(r.getEntityMap().getCell(r.getX(), r.getY() - 1).isReachable())) {
	// return false;
	// }
	// return true;
	// }

	@Override
	public void cancel(Robot r) throws NotDoableException {
		// Useless with the current implementation

	}

	@Override
	public void execute(Robot r) throws NotDoableException {
		// test
		// System.out.println("J'execute le tunnel !");
		// end test
		if (!isDoable(r)) {
			throw new NotDoableException("Ce robot est entouré d'obstacles");
		}
		int range = r.getMovePoints();
		int x;
		int y;
		int randomCpt = 0;
		int myRandom = 0;
		List<Cell> reachable = new ArrayList<Cell>();

		while (range > 0) {
			x = r.getX();
			y = r.getY();
			if (!isDoable(r)) {
				throw new NotDoableException("Ce robot est entouré d'obstacles");
			}
			reachable.clear();
			randomCpt = 0;
			if (y - 1 >= 0 && r.getEntityMap().getCell(x, y - 1).isReachable()
					&& !(r.getEntityMap().getCell(x, y - 1).isExplored())) {
				reachable.add(r.getEntityMap().getCell(x, y - 1));
				randomCpt++;
			}
			if (x + 1 < r.getEntityMap().mapWidth() && r.getEntityMap().getCell(x + 1, y).isReachable()
					&& !(r.getEntityMap().getCell(x + 1, y).isExplored())) {
				reachable.add(r.getEntityMap().getCell(x + 1, y));
				randomCpt++;
			}
			if (x - 1 >= 0 && r.getEntityMap().getCell(x - 1, y).isReachable()
					&& !(r.getEntityMap().getCell(x - 1, y).isExplored())) {
				reachable.add(r.getEntityMap().getCell(x - 1, y));
				randomCpt++;
			}
			if (y + 1 < r.getEntityMap().mapHeight() && r.getEntityMap().getCell(x, y + 1).isReachable()
					&& !(r.getEntityMap().getCell(x, y + 1).isExplored())) {
				reachable.add(r.getEntityMap().getCell(x, y + 1));
				randomCpt++;
			}
			if (randomCpt != 0) {
				myRandom = (int) (Math.random() * randomCpt);
				if (reachable.get(myRandom).getX() < x && reachable.get(myRandom).getY() == y) {
					r.getExplorationMap().getCell(x - 1, y).setExplored(true);
					r.goTo(Direction.WEST, 1);
				} else if (reachable.get(myRandom).getX() > x && reachable.get(myRandom).getY() == y) {
					r.getExplorationMap().getCell(x + 1, y).setExplored(true);
					r.goTo(Direction.EAST, 1);
				} else if (reachable.get(myRandom).getX() == x && reachable.get(myRandom).getY() < y) {
					r.getExplorationMap().getCell(x, y - 1).setExplored(true);
					r.goTo(Direction.NORTH, 1);
				} else if (reachable.get(myRandom).getX() == x && reachable.get(myRandom).getY() > y) {
					r.getExplorationMap().getCell(x, y + 1).setExplored(true);
					r.goTo(Direction.SOUTH, 1);
				}
			} else {
				randomCpt = 0;
				reachable.clear();
				if (y - 1 >= 0 && r.getEntityMap().getCell(x, y - 1).isReachable()) {
					reachable.add(r.getEntityMap().getCell(x, y - 1));
					randomCpt++;
				}
				if (x + 1 < r.getEntityMap().mapWidth() && r.getEntityMap().getCell(x + 1, y).isReachable()) {
					reachable.add(r.getEntityMap().getCell(x + 1, y));
					randomCpt++;
				}
				if (x - 1 >= 0 && r.getEntityMap().getCell(x - 1, y).isReachable()) {
					reachable.add(r.getEntityMap().getCell(x - 1, y));
					randomCpt++;
				}
				if (y + 1 < r.getEntityMap().mapHeight() && r.getEntityMap().getCell(x, y + 1).isReachable()) {
					reachable.add(r.getEntityMap().getCell(x, y + 1));
					randomCpt++;
				}
				myRandom = (int) (Math.random() * randomCpt);
				if (reachable.get(myRandom).getX() < x && reachable.get(myRandom).getY() == y) {
					r.getExplorationMap().getCell(x - 1, y).setExplored(true);
					r.goTo(Direction.WEST, 1);
				} else if (reachable.get(myRandom).getX() > x && reachable.get(myRandom).getY() == y) {
					r.getExplorationMap().getCell(x + 1, y).setExplored(true);
					r.goTo(Direction.EAST, 1);
				} else if (reachable.get(myRandom).getX() == x && reachable.get(myRandom).getY() < y) {
					r.getExplorationMap().getCell(x, y - 1).setExplored(true);
					r.goTo(Direction.NORTH, 1);
				} else if (reachable.get(myRandom).getX() == x && reachable.get(myRandom).getY() > y) {
					r.getExplorationMap().getCell(x, y + 1).setExplored(true);
					r.goTo(Direction.SOUTH, 1);
				}
			}
			range--;
		}

	}

	@Override
	public Class<? extends PickAble> getPickable() {
		return PickExplore.class;
	}

	// Future implementation of Dijkstra

	// @Override
	// public void execute(Robot r) throws NotDoableException {
	// int range = r.getMovePoints();
	// int x = r.getX();
	// int y = r.getY();
	//
	// int c = -range;
	// int l = 0;
	//
	// boolean unexploredCell = false;
	// boolean half = false;
	//
	// List<Action> wayToPickAble = null;
	// List<Action> wayToUnexplored = null;
	//
	// while (c <= range) {
	// for (int i = -l; i <= l; i++) {
	// // System.out.println("je vérifie la case de coordonnées" +
	// // ((Integer) (x + i)).toString()
	// // + ((Integer) (y + c)).toString());
	//
	// // If there is a pickAble on the current cell
	// if (r.getEntityMap().getCell(x + i, y + c).pickAbleHere()) {
	// // WayToPickAble is the actionList to perform to pick it
	// wayToPickAble = r.getEntityMap().pathExists(r, x + i, y + c);
	// // Execute the actionList
	// if (wayToPickAble != null) {
	// for (Iterator<Action> actionIterator = wayToPickAble.iterator();
	// actionIterator.hasNext();) {
	// Action currentAction = actionIterator.next();
	// currentAction.execute(r);
	// }
	// return;
	// }
	// }
	// // if the current cell is unexplored
	// else if (!(r.getExplorationMap().getCell(x + i, y + c).isExplored())) {
	// // WayToUnexplored is the actionList to perform to explore
	// // it
	// wayToUnexplored = r.getEntityMap().pathExists(r, x + i, y + c);
	// if (wayToUnexplored != null) {
	// unexploredCell = true;
	// }
	// }
	//
	// }
	// c++;
	// // This block is used to set the column width
	// if (l == range) {
	// half = true;
	// }
	// if (half) {
	// l--;
	// } else {
	// l++;
	// }
	// }
	// // If a path to an unexplored cell exists, reach it
	// if (unexploredCell != false) {
	// for (Iterator<Action> actionIterator = wayToUnexplored.iterator();
	// actionIterator.hasNext();) {
	// Action currentAction = actionIterator.next();
	// currentAction.execute(r);
	// }
	// } else {
	// // Else execute randomMove
	// Action a = new RandomMove();
	// a.execute(r);
	// }
	// }

	// public static void main(String[] args) throws NotDoableException {
	// Behavior b = new RandomBar();
	// MoveDir m1 = new MoveDir(Direction.SOUTH, 1);
	// MoveDir m2 = new MoveDir(Direction.EAST, 0);
	// Map map = new Map();
	//
	// _Sequence sequence = new Tree(b, m1, m2);
	//
	// Player player = new Player(1, 1, map, new Besace(), Direction.EAST, 1, 1,
	// 1, 1, 1, 1, 1, Team.BLEU,
	// new Base(2, 4, Team.ROUGE));
	//
	// Robot robot = new Robot(5, 5, map, new Besace(), Direction.NORTH, 10, 3,
	// 3, 3, 1, 3, Team.BLEU, 1,
	// new Base(2, 4, Team.ROUGE), sequence, player);
	//
	// Explore e = new Explore();
	//
	// try {
	// e.execute(robot);
	// } catch (NotDoableException e2) {
	//
	// }
	// }

}
