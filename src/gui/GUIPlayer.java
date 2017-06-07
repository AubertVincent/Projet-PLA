package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import entite.Direction;
import operateur.Action;
import personnages.Player;

public class GUIPlayer extends GUICharacter {

	public final List<GUIRobot> listRobot;	
	
	private static Map<Class<? extends Action>, Integer> numberOfSprites = new HashMap<Class<? extends Action>, Integer>();
	static {
		numberOfSprites.put(operateur.MoveDir.class, 9);
		numberOfSprites.put(operateur.ClassicAck.class, 9);
	}

	// Given each possible action doable by the Player, gives the paths to its
	// animation
	protected static Map<Class<? extends Action>, String> actionSpritePath = new HashMap<Class<? extends Action>, String>();
	protected static Map<Class<? extends Action>, Integer> actionSpriteNumberOfSprites;
	static {
		// Puts every possible action's sprite in actionSpritePath
		List<Class<? extends Action>> possibleActionList = Player.getPossibleActionsList();
		for (Iterator<Class<? extends Action>> action = possibleActionList.iterator(); action.hasNext();) {
			Class<? extends Action> currentAction = action.next();
			actionSpritePath.put(currentAction, "res/SpriteSheet" + currentAction.toString());
			actionSpriteNumberOfSprites.put(currentAction, numberOfSprites.get(currentAction));
		}
	}
	private int spriteSheetWidth, spriteSheetHeight;

	public GUIPlayer(int x, int y, Direction dir, String path, int team) throws SlickException {
		super(x, y, dir, path, team);
		listRobot = new ArrayList<GUIRobot>();
//		new GUIPlayer(x, y, entite.Direction.SOUTH, path, 1);
//		this.perso2 = new GUIPlayer(31, 15, entite.Direction.SOUTH, "res/SpriteSheetAnimSoral.png", 2);
		// TODO Auto-generated constructor stub
	}

	// @Override
	// protected void initAnimations(int animationDuration) throws
	// SlickException {
	//
	// Animation[] currentAnimation = new Animation[8];
	//
	// List<Class<? extends Action>> possibleActionList =
	// Player.getPossibleActionsList();
	// for (Iterator<Class<? extends Action>> action =
	// possibleActionList.iterator(); action.hasNext();) {
	// Class<? extends Action> currentAction = action.next();
	//
	// SpriteSheet currentSpriteSheet = new
	// SpriteSheet(actionSpritePath.get(currentAction), spriteSheetWidth,
	// spriteSheetHeight);
	//
	// currentAnimation[0] = loadAnimation(currentSpriteSheet, 0, 1, 0,
	// animationDuration);
	// currentAnimation[1] = loadAnimation(currentSpriteSheet, 0, 1, 1,
	// animationDuration);
	// currentAnimation[2] = loadAnimation(currentSpriteSheet, 0, 1, 2,
	// animationDuration);
	// currentAnimation[3] = loadAnimation(currentSpriteSheet, 0, 1, 3,
	// animationDuration);
	// currentAnimation[4] = loadAnimation(currentSpriteSheet, 1, 9, 0,
	// animationDuration);
	// currentAnimation[5] = loadAnimation(currentSpriteSheet, 1, 9, 1,
	// animationDuration);
	// currentAnimation[6] = loadAnimation(currentSpriteSheet, 1, 9, 2,
	// animationDuration);
	// currentAnimation[7] = loadAnimation(currentSpriteSheet, 1, 9, 3,
	// animationDuration);
	// }
	//
	// }

}
