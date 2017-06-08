package personnages;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import carte.Base;
import carte.Map;
import entite.Direction;
import entite.Entity;
import entite.Team;
import exceptions.NotDoableException;
import operateur.Action;
import sequence._Sequence;

public class Robot extends Character {

	protected static List<Class<? extends Action>> possibleActionsList = new LinkedList<Class<? extends Action>>();

	public _Sequence myAutomaton;
	public Player player;
	public Base base;

	public Robot(int x, int y, Map entityMap, Direction direction, int life, int vision, int attack, int range,
			int movePoints, int recall, int aP, Team team, _Sequence myAutomaton, Player player, Base base) {
		super(x, y, entityMap, direction, life, vision, attack, range, movePoints, recall, aP, team);

		this.myAutomaton = myAutomaton;
		this.player = player;
		this.base = base;
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

	@Override
	public boolean isObstacle() {
		return false;
	}

	public Player getPlayer() {
		return player;
	}

	/**
	 * Suicide a Robot and kill the Robots next to it
	 * 
	 * @param e
	 *            The Robot which is suiciding
	 */

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

		// Cell testN = this.entityMap.getCell(x, y-1);

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

	public void cancelSuicideBomber() {

	}

	public void execute() throws NotDoableException {
		myAutomaton.execute(this);
	}

}
