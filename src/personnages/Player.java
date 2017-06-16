package personnages;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import carte.Base;
import carte.Cell;
import carte.Obstacle;
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

	// ↓ Constructor, update and render ↓

	public Player(Base base, carte.Map entityMap, GUI userInterface) {
		super(base.getX(), base.getY(), entityMap, base);
		this.mySelfGUI = new GUIPlayer(userInterface, 100, this);
		robotList = new RobotList();
		this.besace = new Besace();
		// comment for test
		// besace.add(PickClassicAck.class);
		entityMap.setEntity(this);
	}

	// End(Constructor, update and render)

	// ↓ Miscellaneous methods ↓

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

	@Override
	public boolean isObstacle() {
		return false;
	}

	@Override
	public boolean isPickAble() {
		return false;
	}

	public void removeFromRobotList(Robot robot) {
		robotList.remove(robot);
	}

	/**
	 * remove all reference to this
	 */
	public void die() {
		this.getMap().remove(this);
		this.getEngine().remove(this);
		this.mySelfGUI.setMySelf(null);
		this.mySelfGUI = null;

		for (Iterator<Robot> iterator = getRobotList().iterator(); iterator.hasNext();) {
			Robot currentRobot = iterator.next();
			if (currentRobot.getX() == currentRobot.getBase().getX() - 1
					&& (currentRobot.getY() == currentRobot.getBase().getY())) {
				Cell tmp = currentRobot.getMap().nearestFreeCell(this.getBase().getX(), this.getBase().getY());
				currentRobot.setXY(tmp.getX(), tmp.getY());
				currentRobot.getMap()
						.setEntity(new Obstacle(this.getBase().getX() - 1, this.getBase().getY(), this.getMap()));

			}
			this.kill(currentRobot);
			removeFromRobotList(currentRobot);
			map.remove(currentRobot);
		}
	}

	/*
	 * For this version of the game, Player have just to drop his besace
	 */
	protected void dropPickables() {
		for (Iterator<Class<? extends PickAble>> iterator = getBesace().get().keySet().iterator(); iterator
				.hasNext();) {
			Class<? extends PickAble> currentPickAbleClass = iterator.next();
			int numberOfCurrentPickAble = getBesace().get(currentPickAbleClass);

			if (currentPickAbleClass.equals(pickable.PickPriority.class)) {
				for (int i = 0; i < numberOfCurrentPickAble; i++) {
					PickAble pickableToDrop = new PickPriority(getX(), getY(), this.getMap());
					this.getCell().setEntity(pickableToDrop);
					this.map.addPickAble(pickableToDrop);
					this.getBesace().remove(currentPickAbleClass);
				}

			} else if (currentPickAbleClass.equals(pickable.PickRandomBar.class)) {
				for (int i = 0; i < numberOfCurrentPickAble; i++) {
					PickAble pickableToDrop = new PickRandomBar(getX(), getY(), this.getMap());
					this.getCell().setEntity(pickableToDrop);
					this.map.addPickAble(pickableToDrop);
					this.getBesace().remove(currentPickAbleClass);
				}

			} else if (currentPickAbleClass.equals(pickable.PickSuccession.class)) {
				for (int i = 0; i < numberOfCurrentPickAble; i++) {
					PickAble pickableToDrop = new PickSuccession(getX(), getY(), this.getMap());
					this.getCell().setEntity(pickableToDrop);
					this.map.addPickAble(pickableToDrop);
					this.getBesace().remove(currentPickAbleClass);
				}

			} else if (currentPickAbleClass.equals(pickable.PickClassicAck.class)) {
				for (int i = 0; i < numberOfCurrentPickAble; i++) {
					PickAble pickableToDrop = new PickClassicAck(getX(), getY(), this.getMap());
					this.getCell().setEntity(pickableToDrop);
					this.map.addPickAble(pickableToDrop);
					this.getBesace().remove(currentPickAbleClass);
				}

			} else if (currentPickAbleClass.equals(pickable.PickMoveDir.class)) {
				for (int i = 0; i < numberOfCurrentPickAble; i++) {
					PickAble pickableToDrop = new PickMoveDir(getX(), getY(), this.getMap());
					this.getCell().setEntity(pickableToDrop);
					this.map.addPickAble(pickableToDrop);
					this.getBesace().remove(currentPickAbleClass);
				}

			} else if (currentPickAbleClass.equals(pickable.PickPickUp.class)) {
				for (int i = 0; i < numberOfCurrentPickAble; i++) {
					PickAble pickableToDrop = new PickPickUp(getX(), getY(), this.getMap());
					this.getCell().setEntity(pickableToDrop);
					this.map.addPickAble(pickableToDrop);
					this.getBesace().remove(currentPickAbleClass);
				}

			} else if (currentPickAbleClass.equals(pickable.PickRandomMove.class)) {
				for (int i = 0; i < numberOfCurrentPickAble; i++) {
					PickAble pickableToDrop = new PickRandomMove(getX(), getY(), this.getMap());
					this.getCell().setEntity(pickableToDrop);
					this.map.addPickAble(pickableToDrop);
					this.getBesace().remove(currentPickAbleClass);
				}

			} else if (currentPickAbleClass.equals(pickable.PickRecall.class)) {
				for (int i = 0; i < numberOfCurrentPickAble; i++) {
					PickAble pickableToDrop = new PickRecall(getX(), getY(), this.getMap());
					this.getCell().setEntity(pickableToDrop);
					this.map.addPickAble(pickableToDrop);
					this.getBesace().remove(currentPickAbleClass);
				}

			} else if (currentPickAbleClass.equals(pickable.PickSuicideBomber.class)) {
				for (int i = 0; i < numberOfCurrentPickAble; i++) {
					PickAble pickableToDrop = new PickSuicideBomber(getX(), getY(), this.getMap());
					this.getCell().setEntity(pickableToDrop);
					this.map.addPickAble(pickableToDrop);
					this.getBesace().remove(currentPickAbleClass);
				}

			} else if (currentPickAbleClass.equals(pickable.PickTunnel.class)) {
				for (int i = 0; i < numberOfCurrentPickAble; i++) {
					PickAble pickableToDrop = new PickTunnel(getX(), getY(), this.getMap());
					this.getCell().setEntity(pickableToDrop);
					this.map.addPickAble(pickableToDrop);
					this.getBesace().remove(currentPickAbleClass);
				}

			} else if (currentPickAbleClass.equals(pickable.PickExplore.class)) {
				// Not needed given the current implementation
				;
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
		int possession = this.getBesace().numberOfElementPickAble();
		for (Robot robot : this.getRobotList()) {
			possession += robot.getDropAblePickAbleList().size();
		}
		return possession;
	}

	/**
	 * use to initialize each player round
	 */
	public void resetAttributes() {
		this.movePoints = 20;
		this.remainingAttacks = 5;
	}

	/**
	 * @return true if this player is die
	 */
	public boolean isDie() {
		if (this.getState().equals(State.Dead)) {
			return true;
		} else {
			return false;
		}
	}
	// End(Miscellaneous methods)

	// ↓ Getters and setters ↓

	public static List<Class<?>> getPossibleActionsList() {
		return possibleActionsList;
	}

	private Engine getEngine() {
		return this.getMyselfGUI().getGUI().getEngine();
	}

	@Override
	public Player getPlayer() {
		return this;
	}

	public Besace getBesace() {
		return besace;
	}

	public void setBesace(Besace besace) {
		this.besace = besace;
	}

	public List<Robot> getRobotList() {
		return robotList.getRobotList();
	}

	public GUIPlayer getMyselfGUI() {
		return this.mySelfGUI;
	}

	// End(Getters and setters)

}
