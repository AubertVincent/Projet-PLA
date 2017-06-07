package gui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.SlickException;

import entite.Direction;
import operateur.Action;
import personnages.Player;
import personnages.Robot;

public class GUIRobot extends GUICharacter {

	// Given each possible action doable by the Player, gives the paths to its
	// animation
	protected static Map<Class<? extends Action>, String> actionSpritePath = new HashMap<Class<? extends Action>, String>();
	static {
		// Puts every possible action's sprite in actionSpritePath
		List<Class<? extends Action>> possibleActionList = Robot.getPossibleActionsList();
		for (Iterator<Class<? extends Action>> action = possibleActionList.iterator(); action.hasNext();) {
			Class<? extends Action> currentAction = action.next();
			actionSpritePath.put(currentAction, "res/SpriteSheet" + currentAction.toString());
		}
	}

	public GUIRobot(int x, int y, Direction dir, String path, int team) throws SlickException {
		super(x, y, dir, path, team);
//		new GUIRobot(x, y, dir, path, team);
		// TODO Auto-generated constructor stub
	}

	// @Override
	// protected void initAnimations(int animationDuration) throws
	// SlickException {
	// // TODO Auto-generated method stub
	//
	// }

}
