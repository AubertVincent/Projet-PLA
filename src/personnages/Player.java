package personnages;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.SlickException;

import carte.Base;
import entite.Direction;
import gui.GUI;
import gui.GUIPlayer;
import moteurDuJeu.Engine;
import pickable.PickAble;
import pickable.PickClassicAck;

public class Player extends Character {

	private Besace besace;

	protected static List<Class<?>> possibleActionsList = new LinkedList<Class<?>>();
	static {
		// Move-like animations
		possibleActionsList.add(operateur.MoveDir.class);
		possibleActionsList.add(operateur.RandomMove.class);

		// Teleport-like animations
		possibleActionsList.add(operateur.Recall.class);
		possibleActionsList.add(operateur.Tunnel.class);
		possibleActionsList.add(operateur.CreateRobot.class);

		// ClassicAttack-like animations
		possibleActionsList.add(operateur.ClassicAck.class);
	}

	private RobotList robotList;

	protected GUIPlayer mySelfGUI;

	/**
	 * Set a new Player
	 * 
	 * @param x
	 *            x coordinate on the map
	 * @param y
	 *            y coordinate on the map
	 * @param direction
	 *            Where the character is oriented
	 * @param map
	 *            The map on which the entity is located
	 * @param life
	 *            Player's life
	 * @param vision
	 *            Player's vision range
	 * @param damages
	 *            Player's attack
	 * @param range
	 *            Player's range
	 * @param movePoints
	 *            Player's move points
	 * @param recall
	 *            Player's recall's time
	 */

	public static List<Class<?>> getPossibleActionsList() {
		return possibleActionsList;
	}

	public Player(Base base, carte.Map entityMap, GUI userInterface) throws Exception {
		super(base.getX(), base.getY(), entityMap, base);
		try {
			this.mySelfGUI = new GUIPlayer(userInterface, getX(), getY(), Direction.SOUTH, 100, base.getBaseTeam(),
					this);
		} catch (SlickException e) {
			e.getMessage();
		} catch (Exception e) {
			e.getMessage();
		}
		robotList = new RobotList();
		this.besace = new Besace();
		besace.add(PickClassicAck.class);
		entityMap.setEntity(this);
	}

	public void addRobot(Object obj, Robot robot) {
		robotList.add(obj, robot);
	}

	@Override
	public boolean isPlayer() {
		return true;
	}

	@Override
	public boolean isRobot() {
		return false;
	}

	public GUIPlayer getMyselfGUI() {
		return this.mySelfGUI;
	}

	@Override
	public boolean isObstacle() {
		return false;
	}

	public Besace getBesace() {
		return besace;
	}

	public void setBesace(Besace besace) {
		this.besace = besace;
	}

	@Override
	public boolean isPickAble() {
		return false;
	}

	public List<Robot> getRobotList() {
		return robotList.getRobotList();
	}

	public void removeFromRobotList(Robot robot) {
		robotList.remove(robot);
	}

	public void die() {
		this.getMap().remove(this);
		this.getEngine().remove(this);
		this.mySelfGUI.setMySelf(null);
		this.mySelfGUI = null;

		for (Iterator<Robot> iterator = getRobotList().iterator(); iterator.hasNext();) {
			Robot currentRobot = iterator.next();
			this.kill(currentRobot);
			removeFromRobotList(currentRobot);
		}
	}

	private Engine getEngine() {
		return this.getMyselfGUI().getGUI().getEngine();
	}

	@Override
	public Player getPlayer() {
		return this;
	}

	// For this version of the game, Player have just to drop his besace
	protected void dropPickables() {
		for (Iterator<Class<? extends PickAble>> iterator = getBesace().get().keySet().iterator(); iterator
				.hasNext();) {
			Class<? extends PickAble> currentPickAbleClass = iterator.next();
			int numberOfCurrentPickAble = getBesace().get(currentPickAbleClass);
			for (int i = 0; i < numberOfCurrentPickAble; i++) {
				Constructor<? extends PickAble> pickAbleConstructor;
				try {
					pickAbleConstructor = currentPickAbleClass.getConstructor(Integer.class, Integer.class,
							carte.Map.class);
					PickAble pickAble;
					try {
						pickAble = pickAbleConstructor.newInstance(this.getX(), this.getY(), this.map);
						this.getCell().setEntity(pickAble);
						this.getBesace().remove(currentPickAbleClass);
					} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						e.printStackTrace();
					}
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
