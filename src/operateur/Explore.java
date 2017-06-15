package operateur;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import carte.Cell;
import carte.Map;
import entite.Direction;
import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import pickable.PickExplore;

public class Explore extends Movement {

	// ↓ Constructor, update and render ↓

	public Explore() {
		super();
	}

	// End(Constructor, update and render)

	// ↓ Miscellaneous methods ↓
	@Override
	public String toString() {
		return "E";
	}

	@Override
	protected boolean isDoable(Robot r) {

		// We have to check if there is at least one reachable cell around this
		// robot
		// (North, South, Est, West)
		int x = r.getX();
		int y = r.getY();
		Map myMap = r.getMap();

		if (x < r.getMap().mapWidth() && myMap.getCell(x + 1, y).isReachable()) {
			return true;
		}
		if (x > 0 && myMap.getCell(x - 1, y).isReachable()) {
			return true;
		}
		if (y > 0 && myMap.getCell(x, y - 1).isReachable()) {
			return true;
		}
		if (y < r.getMap().mapHeight() && myMap.getCell(x, y + 1).isReachable()) {
			return true;
		}
		return false;
	}

	@Override
	public void execute(Robot r) throws NotDoableException {
		// test
		// System.out.println("J'execute le tunnel !");
		// end test
		if (!isDoable(r)) {
			throw new NotDoableException("This robot is surround by obstacle");
		}
		int movepoints = r.getMovePoints();
		int x;
		int y;
		int randomCpt = 0;
		int myRandom = 0;
		int priorityCellToGo = 0;
		Map execMap = r.getExplorationMap();
		boolean isExplored = false;

		// List of the reachable cells
		List<Cell> reachable = new ArrayList<Cell>();
		// Execution of this function is made step by step and depends of number
		// of move points. We have to check if this action is doable on each
		// iteration
		x = r.getX();
		y = r.getY();
		while (movepoints > 0) {
			priorityCellToGo = 0;
			if (!isDoable(r)) {
				throw new NotDoableException("This robot is surround by obstacle");
			}
			reachable.clear();
			// Used to get a random cell in the previous list
			randomCpt = 0;
			// Fill the previous list with the unexplored cells which are
			// reachable
			if (y - 1 >= 0 && r.getMap().getCell(x, y - 1).isReachable()
					&& !(r.getMap().getCell(x, y - 1).isExplored())) {
				reachable.add(r.getMap().getCell(x, y - 1));
				randomCpt++;
			}
			if (x + 1 < r.getMap().mapWidth() && r.getMap().getCell(x + 1, y).isReachable()
					&& (r.getMap().getCell(x + 1, y).isExplored() == false)) {
				reachable.add(r.getMap().getCell(x + 1, y));
				randomCpt++;
			}
			if (x - 1 >= 0 && r.getMap().getCell(x - 1, y).isReachable()
					&& !(r.getMap().getCell(x - 1, y).isExplored())) {
				reachable.add(r.getMap().getCell(x - 1, y));
				randomCpt++;
			}
			if (y + 1 < r.getMap().mapHeight() && r.getMap().getCell(x, y + 1).isReachable()
					&& !(r.getMap().getCell(x, y + 1).isExplored())) {
				reachable.add(r.getMap().getCell(x, y + 1));
				randomCpt++;
			}
			// If any reachable cell is unexplored
			if (randomCpt != 0) {
				// Go to a random cell of the previous list
				myRandom = (int) (Math.random() * randomCpt);
				// Except if there is an pickable around this robot
				for (Iterator<Cell> iterator = reachable.iterator(); iterator.hasNext();) {
					Cell currentCell = iterator.next();
					if (!(execMap.getCell(currentCell.getX(), currentCell.getY()).isExplored())
							&& currentCell.pickAbleHere()) {
						myRandom = priorityCellToGo;
					}
					priorityCellToGo++;
				}
				if (reachable.get(myRandom).getX() < x && reachable.get(myRandom).getY() == y) {
					r.getExplorationMap().getCell(x - 1, y).setExplored(true);
					r.addActionToActionList(new MoveDir(Direction.WEST, 1));
					x -= 1;
					execMap.getCell(x, y).setExplored(true);
				} else if (reachable.get(myRandom).getX() > x && reachable.get(myRandom).getY() == y) {
					r.getExplorationMap().getCell(x + 1, y).setExplored(true);
					r.addActionToActionList(new MoveDir(Direction.EAST, 1));
					x += 1;
					execMap.getCell(x, y).setExplored(true);
				} else if (reachable.get(myRandom).getX() == x && reachable.get(myRandom).getY() < y) {
					r.getExplorationMap().getCell(x, y - 1).setExplored(true);
					r.addActionToActionList(new MoveDir(Direction.NORTH, 1));
					y -= 1;
					execMap.getCell(x, y).setExplored(true);
				} else if (reachable.get(myRandom).getX() == x && reachable.get(myRandom).getY() > y) {
					r.getExplorationMap().getCell(x, y + 1).setExplored(true);
					r.addActionToActionList(new MoveDir(Direction.SOUTH, 1));
					y += 1;
					execMap.getCell(x, y).setExplored(true);
				}
				// Else, fill the list with the reachable cells
			} else {
				randomCpt = 0;
				reachable.clear();
				if (y - 1 >= 0 && r.getMap().getCell(x, y - 1).isReachable()) {
					reachable.add(r.getMap().getCell(x, y - 1));
					randomCpt++;
				}
				if (x + 1 < r.getMap().mapWidth() && r.getMap().getCell(x + 1, y).isReachable()) {
					reachable.add(r.getMap().getCell(x + 1, y));
					randomCpt++;
				}
				if (x - 1 >= 0 && r.getMap().getCell(x - 1, y).isReachable()) {
					reachable.add(r.getMap().getCell(x - 1, y));
					randomCpt++;
				}
				if (y + 1 < r.getMap().mapHeight() && r.getMap().getCell(x, y + 1).isReachable()) {
					reachable.add(r.getMap().getCell(x, y + 1));
					randomCpt++;
				}
				myRandom = (int) (Math.random() * randomCpt);

				// Go to a random cell of the previous list
				// Except if there is a pickable around this robot
				for (Iterator<Cell> iterator = reachable.iterator(); iterator.hasNext();) {
					Cell currentCell = iterator.next();
					if (!(execMap.getCell(currentCell.getX(), currentCell.getY()).isExplored())
							&& currentCell.pickAbleHere()) {
						myRandom = priorityCellToGo;
					}
					priorityCellToGo++;
				}
				if (reachable.get(myRandom).getX() < x && reachable.get(myRandom).getY() == y) {
					r.getExplorationMap().getCell(x - 1, y).setExplored(true);
					r.addActionToActionList(new MoveDir(Direction.WEST, 1));
					x -= 1;
					execMap.getCell(x, y).setExplored(true);
				} else if (reachable.get(myRandom).getX() > x && reachable.get(myRandom).getY() == y) {
					r.getExplorationMap().getCell(x + 1, y).setExplored(true);
					r.addActionToActionList(new MoveDir(Direction.EAST, 1));
					x += 1;
					execMap.getCell(x, y).setExplored(true);
				} else if (reachable.get(myRandom).getX() == x && reachable.get(myRandom).getY() < y) {
					r.getExplorationMap().getCell(x, y - 1).setExplored(true);
					r.addActionToActionList(new MoveDir(Direction.NORTH, 1));
					y -= 1;
					execMap.getCell(x, y).setExplored(true);
				} else if (reachable.get(myRandom).getX() == x && reachable.get(myRandom).getY() > y) {
					r.getExplorationMap().getCell(x, y + 1).setExplored(true);
					r.addActionToActionList(new MoveDir(Direction.SOUTH, 1));
					y += 1;
					execMap.getCell(x, y).setExplored(true);
				}
			}
			// Use a move point
			movepoints--;
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
	// int x = r.getX() ;
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

	// End(Miscellaneous methods)

}
