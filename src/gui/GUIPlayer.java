package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.SlickException;

import entite.Direction;
import entite.Team;
import operateur.ClassicAck;
import operateur.MoveDir;
import personnages.Player;
import personnages.Robot;

public class GUIPlayer extends GUICharacter {

	private final List<GUIRobot> guiRobotlist;

	private Player player;

	// The Map<ActionClass, Integer> of the yet added class->number of sprites
	// of its animation
	private static Map<Class<?>, Integer> numberOfSprites = new HashMap<Class<?>, Integer>();
	static {
		// Move-like animations
		numberOfSprites.put(operateur.MoveDir.class, 9);
		numberOfSprites.put(operateur.RandomMove.class, 9);

		// Teleport-like animations
		numberOfSprites.put(operateur.Recall.class, 7);
		numberOfSprites.put(operateur.Tunnel.class, 7);
		numberOfSprites.put(operateur.SuicideBomber.class, 7);
		numberOfSprites.put(operateur.CreateRobot.class, 7);

		// ClassicAttack-like animations
		numberOfSprites.put(operateur.ClassicAck.class, 6);

	}

	// Given EVERY POSSIBLE doable action (by the Player), gives the paths to
	// its animation and its number of sprites
	protected static Map<Class<?>, String> actionSpritePath = new HashMap<Class<?>, String>();
	protected static Map<Class<?>, Integer> actionSpriteNumberOfSprites = new HashMap<Class<?>, Integer>();
	static {
		List<Class<?>> possibleActionList = Player.getPossibleActionsList();
		for (Iterator<Class<?>> action = possibleActionList.iterator(); action.hasNext();) {
			Class<?> currentAction = action.next();
			actionSpritePath.put(currentAction, "res/SpriteSheet" + currentAction.getSimpleName() + ".png");
			actionSpriteNumberOfSprites.put(currentAction, numberOfSprites.get(currentAction));
			System.out
					.println("For " + currentAction.getSimpleName() + " will load " + numberOfSprites.get(currentAction)
							+ " sprites from " + "res/SpriteSheet" + currentAction.getSimpleName() + ".png");
		}
	}

	// TODO : bound to be dynamic when something is picked
	List<Class<? extends operateur.Action>> animationsList = new LinkedList<Class<? extends operateur.Action>>();

	public GUIPlayer(GUI userInterface, int x, int y, Direction dir, int animationDuration, Team team)
			throws SlickException, Exception {
		super(userInterface, x, y, dir, animationDuration, team);

		guiRobotlist = new ArrayList<GUIRobot>();
		animationsList.add(ClassicAck.class);
		animationsList.add(MoveDir.class);
		this.player = null;
	}

	public List<GUIRobot> getGuiRobotList() {
		return this.guiRobotlist;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void createRobot(Robot robot, GUI userinterface) throws SlickException {
		try {
			GUIRobot tmp = new GUIRobot(userinterface, robot.getX(), robot.getY(), Direction.SOUTH, 100,
					robot.getTeam());

			guiRobotlist.add(tmp);
		} catch (Exception e) {
			e.getMessage();
		}
	}

}
