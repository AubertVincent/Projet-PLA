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

	private GUIPlayer guiPlayer;

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
	 * @param attack
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
			GUIPlayer GUIPlayer = new GUIPlayer(userInterface, base.getX(), base.getY(), Direction.SOUTH, 100,
					base.getBaseTeam(), this);

			this.guiPlayer = GUIPlayer;
			userInterface.addGUIPlayer(GUIPlayer);
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
		this.guiPlayer.removeGUIRobot(robot.getGUIRobot());
		robotList.remove(robot);

	}

	public RobotList getListRobot() {
		return robotList;
	}

	@Override
	public boolean isPlayer() {
		return true;
	}

	@Override
	public boolean isRobot() {
		return false;
	}

	public void setX(int x) {
		this.getEntityMap().movePlayer(this, x, this.getY());
		super.setX(x);
	}

	public void setY(int y) {
		this.getEntityMap().movePlayer(this, this.getX(), y);
		super.setY(y);
	}

	public int getX() {
		return super.getX();
	}

	public int getY() {
		return super.getY();
	}

	public GUIPlayer getGUIPlayer() {
		return this.guiPlayer;
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

	public void movePlayer(int newX, int newY) {
		this.getEntityMap().movePlayer(this, newX, newY);
	}

}
