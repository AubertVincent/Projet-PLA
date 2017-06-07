package personnages;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import carte.Map;
import entite.Direction;
import entite.Entity;
import operateur.*;

public class Robot extends Character {

	protected static List<Class<? extends Action>> possibleActionsList = new LinkedList<Class<? extends Action>>();

	/**
	 * Set a new Robot
	 * 
	 * @param x
	 *            x coordinate on the map
	 * @param y
	 *            y coordinate on the map
	 * @param direction
	 *            Where the character is oriented
	 * @param life
	 *            Robot's life
	 * @param vision
	 *            Robot's vision range
	 * @param attack
	 *            Robot's attack
	 * @param range
	 *            Robot's range
	 * @param movePoints
	 *            Robot's move points
	 * @param recall
	 *            Robot's recall's time
	 */
	public Robot(int x, int y, Direction direction, int life, int vision, int attack, int range, int movePoints,
			int recall) {
		super(x, y, direction, life, vision, attack, range, movePoints, recall);
		possibleActionsList.add(ClassicAck.class);
		possibleActionsList.add(MoveDir.class);
		possibleActionsList.add(Tunnel.class);
		possibleActionsList.add(Recall.class);
		possibleActionsList.add(SuicideBomber.class);
	}

	public static List<Class<? extends Action>> getPossibleActionsList() {
		return possibleActionsList;
	}

	@Override
	public boolean isPlayer() {
		return false;
	}

	@Override
	public boolean isRobot() {
		return true;
	}

	/**
	 * Suicide a Robot and kill the Robots next to it
	 * 
	 * @param e
	 *            The Robot which is suiciding
	 */
	public Player isToPlayer(){
		//TODO
		return null;
		
	}
	
	public void suicideBomber(Entity e) {
		int x = e.getX();
		int y = e.getY();
		List<Entity> testNorth = Map.getListEntity(x, y - 1);
		List<Entity> testSouth = Map.getListEntity(x, y + 1);
		List<Entity> testWest = Map.getListEntity(x - 1, y);
		List<Entity> testEast = Map.getListEntity(x + 1, y);

		for (Iterator<Entity> i = testNorth.iterator(); i.hasNext();) {
			Entity eCourant = i.next();
			if (eCourant instanceof Robot) {
				((Robot) eCourant).setLife(0);
			}
		}
		for (Iterator<Entity> i = testSouth.iterator(); i.hasNext();) {
			Entity eCourant = i.next();
			if (eCourant instanceof Robot) {
				((Robot) eCourant).setLife(0);
			}
		}
		for (Iterator<Entity> i = testWest.iterator(); i.hasNext();) {
			Entity eCourant = i.next();
			if (eCourant instanceof Robot) {
				((Robot) eCourant).setLife(0);
			}
		}
		for (Iterator<Entity> i = testEast.iterator(); i.hasNext();) {
			Entity eCourant = i.next();
			if (eCourant instanceof Robot) {
				((Robot) eCourant).setLife(0);
			}
		}
	}
}
