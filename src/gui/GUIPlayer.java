package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.SlickException;

import carte.Coordinates;
import entite.Direction;
import entite.Team;
import operateur.Action;
import operateur.ClassicAck;
import operateur.MoveDir;
import personnages.Player;
import personnages.Robot;

public class GUIPlayer extends GUICharacter {

	private final List<GUIRobot> guiRobotlist;

	Player player;

	// The Map<ActionClass, Integer> of the yet added class->number of sprites
	// of its animation
	private static Map<Class<? extends Action>, Integer> numberOfSprites = new HashMap<Class<? extends Action>, Integer>();
	static {
		numberOfSprites.put(operateur.MoveDir.class, 9);
		numberOfSprites.put(operateur.ClassicAck.class, 6);
	}

	// Given EVERY POSSIBLE doable action (by the Player), gives the paths to
	// its animation and its number of sprites
	protected static Map<Class<? extends Action>, String> actionSpritePath = new HashMap<Class<? extends Action>, String>();
	protected static Map<Class<? extends Action>, Integer> actionSpriteNumberOfSprites = new HashMap<Class<? extends Action>, Integer>();
	static {
		List<Class<? extends Action>> possibleActionList = Player.getPossibleActionsList();
		for (Iterator<Class<? extends Action>> action = possibleActionList.iterator(); action.hasNext();) {
			Class<? extends Action> currentAction = action.next();
			actionSpritePath.put(currentAction, "res/SpriteSheet" + currentAction.getSimpleName() + ".png");
			actionSpriteNumberOfSprites.put(currentAction, numberOfSprites.get(currentAction));
			System.out
					.println("For " + currentAction.getSimpleName() + " will load " + numberOfSprites.get(currentAction)
							+ " sprites from " + "res/SpriteSheet" + currentAction.getSimpleName() + ".png");
		}
	}

	// TODO : bound to be dynamic when something is picked
	List<Class<? extends operateur.Action>> animationsList = new LinkedList<Class<? extends operateur.Action>>();

	public GUIPlayer(GUI userInterface, Coordinates coord, Direction dir, int animationDuration, Team team,
			Player player) throws SlickException, Exception {
		super(userInterface, coord, dir, animationDuration, team);

		guiRobotlist = new ArrayList<GUIRobot>();
		animationsList.add(ClassicAck.class);
		animationsList.add(MoveDir.class);
		this.player = player;
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

	public void createRobot(Robot robot, GUI userinterface) throws
	 SlickException {
	 try {
		 GUIRobot tmp = new GUIRobot(userinterface, robot.getCoord(),
				 Direction.SOUTH, 100, robot.getTeam(), robot, this);
	 	}
	 }

	public void addGUIRobot(GUIRobot guiRobot) {
		guiRobotlist.add(guiRobot);
	}

	public void removeGUIRobot(GUIRobot guiRobot) {
		guiRobotlist.remove(guiRobot);
	}

}
