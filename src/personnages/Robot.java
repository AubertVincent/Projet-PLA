package personnages;

import java.util.Iterator;
import java.util.List;

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

	public Robot(int x, int y, Map entityMap, GUI userInterface, _Sequence myAutomaton, Player player) {
		super(x, y, entityMap, player.getBase());

		this.myAutomaton = myAutomaton;
		this.mySelfGUI = new GUIRobot(userInterface, x, y, Direction.SOUTH, 100, base.getBaseTeam(), this,
				player.getMyselfGUI());
		this.player = player;
		this.player.addRobot(new Coordinates(x, y), this);
		entityMap.setEntity(this);
		this.explorationMap = new Map(userInterface);
		this.explorationMap.initExploration(userInterface);
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

	@Override
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
		List<Entity> northEntityList = this.map.getCell(x, y - 1).getEntityList();
		List<Entity> southEntityList = this.map.getCell(x, y + 1).getEntityList();
		List<Entity> westEntityList = this.map.getCell(x - 1, y).getEntityList();
		List<Entity> eastEntityList = this.map.getCell(x + 1, y).getEntityList();

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
		List<Entity> northEntityList = this.map.getCell(x, y - 1).getEntityList();
		List<Entity> southEntityList = this.map.getCell(x, y + 1).getEntityList();
		List<Entity> westEntityList = this.map.getCell(x - 1, y).getEntityList();
		List<Entity> eastEntityList = this.map.getCell(x + 1, y).getEntityList();

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

	public void die() {

		player.removeFromRobotList(this);

		this.mySelfGUI.setMySelf(null);
		this.mySelfGUI = null;

		// Remove every occurrence of current robot in targetsLife
		for (Iterator<Pair<Direction, Integer>> iterator = targetsLife.keySet().iterator(); iterator.hasNext();) {
			Pair<Direction, Integer> currentPair = iterator.next();
			Robot currentRobot = targetsLife.get(currentPair).getFirst();
			if (currentRobot.equals(this)) {
				targetsLife.remove(currentPair);
			}
		}

		this.getMap().remove(this);
	}
}
