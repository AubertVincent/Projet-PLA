package personnages;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import carte.Map;
import entite.Direction;
import entite.Entity;
import entite.Team;
import exceptions.NotDoableException;
import operateur.Action;
import sequence._Sequence;

public class Robot extends Character {

	protected static List<Class<? extends Action>> possibleActionsList = new LinkedList<Class<? extends Action>>();

	_Sequence myAutomata;

	/**
	 * Set a new Robot
	 * 
	 * @param x
	 *            x coordinate on the map
	 * @param y
	 *            y coordinate on the map
	 * @param entityMap
	 *            The map on which the entity is located
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

	public static List<Class<? extends Action>> getPossibleActionsList() {
		return possibleActionsList;
	}

	public Robot(int x, int y, Map entityMap, Direction direction, int life, int vision, int attack, int range,
			int movePoints, int recall, int aP, Team team) {
		super(x, y, entityMap, direction, life, vision, attack, range, movePoints, recall, aP, team);
	}

	@Override
	public boolean isPlayer() {
		return false;
	}

	@Override
	public boolean isRobot() {
		return true;
	}

	@Override
	public boolean isObstacle() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Suicide a Robot and kill the Robots next to it
	 * 
	 * @param e
	 *            The Robot which is suiciding
	 */
	public Player isToPlayer() {
		// TODO
		return null;

	}

	/**
	 * Suicide a robot and kill the robots around it
	 */
	public void suicideBomber() {
		int x = this.getX();
		int y = this.getY();
		List<Entity> testNorth = this.entityMap.getListEntity(x, y - 1);
		List<Entity> testSouth = this.entityMap.getListEntity(x, y + 1);
		List<Entity> testWest = this.entityMap.getListEntity(x - 1, y);
		List<Entity> testEast = this.entityMap.getListEntity(x + 1, y);

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

	public void execute() throws NotDoableException {
		myAutomata.execute(this);
	}

	@Override
	public boolean isPickAble() {
		// TODO Auto-generated method stub
		return false;
	}

}
