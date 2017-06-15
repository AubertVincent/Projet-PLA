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
import util.Pair;

public class GUIRobot extends GUICharacter {

	protected Robot mySelf;

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

	// Given EVERY POSSIBLE doable action (by the Robot), gives the paths to
	// its
	// animation and its number of sprites
	protected static Map<Class<?>, Pair<String, String>> actionSpritePath = new HashMap<Class<?>, Pair<String, String>>();
	protected static Map<Class<?>, Integer> actionSpriteNumberOfSprites = new HashMap<Class<?>, Integer>();
	static {
		// Puts every possible action's sprite in actionSpritePath
		List<Class<?>> possibleActionList = Robot.getPossibleActionsList();
		for (Iterator<Class<?>> action = possibleActionList.iterator(); action.hasNext();) {
			Class<?> currentAction = action.next();
			actionSpritePath.put(currentAction,
					new Pair<String, String>("res/Robot/Bleu/SpriteSheet" + currentAction.getSimpleName() + ".png",
							"res/Robot/Rouge/SpriteSheet" + currentAction.getSimpleName() + ".png"));
			actionSpriteNumberOfSprites.put(currentAction, numberOfSprites.get(currentAction));
			System.out
					.println("For " + currentAction.getSimpleName() + " will load " + numberOfSprites.get(currentAction)
							+ " sprites from " + "res/Robot/SpriteSheet" + currentAction.getSimpleName() + ".png");
		}
	}

	public void setMySelf(Robot mySelf) {
		this.mySelf = mySelf;
	}

	public Robot getMyself() {
		return this.mySelf;
	}

	List<Class<? extends operateur.Action>> animationsList = new LinkedList<Class<? extends operateur.Action>>();

	public GUIRobot(GUI userInterface, int x, int y, Direction dir, int animationDuration, Team team, Robot robot,
			GUIPlayer guiPlayer) {
		super(userInterface, x, y, dir, animationDuration, team, robot);

		animationsList.add(ClassicAck.class);
		animationsList.add(MoveDir.class);
		this.mySelf = robot;
	}

}
