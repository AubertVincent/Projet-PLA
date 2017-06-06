package personnages;

import java.util.LinkedList;
import java.util.List;

import entite.Direction;
import operateur.*;

public class Player extends Character {

	protected static List<Class<? extends Action>> possibleActionsList = new LinkedList<Class<? extends Action>>();

	/**
	 * Set a new Player
	 * 
	 * @param x
	 *            x coordinate on the map
	 * @param y
	 *            y coordinate on the map
	 * @param direction
	 *            Where the character is oriented
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
	public Player(int x, int y, Direction direction, int life, int vision, int attack, int range, int movePoints,
			int recall) {
		super(x, y, direction, life, vision, attack, range, movePoints, recall);
		possibleActionsList.add(ClassicAck.class);
		possibleActionsList.add(MoveDir.class);
		possibleActionsList.add(Tunnel.class);
		possibleActionsList.add(Recall.class);
	}

	public static List<Class<? extends Action>> getPossibleActionsList() {
		return possibleActionsList;
	}

	@Override
	public boolean isPlayer() {
		return true;
	}

	@Override
	public boolean isRobot() {
		return false;
	}

	// TODO Keyboard reaction

}
