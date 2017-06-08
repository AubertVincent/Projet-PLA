package personnages;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import entite.Direction;
import entite.Team;
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

	
	public Player(int x, int y, carte.Map entityMap, Direction direction, int life, int vision, int attack, int range,
			int movePoints, int recall, Team team, Map<Class<? extends PickAble>, Integer> besace) {
		super(x, y, entityMap, direction, life, vision, attack, range, movePoints, recall, team);
		this.besace = besace;
		possibleActionsList.add(ClassicAck.class);
		possibleActionsList.add(MoveDir.class);
		possibleActionsList.add(Tunnel.class);
		possibleActionsList.add(Recall.class);
		this.besace.put(PickClassicAck.class, 0);
		this.besace.put(PickSuicideBomber.class, 0);
		this.besace.put(PickTunnel.class, 0);
		this.besace.put(PickMoveDir.class, 0);
		this.besace.put(PickRecall.class, 0);
		this.besace.put(PickPickUp.class, 0);
		this.besace.put(PickSuccession.class, 0);
		this.besace.put(PickRandomBar.class, 0);
		this.besace.put(PickPriority.class, 0);
	}


	@Override
	public boolean isPlayer() {
		return true;
	}

	@Override
	public boolean isRobot() {
		return false;
	}

	public Map<Class<? extends PickAble>, Integer> getBesace() {
		return besace;
	}

	public void setBesace(Map<Class<? extends PickAble>, Integer> besace) {
		this.besace = besace;
	}

}
