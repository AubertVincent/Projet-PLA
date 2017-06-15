package personnages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import carte.Base;
import carte.Cell;
import carte.Coordinates;
import carte.Map;
import entite.Direction;
import entite.Entity;
import exceptions.NotDoableException;
import gui.GUI;
import gui.GUIRobot;
import operateur.Action;
import sequence._Sequence;
import util.Pair;

public class Robot extends Character {

	protected _Sequence myAutomaton;
	private java.util.Map<Pair<Direction, Integer>, Pair<Robot, Integer>> targetsLife;
	private GUIRobot mySelfGUI;

	private Player player;
	private Map explorationMap;

	private boolean isVisible;

	private List<Action> automatonInList;

	private int currentAction;

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
		this.isVisible = true;
		this.automatonInList = new ArrayList<Action>();
		try {
			this.fillActionList();
			this.currentAction = 0;
		} catch (NotDoableException e) {
			e.getMessage();
		}
	}

	// For test delete when it's over
	public Robot(Base base, Map entityMap, _Sequence myAutomaton, Player player) {
		super(base.getX(), base.getY(), entityMap, base);
		this.myAutomaton = myAutomaton;
		this.player = player;
		this.explorationMap = entityMap;
		this.explorationMap.getCell(this.x, this.y).setExplored(true);

		this.myAutomaton = myAutomaton;
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
	public void suicideBomber(List<Cell> listCell) {
		// Run through the list of targets
		for (Iterator<Cell> cellIterator = listCell.iterator(); cellIterator.hasNext();) {
			Cell testCell = cellIterator.next();
			// The list in which are all the entities present on the cell
			// targeted
			List<Entity> testEntityList = testCell.getEntityList();
			// int i = 0;
			// Run through the list of entities
			for (Iterator<Entity> entityIterator = testEntityList.iterator(); entityIterator.hasNext();) {
				Entity eCourant = entityIterator.next();
				// If the entity is a robot, kill it
				if (eCourant instanceof Robot) {
					// this.targetsLife.put(new Pair<Direction,
					// Integer>(Direction.NORTH, i),
					// new Pair<Robot, Integer>(((Robot) eCourant), ((Robot)
					// eCourant).getLife()));
					// i++;
					((Robot) eCourant).setLife(0);
				}
			}
		}
		// Suicide the robot which is executing SuicideBomber
		this.setLife(0);

	}

	// FIXME or delete if not needed
	public void cancelSuicideBomber() {
		// int x = this.getX();
		// int y = this.getY();
		// List<Entity> northEntityList = this.entityMap.getCell(x, y -
		// 1).getListEntity();
		// List<Entity> southEntityList = this.entityMap.getCell(x, y +
		// 1).getListEntity();
		// List<Entity> westEntityList = this.entityMap.getCell(x - 1,
		// y).getListEntity();
		// List<Entity> eastEntityList = this.entityMap.getCell(x + 1,
		// y).getListEntity();
		//
		// int i = 0;
		// for (Iterator<Entity> entityIterator = northEntityList.iterator();
		// entityIterator.hasNext();) {
		// Entity eCourant = entityIterator.next();
		// if (eCourant instanceof Robot) {
		// Pair<Direction, Integer> key = new Pair<Direction,
		// Integer>(Direction.NORTH, i);
		// Pair<Robot, Integer> robotLife = this.targetsLife.get(key);
		// ((Robot) eCourant).setLife(robotLife.getSecond());
		// i++;
		// }
		// }
		//
		// i = 0;
		// for (Iterator<Entity> entityIterator = southEntityList.iterator();
		// entityIterator.hasNext();) {
		// Entity eCourant = entityIterator.next();
		// if (eCourant instanceof Robot) {
		// Pair<Direction, Integer> key = new Pair<Direction,
		// Integer>(Direction.SOUTH, i);
		// Pair<Robot, Integer> robotLife = this.targetsLife.get(key);
		// ((Robot) eCourant).setLife(robotLife.getSecond());
		// i++;
		// }
		// }
		//
		// i = 0;
		// for (Iterator<Entity> entityIterator = westEntityList.iterator();
		// entityIterator.hasNext();) {
		// Entity eCourant = entityIterator.next();
		// if (eCourant instanceof Robot) {
		// Pair<Direction, Integer> key = new Pair<Direction,
		// Integer>(Direction.WEST, i);
		// Pair<Robot, Integer> robotLife = this.targetsLife.get(key);
		// ((Robot) eCourant).setLife(robotLife.getSecond());
		// i++;
		// }
		// }
		//
		// i = 0;
		// for (Iterator<Entity> entityIterator = eastEntityList.iterator();
		// entityIterator.hasNext();) {
		// Entity eCourant = entityIterator.next();
		// if (eCourant instanceof Robot) {
		// Pair<Direction, Integer> key = new Pair<Direction,
		// Integer>(Direction.EAST, i);
		// Pair<Robot, Integer> robotLife = this.targetsLife.get(key);
		// ((Robot) eCourant).setLife(robotLife.getSecond());
		// i++;
		// }
		// }
	}

	public void fillActionList() throws NotDoableException {
		myAutomaton.addActionToActionList(this);
	}

	public void removeAnAction(Action action) {
		this.automatonInList.remove(action);
	}

	public List<Action> getAutomatonInList() {
		return this.automatonInList;
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

	/**
	 * Teleport an entity to the coordinates given
	 * 
	 * @param e
	 *            the entity
	 * @param x
	 *            x coordinate on the map
	 * @param y
	 *            y coordinate on the map
	 */
	public void teleport(int x, int y) {
		if (this.getState().equals(State.Wait)) {
			this.setState(State.TeleportMove);
			this.getMyselfGUI().setActionRequest(true);
			this.setX(x);
			this.setY(y);

			this.pickUp();
		}
	}

	public void recall(int time) {
		// FIXME : calculer case d'apparition et mettre case d'arrivee en
		// "explored" et gerer le non positionnement sur la map dans le modele
		this.setX(x);
		this.setY(y);
		this.pickUp();
		// First execution of recall, this robot is now not display on the map
		if ((this.recall == 3) && (isVisible == true)) {
			this.recall = this.recall - time;
			setIsVisible(false);
		} else {
			this.recall++;
		}
		// When the robot has spend recall-time round in the base, it's now
		// visible
		if (isVisible == false && this.recall == 3) {
			this.setIsVisible(true);
		}

	}

	public boolean getIsVisible() {
		return this.isVisible;
	}

	public void setIsVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public void addActionToActionList(Action action) {
		this.automatonInList.add(action);
	}

	public int getCurrentAction() {
		return currentAction;
	}

	public void setNextAction() {
		this.currentAction++;
	}

	public void setFirstAction() {
		this.currentAction = 0;
	}
}
