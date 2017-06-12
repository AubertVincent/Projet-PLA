package personnages;

import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.SlickException;

import carte.Base;
import entite.Direction;
import gui.GUI;
import gui.GUIPlayer;
import operateur.Action;
import operateur.ClassicAck;
import operateur.MoveDir;

public class Player extends Character {

	private Besace besace;

	protected static List<Class<? extends Action>> possibleActionsList = new LinkedList<Class<? extends Action>>();
	static {
		possibleActionsList.add(ClassicAck.class);
		possibleActionsList.add(MoveDir.class);
		// possibleActionsList.add(Tunnel.class);
		// possibleActionsList.add(Recall.class);
	}

	private RobotList robotList;

	private GUIPlayer mySelfGUI;

	/**
	 * Set a new Player
	 * 
	 * @param x
	 *            x coordinate on the map
	 * @param y
	 *            y coordinate on the map
	 * @param direction
	 *            Where the character is oriented
	 * @param entityMap
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

	public static List<Class<? extends Action>> getPossibleActionsList() {
		return possibleActionsList;
	}

	public Player(Base base, carte.Map entityMap, GUI userInterface) throws Exception {
		super(base.getX(), base.getY(), entityMap, base);
		try {
			this.mySelfGUI = new GUIPlayer(userInterface, base.getX(), base.getY(), Direction.SOUTH, 100,
					base.getBaseTeam(), this);
			super.setGUICharacter(this.mySelfGUI);
		} catch (SlickException e) {
			e.getMessage();
		} catch (Exception e) {
			e.getMessage();
		}
		robotList = new RobotList();
		this.besace = new Besace();
	}

	public void addRobot(Object obj, Robot robot) {
		robotList.add(obj, robot);
	}

	public void removeRobot(Robot robot) {
		this.mySelfGUI.removeGUIRobot(robot.getMyselfGUI());
		robotList.remove(robot);

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
}
