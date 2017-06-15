package personnages;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.SlickException;

import carte.Base;
import entite.Direction;
import exceptions.NotDoableException;
import gui.GUI;
import gui.GUIPlayer;
import moteurDuJeu.Engine;
import pickable.PickAble;
import pickable.PickClassicAck;
import pickable.PickMoveDir;
import pickable.PickPickUp;
import pickable.PickPriority;
import pickable.PickRandomBar;
import pickable.PickRandomMove;
import pickable.PickRecall;
import pickable.PickSuccession;
import pickable.PickSuicideBomber;
import pickable.PickTunnel;

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
		// comment for test
		// besace.add(PickClassicAck.class);
		entityMap.setEntity(this);
	}

	// delete when it's over
	public Player(Base base, carte.Map entityMap) throws Exception {
		super(base.getX(), base.getY(), entityMap, base);
		robotList = new RobotList();
		this.besace = new Besace();

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
			map.remove(currentRobot);
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
	protected void dropPickables() throws NotDoableException {
		for (Iterator<Class<? extends PickAble>> iterator = getBesace().get().keySet().iterator(); iterator
				.hasNext();) {
			Class<? extends PickAble> currentPickAbleClass = iterator.next();
			int numberOfCurrentPickAble = getBesace().get(currentPickAbleClass);

			if (currentPickAbleClass.equals(pickable.PickPriority.class)) {
				for (int i = 0; i < numberOfCurrentPickAble; i++) {
					PickAble pickableToDrop = new PickPriority(getX(), getY(), this.getMap());
					this.getCell().setEntity(pickableToDrop);
					this.getBesace().remove(currentPickAbleClass);
				}

			} else if (currentPickAbleClass.equals(pickable.PickRandomBar.class)) {
				for (int i = 0; i < numberOfCurrentPickAble; i++) {
					PickAble pickableToDrop = new PickRandomBar(getX(), getY(), this.getMap());
					this.getCell().setEntity(pickableToDrop);
					this.getBesace().remove(currentPickAbleClass);
				}

			} else if (currentPickAbleClass.equals(pickable.PickSuccession.class)) {
				for (int i = 0; i < numberOfCurrentPickAble; i++) {
					PickAble pickableToDrop = new PickSuccession(getX(), getY(), this.getMap());
					this.getCell().setEntity(pickableToDrop);
					this.getBesace().remove(currentPickAbleClass);
				}

			} else if (currentPickAbleClass.equals(pickable.PickClassicAck.class)) {
				for (int i = 0; i < numberOfCurrentPickAble; i++) {
					PickAble pickableToDrop = new PickClassicAck(getX(), getY(), this.getMap());
					this.getCell().setEntity(pickableToDrop);
					this.getBesace().remove(currentPickAbleClass);
				}

			} else if (currentPickAbleClass.equals(pickable.PickMoveDir.class)) {
				for (int i = 0; i < numberOfCurrentPickAble; i++) {
					PickAble pickableToDrop = new PickMoveDir(getX(), getY(), this.getMap());
					this.getCell().setEntity(pickableToDrop);
					this.getBesace().remove(currentPickAbleClass);
				}

			} else if (currentPickAbleClass.equals(pickable.PickPickUp.class)) {
				for (int i = 0; i < numberOfCurrentPickAble; i++) {
					PickAble pickableToDrop = new PickPickUp(getX(), getY(), this.getMap());
					this.getCell().setEntity(pickableToDrop);
					this.getBesace().remove(currentPickAbleClass);
				}

			} else if (currentPickAbleClass.equals(pickable.PickRandomMove.class)) {
				for (int i = 0; i < numberOfCurrentPickAble; i++) {
					PickAble pickableToDrop = new PickRandomMove(getX(), getY(), this.getMap());
					this.getCell().setEntity(pickableToDrop);
					this.getBesace().remove(currentPickAbleClass);
				}

			} else if (currentPickAbleClass.equals(pickable.PickRecall.class)) {
				for (int i = 0; i < numberOfCurrentPickAble; i++) {
					PickAble pickableToDrop = new PickRecall(getX(), getY(), this.getMap());
					this.getCell().setEntity(pickableToDrop);
					this.getBesace().remove(currentPickAbleClass);
				}

			} else if (currentPickAbleClass.equals(pickable.PickSuicideBomber.class)) {
				for (int i = 0; i < numberOfCurrentPickAble; i++) {
					PickAble pickableToDrop = new PickSuicideBomber(getX(), getY(), this.getMap());
					this.getCell().setEntity(pickableToDrop);
					this.getBesace().remove(currentPickAbleClass);
				}

			} else if (currentPickAbleClass.equals(pickable.PickTunnel.class)) {
				for (int i = 0; i < numberOfCurrentPickAble; i++) {
					PickAble pickableToDrop = new PickTunnel(getX(), getY(), this.getMap());
					this.getCell().setEntity(pickableToDrop);
					this.getBesace().remove(currentPickAbleClass);
				}

			} else if (currentPickAbleClass.equals(pickable.PickExplore.class)) {
				// Not needed given the current implementation
				throw new NotDoableException("Impossible to drop explore");
			}

			// We tried to use reflection but got stuck with a
			// 'java.lang.reflect.InvocationTargetException'
			// try {
			// PickAble pickable = currentPickAbleClass
			// .getConstructor(Integer.class, Integer.class,
			// carte.Map.class)
			// .newInstance(getX(), getY(), this.getMap());
			// this.getCell().setEntity(pickable);
			// this.getBesace().remove(currentPickAbleClass);
			//
			// } catch (InstantiationException | IllegalAccessException |
			// IllegalArgumentException
			// | InvocationTargetException | NoSuchMethodException |
			// SecurityException e) {
			// e.printStackTrace();
			// }

		}
	}

	/**
	 * This function return number of element owned by this player, it means
	 * number of operator in robots' player on the map and number of operator in
	 * his besace
	 * 
	 * @return
	 */
	public int numberOfOwnedPickAble() {
		int possession = this.getBesace().numberOfElement();
		for (Robot robot : this.getRobotList()) {
			possession += robot.getDropAblePickAbleList().size();
		}
		return possession;
	}
}
