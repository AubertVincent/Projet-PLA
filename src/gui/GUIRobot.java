package gui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import entite.Direction;
import entite.Team;
import operateur.ClassicAck;
import operateur.MoveDir;
import personnages.Robot;

public class GUIRobot extends GUICharacter {

	Robot mySelf;
	GUIPlayer guiPlayer;

	// The Map<ActionClass, Integer> of the yet added class->number of sprites
	// of its animation
	private static Map<Class<?>, Integer> numberOfSprites = new HashMap<Class<?>, Integer>();
	static {
		numberOfSprites.put(operateur.MoveDir.class, 9);
		numberOfSprites.put(operateur.ClassicAck.class, 6);
	}

	// Given EVERY POSSIBLE doable action (by the Robot), gives the paths to
	// its
	// animation and its number of sprites
	protected static Map<Class<?>, String> actionSpritePath = new HashMap<Class<?>, String>();
	protected static Map<Class<?>, Integer> actionSpriteNumberOfSprites = new HashMap<Class<?>, Integer>();
	static {
		// Puts every possible action's sprite in actionSpritePath
		List<Class<?>> possibleActionList = Robot.getPossibleActionsList();
		for (Iterator<Class<?>> action = possibleActionList.iterator(); action.hasNext();) {
			Class<?> currentAction = action.next();
			actionSpritePath.put(currentAction, "res/Armure/SpriteSheet" + currentAction.getSimpleName() + ".png");
			actionSpriteNumberOfSprites.put(currentAction, numberOfSprites.get(currentAction));
			System.out
					.println("For " + currentAction.getSimpleName() + " will load " + numberOfSprites.get(currentAction)
							+ " sprites from " + "res/Armure/SpriteSheet" + currentAction.getSimpleName() + ".png");
		}
	}

	public void setMySelf(Robot mySelf) {
		this.mySelf = mySelf;
	}

	public Robot getMyself() {
		return this.mySelf;
	}

	// TODO : bound to be dynamic when something is picked
	List<Class<? extends operateur.Action>> animationsList = new LinkedList<Class<? extends operateur.Action>>();

	public GUIRobot(GUI userInterface, int x, int y, Direction dir, int animationDuration, Team team, Robot robot,
			GUIPlayer guiPlayer) {
		super(userInterface, x, y, dir, animationDuration, team, robot);

		this.guiPlayer = guiPlayer;
		animationsList.add(ClassicAck.class);
		animationsList.add(MoveDir.class);
		this.mySelf = robot;
	}

}
