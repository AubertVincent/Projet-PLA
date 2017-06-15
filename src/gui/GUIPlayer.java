package gui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import operateur.ClassicAck;
import operateur.MoveDir;
import personnages.Player;
import util.Pair;

public class GUIPlayer extends GUICharacter {

	Player mySelf;

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
	protected static Map<Class<?>, Pair<String, String>> actionSpritePath = new HashMap<Class<?>, Pair<String, String>>();
	protected static Map<Class<?>, Integer> actionSpriteNumberOfSprites = new HashMap<Class<?>, Integer>();
	static {
		List<Class<?>> possibleActionList = Player.getPossibleActionsList();
		for (Iterator<Class<?>> action = possibleActionList.iterator(); action.hasNext();) {
			Class<?> currentAction = action.next();
			actionSpritePath.put(currentAction,
					new Pair<String, String>("res/Player/Bleu/SpriteSheet" + currentAction.getSimpleName() + ".png",
							"res/Player/Rouge/SpriteSheet" + currentAction.getSimpleName() + ".png"));
			actionSpriteNumberOfSprites.put(currentAction, numberOfSprites.get(currentAction));
			System.out
					.println("For " + currentAction.getSimpleName() + " will load " + numberOfSprites.get(currentAction)
							+ " sprites from " + "res/Player/SpriteSheet" + currentAction.getSimpleName() + ".png");
		}
	}

	List<Class<? extends operateur.Action>> animationsList = new LinkedList<Class<? extends operateur.Action>>();

	// ↓ Constructor ↓

	/**
	 * 
	 * @param userInterface
	 *            The game window in wich the GuiPickAble should be placed
	 * @param animationDuration
	 *            Duration duration of the animation
	 * @param player
	 *            The player that is linked to its graphical representation
	 */
	public GUIPlayer(GUI userInterface, int animationDuration, Player player) {
		super(userInterface, animationDuration, player);

		animationsList.add(ClassicAck.class);
		animationsList.add(MoveDir.class);
		this.mySelf = player;
	}

	// End(Constructor)

	// ↓ Getters and setters ↓

	/**
	 * 
	 * @param player
	 *            The player that is linked to its graphical representation
	 */
	public void setMySelf(Player player) {
		this.mySelf = player;
	}

	/**
	 * For To recover the player
	 */
	public Player getMyself() {
		return this.mySelf;
	}

	// End(Getters and setters)
}
