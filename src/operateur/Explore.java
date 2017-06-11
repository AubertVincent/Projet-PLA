package operateur;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import carte.Base;
import carte.Cell;
import carte.Map;
import entite.Direction;
import entite.Team;
import exceptions.NotDoableException;
import personnages.Besace;
import personnages.Player;
import personnages.Robot;
import sequence.Tree;
import sequence._Sequence;

public class Explore extends Movement {

	public Explore() {
		super();
	}

	@Override
	protected boolean isDoable(Robot r) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void cancel(Robot r) throws NotDoableException {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(Robot r) throws NotDoableException {
		int range = r.getMovePoints();
		int x = r.getX();
		int y = r.getY();
		int randomCpt = 0;
		int myRandom = 0;
		int unexploredcpt = 0;
		Cell testNorth = r.getEntityMap().getCell(x, y-1);
		Cell testSouth = r.getEntityMap().getCell(x, y+1);
		Cell testEast = r.getEntityMap().getCell(x+1, y);
		Cell testWest = r.getEntityMap().getCell(x-1, y);
		List<Cell> reachable = new ArrayList<Cell>();
		
		while (range > 0){
			reachable.clear();
			randomCpt = 0;
			if (testNorth.isReachable() && !(testNorth.isExplored())){
				reachable.add(testNorth);
				randomCpt++;
			}
			if (testEast.isReachable() && !(testEast.isExplored())){
				reachable.add(testEast);
				randomCpt++;
			}
			if (testWest.isReachable() && !(testWest.isExplored())){
				reachable.add(testWest);
				randomCpt++;
			}
			if (testSouth.isReachable() && !(testSouth.isExplored())){
				reachable.add(testSouth);
				randomCpt++;
			}
			if (randomCpt!=0){
				myRandom = (int) (Math.random()*randomCpt);
				//TODO Accéder à la cellule tirée au sort
			}else{
				randomCpt = 0;
				reachable.clear();
				if (testNorth.isReachable()){
					reachable.add(testNorth);
					randomCpt++;
				}
				if (testEast.isReachable()){
					reachable.add(testEast);
					randomCpt++;
				}
				if (testWest.isReachable()){
					reachable.add(testWest);
					randomCpt++;
				}
				if (testSouth.isReachable()){
					reachable.add(testSouth);
					randomCpt++;
				}
				myRandom = (int) (Math.random()*randomCpt);
				//TODO Accéder à la cellule tirée au sort
			}
		}
		
	}

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

	public static void main(String[] args) throws NotDoableException {
		Behavior b = new RandomBar();
		MoveDir m1 = new MoveDir(Direction.SOUTH, 1);
		MoveDir m2 = new MoveDir(Direction.EAST, 0);
		Map map = new Map();

		_Sequence sequence = new Tree(b, m1, m2);

		Player player = new Player(1, 1, map, new Besace(), Direction.EAST, 1, 1, 1, 1, 1, 1, 1, Team.BLEU,
				new Base(2, 4, Team.ROUGE));

		Robot robot = new Robot(5, 5, map, new Besace(), Direction.NORTH, 10, 3, 3, 3, 1, 3, Team.BLEU, 1,
				new Base(2, 4, Team.ROUGE), sequence, player);

		Explore e = new Explore();

		try {
			e.execute(robot);
		} catch (NotDoableException e2) {

		}
	}

}
