package personnages;

import java.util.LinkedList;
import java.util.List;

import carte.Base;
import entite.Direction;
import entite.Team;
import gui.GUICharacter;
import operateur.Action;
import operateur.ClassicAck;
import operateur.MoveDir;
import pickable.PickMoveDir;
import pickable.PickRecall;
import pickable.PickSuicideBomber;
import pickable.PickTunnel;

public class Player extends Character {

	public Besace besace;

	protected static List<Class<? extends Action>> possibleActionsList = new LinkedList<Class<? extends Action>>();
	static {
		possibleActionsList.add(ClassicAck.class);
		possibleActionsList.add(MoveDir.class);
		// possibleActionsList.add(Tunnel.class);
		// possibleActionsList.add(Recall.class);
	}

	private RobotList robotList;

	/**
	 * Set a new Player
	 * 
	 * @param x
	 *            x coordinate on the map
	 * @param y
	 *            y coordinate on the map
	 * @param direction
	 *            Where the character is oriented
	 * @param entityMap
	 *            The map on which the entity is located
	 * @param life
	 *            Player's life
	 * @param vision
	 *            Player's vision range
	 * @param attack
	 *            Player's attack
	 * @param range
	 *            Player's range
	 * @param movePoints
	 *            Player's move points
	 * @param recall
	 *            Player's recall's time
	 */

	public static List<Class<? extends Action>> getPossibleActionsList() {
		return possibleActionsList;
	}

	PickMoveDir move;

	public Player(int x, int y, carte.Map entityMap, Besace besace, Direction direction, int life, int vision,
			int attack, int range, int movePoints, int recall, int attackPoints, Team team, Base base,
			GUICharacter GUIPlayer) {
		super(x, y, entityMap, besace, direction, life, vision, attack, range, movePoints, recall, team, attackPoints,
				base, GUIPlayer);
		robotList = new RobotList();

		besace.initBesace();

		besace.add(PickMoveDir.class);
		besace.add(PickRecall.class);
		besace.add(PickSuicideBomber.class);
		besace.add(PickTunnel.class);

		// for (Iterator<Entry<Class<? extends PickAble>, Integer>> iterator =
		// besace.get().entrySet().iterator(); iterator
		// .hasNext();) {
		// Map.Entry<Class<? extends PickAble>, Integer> mapentry =
		// (Map.Entry<Class<? extends PickAble>, Integer>) iterator
		// .next();
		// System.out.println("clé: " + mapentry.getKey() + " | valeur: " +
		// mapentry.getValue());
		// }
	}

	public void addRobot(Object obj, Robot robot) {
		robotList.add(obj, robot);
	}

	// public void addOperator(Operator op) {
	// Integer nbr = besace.get(op);
	// if (nbr == null) {
	// besace.put(op, 1);
	// } else {
	// besace.put(op, nbr + 1);
	// }
	// }
	//
	// public void removeOperator(Operator op) {
	// Integer nbr = besace.get(op);
	// if (nbr == 1) {
	// besace.remove(op);
	// } else {
	// besace.put(op, nbr - 1);
	// }
	// }
	//
	// public boolean isInBesace(Operator op) {
	// return besace.get(op) != null;
	// }
	//
	// public int nbrInBesace(Operator op) {
	// return besace.get(op);
	// }

	public RobotList getRobotList() {
		return robotList;
	}

	// TODO
	public void createRobot() {

	}

	@Override
	public boolean isPlayer() {
		return true;
	}

	@Override
	public boolean isRobot() {
		return false;
	}

	public void setX(int x) {
		super.setX(x);
	}

	public void setY(int y) {
		super.setY(y);
	}

	public int getX() {
		return super.getX();
	}

	public int getY() {
		return super.getY();
	}

	// // Tests main
	// public static void main(String[] args) {
	// Player joueur = new Player(5, 12, Direction.NORTH, 1, 1, 1, 1, 5, 1);
	//
	// System.out.println("is player ? " + joueur.isPlayer());
	// System.out.println("is Robot ? " + joueur.isRobot());
	// joueur.addRobot(new Robot(0, 0, Direction.EAST, 1, 0, 1, 1, 1, 1));
	// joueur.addRobot(new Robot(1, 0, Direction.EAST, 1, 0, 1, 1, 1, 1));
	// joueur.addRobot(new Robot(2, 0, Direction.EAST, 1, 0, 1, 1, 1, 1));
	// joueur.addRobot(new Robot(3, 0, Direction.EAST, 1, 0, 1, 1, 1, 1));
	// System.out.println(joueur.getListRobot().size());
	// System.out.println("Position avant : " + joueur.getX() + " " +
	// joueur.getY());
	// joueur.setX(10);
	// joueur.setY(10);
	// System.out.println("Position avant : " + joueur.getX() + " " +
	// joueur.getY());
	// ClassicAck test = new ClassicAck(4,5);
	//// joueur.addOperator(test);
	//// System.out.println("Is in the besace : ? " + joueur.isInBesace(test));
	//
	//
	// }

	@Override
	public boolean isObstacle() {
		// TODO Auto-generated method stub
		return false;
	}

	public Besace getBesace() {
		return besace;
	}

	public void setBesace(Besace besace) {
		this.besace = besace;
	}

	@Override
	public boolean isPickAble() {
		// TODO Auto-generated method stub
		return false;
	}

}
