package personnages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import carte.Cell;
import carte.Coordinates;
import carte.Map;
import carte.Obstacle;
import entite.Entity;
import exceptions.NotDoableException;
import gui.GUI;
import gui.GUIRobot;
import operateur.Action;
import pickable.PickAble;
import sequence._Sequence;

public class Robot extends Character {

	protected _Sequence myAutomaton;
	// Used to be used in Cancel
	// private java.util.Map<Pair<Direction, Integer>, Pair<Robot, Integer>>
	// targetsLife;
	private GUIRobot mySelfGUI;
	protected List<PickAble> dropAblePickAbleList;
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

	// ↓ Constructor, update and render ↓

	public Robot(int x, int y, Map map, GUI userInterface, _Sequence myAutomaton, Player player) {
		super(x, y, map, player.getBase());

		this.dropAblePickAbleList = myAutomaton.sequenceToPickAbleList(x, y, map);
		this.myAutomaton = myAutomaton;
		this.mySelfGUI = new GUIRobot(userInterface, 100, this);
		this.player = player;
		this.player.addRobot(new Coordinates(x, y), this);
		map.setEntity(this);
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

	// End(Constructor, update and render)

	// ↓ Miscellaneous methods ↓

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

	/**
	 * The robot throws the pickable which constitutes it
	 */
	protected void dropPickables() {
		for (Iterator<PickAble> iterator = this.getDropAblePickAbleList().listIterator(); iterator.hasNext();) {
			PickAble currentPickAble = iterator.next();
			currentPickAble.setX(this.getX());
			currentPickAble.setY(this.getY());
			this.getMap().setEntity(currentPickAble);
			this.getMap().addPickAble(currentPickAble);
		}
		dropAblePickAbleList.clear();
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
		this.getMyselfGUI().setActionRequest(true);
		// Run through the list of targets
		if (this.getState().equals(State.Wait)) {
			this.setState(State.SuicideBomberAttack);
			this.getMyselfGUI().setActionRequest(true);
			for (Iterator<Cell> cellIterator = listCell.iterator(); cellIterator.hasNext();) {
				Cell testCell = cellIterator.next();
				// The list in which are all the entities present on the cell
				// targeted
				List<Entity> testEntityList = testCell.getEntityList();
				// Run through the list of entities
				List<Entity> testEntityListCopy = new ArrayList<Entity>(testEntityList);
				for (Iterator<Entity> entityIterator = testEntityListCopy.iterator(); entityIterator.hasNext();) {
					Entity eCourant = entityIterator.next();
					// If the entity is a robot, kill it
					if (eCourant instanceof Robot) {
						this.getMyselfGUI().setActionRequest(true);
						this.kill((Robot) eCourant);
					}
				}
			}
			// Suicide the robot which is executing SuicideBomber
			this.kill(this);
		}

	}

	/**
	 * remove all reference to this
	 */
	public void die() {

		player.removeFromRobotList(this);

		this.mySelfGUI.setMySelf(null);
		this.mySelfGUI = null;

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
			this.setXY(x, y);

			this.pickUp();
		}
	}

	public void recall(int time) {

		if (this.getState().equals(State.Wait)) {

			// First execution of recall, this robot is now not display on the
			// map
			if ((this.recall >= 3) && (isVisible == true)) {
				this.setState(State.TeleportMove);
				this.getMyselfGUI().setActionRequest(true);
				this.recall = this.recall - this.recall + time;
				setIsVisible(false);
				this.setXY(this.getBase().getX() - 1, this.getBase().getY());
				this.getExplorationMap().getCell(getX(), getY()).setExplored(true);
			} else {
				this.recall++;
			}
			// When the robot has spend recall-time round in the base, it's now
			// visible
			if (isVisible == false && this.recall >= 3) {
				// Place the robot at the base's nearest free cell
				this.setState(State.TeleportMove);
				this.recall = 3;
				this.getMyselfGUI().setActionRequest(true);
				Cell tmp = this.getMap().nearestFreeCell(this.getBase().getX(), this.getBase().getY());
				this.setXY(tmp.getX(), tmp.getY());
				this.getMap().setEntity(new Obstacle(this.getBase().getX() - 1, this.getBase().getY(), this.getMap()));
				this.getExplorationMap().getCell(getX(), getY()).setExplored(true);
				this.setIsVisible(true);
				this.life = 3;
			}
		}
	}

	// End(Miscellaneous methods)

	// ↓ Getters and setters ↓

	public static List<Class<?>> getPossibleActionsList() {
		return possibleActionsList;
	}

	public Map getExplorationMap() {
		return explorationMap;
	}

	public void setExplorationMap(Map explorationMap) {
		this.explorationMap = explorationMap;
	}

	public _Sequence getAutomaton() {
		return this.myAutomaton;
	}

	public void setAutomaton(_Sequence automaton) {
		this.myAutomaton = automaton;
		this.dropAblePickAbleList.clear();
		this.dropAblePickAbleList = myAutomaton.sequenceToPickAbleList(getX(), getY(), this.getMap());
	}

	public GUIRobot getMyselfGUI() {
		return this.mySelfGUI;
	}

	@Override
	public Player getPlayer() {
		return this.player;
	}

	public List<PickAble> getDropAblePickAbleList() {
		return dropAblePickAbleList;
	}

	public void setXY(int x, int y) {
		this.getMap().moveCharacter(this, x, y);
		super.x = x;
		super.y = y;
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

	public void resetAttributes() {
		this.movePoints = 10;
		this.remainingAttacks = 1;
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

	// End(Getters and setters)
}
