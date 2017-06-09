package personnages;

import java.util.Iterator;
import java.util.List;

import carte.Base;
import carte.Map;
import entite.Direction;
import entite.Entity;
import entite.Team;
import exceptions.NotDoableException;
import operateur.Action;
import operateur.ClassicAck;
import operateur.MoveDir;
import pickable.Picked;
import sequence._Sequence;
import util.Pair;

public class Robot extends Character {


	protected _Sequence myAutomaton;
	protected Player player;
	private java.util.Map<Pair<Direction, Integer>, Pair<Robot, Integer>> targetsLife;

	public Robot(int x, int y, Map entityMap, List<Picked> myOwnBesace, Direction direction, int life, int vision,
			int attack, int range, int movePoints, int recall, Team team, int attackPoints, Base base,
			_Sequence myAutomaton, Player player,
			java.util.Map<Pair<Direction, Integer>, Pair<Robot, Integer>> targetsLife) {
		super(x, y, entityMap, myOwnBesace, direction, life, vision, attack, range, movePoints, recall, team,
				attackPoints, base);
		this.myAutomaton = myAutomaton;
		this.player = player;
		this.targetsLife = targetsLife;
	}

	static {
		possibleActionsList.add(ClassicAck.class);
		possibleActionsList.add(MoveDir.class);
		// possibleActionsList.add(Tunnel.class);
		// possibleActionsList.add(Recall.class);
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
		List<Entity> northEntityList = this.entityMap.getListEntity(x, y - 1);
		List<Entity> southEntityList = this.entityMap.getListEntity(x, y + 1);
		List<Entity> westEntityList = this.entityMap.getListEntity(x - 1, y);
		List<Entity> eastEntityList = this.entityMap.getListEntity(x + 1, y);

		// Cell testN = this.entityMap.getCell(x, y-1);

		int i = 0;
		for (Iterator<Entity> entityIterator = northEntityList.iterator(); entityIterator.hasNext();) {
			Entity eCourant = entityIterator.next();
			if (eCourant instanceof Robot) {
				this.targetsLife.put(new Pair<Direction, Integer>(Direction.NORTH, i),
						new Pair<Robot, Integer>(((Robot) eCourant), ((Robot) eCourant).getLife()));
				((Robot) eCourant).setLife(0);
				i++;
			}
		}

		i = 0;
		for (Iterator<Entity> entityIterator = southEntityList.iterator(); entityIterator.hasNext();) {
			Entity eCourant = entityIterator.next();
			if (eCourant instanceof Robot) {
				this.targetsLife.put(new Pair<Direction, Integer>(Direction.SOUTH, i),
						new Pair<Robot, Integer>(((Robot) eCourant), ((Robot) eCourant).getLife()));
				((Robot) eCourant).setLife(0);
				i++;
			}
		}

		i = 0;
		for (Iterator<Entity> entityIterator = westEntityList.iterator(); entityIterator.hasNext();) {
			Entity eCourant = entityIterator.next();
			if (eCourant instanceof Robot) {
				this.targetsLife.put(new Pair<Direction, Integer>(Direction.WEST, i),
						new Pair<Robot, Integer>(((Robot) eCourant), ((Robot) eCourant).getLife()));
				((Robot) eCourant).setLife(0);
				i++;
			}
		}

		i = 0;
		for (Iterator<Entity> entityIterator = eastEntityList.iterator(); entityIterator.hasNext();) {
			Entity eCourant = entityIterator.next();
			if (eCourant instanceof Robot) {
				this.targetsLife.put(new Pair<Direction, Integer>(Direction.EAST, i),
						new Pair<Robot, Integer>(((Robot) eCourant), ((Robot) eCourant).getLife()));
				((Robot) eCourant).setLife(0);
				i++;
			}
		}
	}

	public void cancelSuicideBomber() {
		int x = this.getX();
		int y = this.getY();
		List<Entity> northEntityList = this.entityMap.getListEntity(x, y - 1);
		List<Entity> southEntityList = this.entityMap.getListEntity(x, y + 1);
		List<Entity> westEntityList = this.entityMap.getListEntity(x - 1, y);
		List<Entity> eastEntityList = this.entityMap.getListEntity(x + 1, y);

		int i = 0;
		for (Iterator<Entity> entityIterator = northEntityList.iterator(); entityIterator.hasNext();) {
			Entity eCourant = entityIterator.next();
			if (eCourant instanceof Robot) {
				Pair<Direction, Integer> key = new Pair<Direction, Integer>(Direction.NORTH, i);
				Pair<Robot, Integer> robotLife = this.targetsLife.get(key);
				((Robot) eCourant).setLife(robotLife.getSecond());
				i++;
			}
		}

		i = 0;
		for (Iterator<Entity> entityIterator = southEntityList.iterator(); entityIterator.hasNext();) {
			Entity eCourant = entityIterator.next();
			if (eCourant instanceof Robot) {
				Pair<Direction, Integer> key = new Pair<Direction, Integer>(Direction.SOUTH, i);
				Pair<Robot, Integer> robotLife = this.targetsLife.get(key);
				((Robot) eCourant).setLife(robotLife.getSecond());
				i++;
			}
		}

		i = 0;
		for (Iterator<Entity> entityIterator = westEntityList.iterator(); entityIterator.hasNext();) {
			Entity eCourant = entityIterator.next();
			if (eCourant instanceof Robot) {
				Pair<Direction, Integer> key = new Pair<Direction, Integer>(Direction.WEST, i);
				Pair<Robot, Integer> robotLife = this.targetsLife.get(key);
				((Robot) eCourant).setLife(robotLife.getSecond());
				i++;
			}
		}

		i = 0;
		for (Iterator<Entity> entityIterator = eastEntityList.iterator(); entityIterator.hasNext();) {
			Entity eCourant = entityIterator.next();
			if (eCourant instanceof Robot) {
				Pair<Direction, Integer> key = new Pair<Direction, Integer>(Direction.EAST, i);
				Pair<Robot, Integer> robotLife = this.targetsLife.get(key);
				((Robot) eCourant).setLife(robotLife.getSecond());
				i++;
			}
		}
	}

	public void execute() throws NotDoableException {
		myAutomaton.execute(this);
	}

}
