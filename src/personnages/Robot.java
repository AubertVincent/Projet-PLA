package personnages;

import java.util.Iterator;
import java.util.List;

import carte.Base;
import carte.Coordinates;
import carte.Map;
import entite.Direction;
import entite.Entity;
import exceptions.NotDoableException;
import gui.GUI;
import gui.GUIRobot;
import sequence._Sequence;
import util.Pair;

public class Robot extends Character {

	protected _Sequence myAutomaton;
	private java.util.Map<Pair<Direction, Integer>, Pair<Robot, Integer>> targetsLife;
	private GUIRobot mySelfGUI;

	private Player player;
	private Map explorationMap;

	static {
		// Move-like animations
		possibleActionsList.add(operateur.MoveDir.class);
		possibleActionsList.add(operateur.RandomMove.class);

		// Teleport-like animations
		possibleActionsList.add(operateur.Recall.class);
		possibleActionsList.add(operateur.Tunnel.class);
		possibleActionsList.add(operateur.SuicideBomber.class);

		// ClassicAttack-like animations
		possibleActionsList.add(operateur.ClassicAck.class);
	}

	public Robot(Base base, Map entityMap, GUI userInterface, _Sequence myAutomaton, Player player) {
		super(base.getX(), base.getY(), entityMap, base);

		this.myAutomaton = myAutomaton;
		this.mySelfGUI = new GUIRobot(userInterface, getX(), getY(), Direction.SOUTH, 100, base.getBaseTeam(), this,
				player.getMyselfGUI());
		this.player = player;
		this.player.addRobot(new Coordinates(base.getX(), base.getY()), this);
	}

	public static List<Class<?>> getPossibleActionsList() {
		return possibleActionsList;
	}

	public Map getExplorationMap() {
		return explorationMap;
	}

	public void setExplorationMap(Map explorationMap) {
		this.explorationMap = explorationMap;
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

	public _Sequence getAutomaton() {
		return this.myAutomaton;
	}

	public void setAutomaton(_Sequence automaton) {
		this.myAutomaton = automaton;
	}

	public GUIRobot getMyselfGUI() {
		return this.mySelfGUI;
	}

	public Player getPlayer() {
		return this.player;
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
		List<Entity> northEntityList = this.entityMap.getCell(x, y - 1).getListEntity();
		List<Entity> southEntityList = this.entityMap.getCell(x, y + 1).getListEntity();
		List<Entity> westEntityList = this.entityMap.getCell(x - 1, y).getListEntity();
		List<Entity> eastEntityList = this.entityMap.getCell(x + 1, y).getListEntity();

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
		List<Entity> northEntityList = this.entityMap.getCell(x, y - 1).getListEntity();
		List<Entity> southEntityList = this.entityMap.getCell(x, y + 1).getListEntity();
		List<Entity> westEntityList = this.entityMap.getCell(x - 1, y).getListEntity();
		List<Entity> eastEntityList = this.entityMap.getCell(x + 1, y).getListEntity();

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
