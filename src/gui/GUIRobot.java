package gui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.SlickException;

import entite.Direction;
import entite.Team;
import operateur.Action;
import operateur.ClassicAck;
import operateur.MoveDir;
import personnages.Player;
import personnages.Robot;

public class GUIRobot extends GUICharacter {

	Robot robot;
	GUIPlayer guiPlayer;
	// The Map<ActionClass, Integer> of the yet added class->number of sprites
	// of its animation
	private static Map<Class<? extends Action>, Integer> numberOfSprites = new HashMap<Class<? extends Action>, Integer>();
	static {
		numberOfSprites.put(operateur.MoveDir.class, 9);
		numberOfSprites.put(operateur.ClassicAck.class, 6);
	}

	// Given EVERY POSSIBLE doable action (by the Robot), gives the paths to
	// its
	// animation and its number of sprites
	protected static Map<Class<? extends Action>, String> actionSpritePath = new HashMap<Class<? extends Action>, String>();
	protected static Map<Class<? extends Action>, Integer> actionSpriteNumberOfSprites = new HashMap<Class<? extends Action>, Integer>();
	static {
		// Puts every possible action's sprite in actionSpritePath
		List<Class<? extends Action>> possibleActionList = Robot.getPossibleActionsList();
		for (Iterator<Class<? extends Action>> action = possibleActionList.iterator(); action.hasNext();) {
			Class<? extends Action> currentAction = action.next();
			actionSpritePath.put(currentAction, "res/Armure/SpriteSheet" + currentAction.getSimpleName() + ".png");
			actionSpriteNumberOfSprites.put(currentAction, numberOfSprites.get(currentAction));
			System.out
					.println("For " + currentAction.getSimpleName() + " will load " + numberOfSprites.get(currentAction)
							+ " sprites from " + "res/Armure/SpriteSheet" + currentAction.getSimpleName() + ".png");
		}
	}

	public void setRobot(Robot robot) {
		this.robot = robot;
	}

	public Robot getRobot() {
		return this.robot;
	}

	public Player getPlayer() {
		return this.guiPlayer.getPlayer();
	}

	// TODO : bound to be dynamic when something is picked
	List<Class<? extends operateur.Action>> animationsList = new LinkedList<Class<? extends operateur.Action>>();

	public GUIRobot(GUI userInterface, int x, int y, Direction dir, int animationDuration, Team team, Robot robot,
			GUIPlayer guiPlayer) throws SlickException, Exception {
		super(userInterface, x, y, dir, animationDuration, team);

		this.guiPlayer = guiPlayer;
		animationsList.add(ClassicAck.class);
		animationsList.add(MoveDir.class);
		this.robot = robot;
	}

}
