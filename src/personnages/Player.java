package personnages;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import entite.Direction;
import operateur.*;
import pickable.*;

public class Player extends Character {

	protected static List<Class<? extends Action>> possibleActionsList = new LinkedList<Class<? extends Action>>();

	public Map<Class<? extends PickAble>, Integer> besace = new HashMap<Class<? extends PickAble>, Integer>();

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
		besace.put(PickClassicAck.class,0);
		besace.put(PickSuicideBomber.class,0);
		besace.put(PickTunnel.class, 0);
		besace.put(PickMoveDir.class,0);
		besace.put(PickRecall.class,0);
		besace.put(PickPickUp.class,0);
		besace.put(PickSuccession.class,0);
		besace.put(PickRandomBar.class,0);
		besace.put(PickPriority.class,0);
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
