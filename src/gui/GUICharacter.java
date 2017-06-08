package gui;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import entite.Direction;
import operateur.Action;

import personnages.Character;
import personnages.Player;
import personnages.Robot;

// Contenu a rajouter a personnages.Personnage 
public abstract class GUICharacter {

	private static final int spriteSheetWidth = 64;
	private static final int spriteSheetHeight = 64;
	
	// Coordinates in pixels
	private float xPx, yPx;
	// Coordinates to reach in pixels
	private float xPxTarget, yPxTarget;
	// Coordinates in cell's position in map
	private int xCell, yCell;
	// Coordinates to reach in cell's position in map
	private int xCellTarget, yCellTarget;

	private Direction dir;

	private int team;

	// TODO
	// Tableau etat -> booleen
	// Map<entite.Etat, Boolean> tableauEtat = new HashMap<entite.Etat,
	// Boolean>();
	// Pour l'instant : booleen moving
	private boolean moving;

	// TODO
	// Tableau action -> animation[] (plus tard)
	Map<Class<? extends operateur.Action>, Animation[]> listeAnimations = new HashMap<Class<? extends operateur.Action>, Animation[]>();
	// Pour l'instant : animation[]
	private final Animation[] animation_depl = new Animation[8];

	protected Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y, int animationDuration) {
		Animation animation = new Animation();
		for (int x = startX; x < endX; x++) {
			animation.addFrame(spriteSheet.getSprite(x, y), animationDuration);
		}
		return animation;
	}

	// TODO : adapt to HashMap
	protected void initAnimations(int animationDuration) throws SlickException, Exception {

		Animation[] currentAnimation = new Animation[8];

		// Get possibleActionList of the current Character
		List<Class<? extends Action>> possibleActionList ;
		if (this instanceof GUIPlayer){
			possibleActionList = Player.getPossibleActionsList();
		}
		else if (this instanceof GUIRobot){
			possibleActionList = Robot.getPossibleActionsList();	
		}
		else {
			throw new Exception("Unknown Character subclass");
		}
		// load the animation of each action element of the possibleActionList
		for (Iterator<Class<? extends Action>> action = possibleActionList.iterator(); action.hasNext();) {
			Class<? extends Action> currentAction = action.next();
			SpriteSheet currentSpriteSheet ;
			if (this instanceof GUIPlayer){
				currentSpriteSheet = new SpriteSheet(GUIPlayer.actionSpritePath.get(currentAction), spriteSheetWidth, spriteSheetHeight);
				currentNumberOfSprites = 
			}
			else if (this instanceof GUIRobot){
				currentSpriteSheet = new SpriteSheet(GUIRobot.actionSpritePath.get(currentAction), spriteSheetWidth, spriteSheetHeight);					
			}
			else {
				throw new Exception("Unknown Charcter subclass");
			}
			
			
			currentAnimation[0] = loadAnimation(currentSpriteSheet, 0, 1, 0, animationDuration);
			currentAnimation[1] = loadAnimation(currentSpriteSheet, 0, 1, 1, animationDuration);
			currentAnimation[2] = loadAnimation(currentSpriteSheet, 0, 1, 2, animationDuration);
			currentAnimation[3] = loadAnimation(currentSpriteSheet, 0, 1, 3, animationDuration);
			currentAnimation[4] = loadAnimation(currentSpriteSheet, 1, numberOfSprites, 0, animationDuration);
			currentAnimation[5] = loadAnimation(currentSpriteSheet, 1, numberOfSprites, 1, animationDuration);
			currentAnimation[6] = loadAnimation(currentSpriteSheet, 1, numberOfSprites, 2, animationDuration);
			currentAnimation[7] = loadAnimation(currentSpriteSheet, 1, numberOfSprites, 3, animationDuration);
			
			
			
		}

	}
	//	protected abstract void initAnimations(int animationDuration) throws SlickException;
	// protected void initAnimations(Map<Action, String> spriteSheetLocation,
	// int spriteSheetWidth, int spriteSheetHeight,
	// int animationDuration) throws SlickException {
	// SpriteSheet spriteSheet = new SpriteSheet(spriteSheetLocation,
	// spriteSheetWidth, spriteSheetHeight);
	// this.animation_depl[0] = loadAnimation(spriteSheet, 0, 1, 0,
	// animationDuration);
	// this.animation_depl[1] = loadAnimation(spriteSheet, 0, 1, 1,
	// animationDuration);
	// this.animation_depl[2] = loadAnimation(spriteSheet, 0, 1, 2,
	// animationDuration);
	// this.animation_depl[3] = loadAnimation(spriteSheet, 0, 1, 3,
	// animationDuration);
	// this.animation_depl[4] = loadAnimation(spriteSheet, 1, 9, 0,
	// animationDuration);
	// this.animation_depl[5] = loadAnimation(spriteSheet, 1, 9, 1,
	// animationDuration);
	// this.animation_depl[6] = loadAnimation(spriteSheet, 1, 9, 2,
	// animationDuration);
	// this.animation_depl[7] = loadAnimation(spriteSheet, 1, 9, 3,
	// animationDuration);
	// }

	/**
	 * Creates the GUICharacter corresponding to a character, its graphical
	 * representation.
	 * 
	 * @param x
	 *            x coordinate in the map grid
	 * @param y
	 *            y coordinate in the map grid
	 * @param dir
	 *            Direction of the character
	 * @param spriteSheetAnimation
	 *            Path to the sprite sheet of the moving animation (TODO : adapt
	 *            to any animation)
	 * @throws SlickException
	 *             Indicates a failure of the loading of a sprite sheet
	 */

	public GUICharacter(int x, int y, Direction dir, String spriteSheetAnimation, int team) throws SlickException {

		super();
		this.xCell = x;
		this.yCell = y;
		setTargetX(getCurrentX());
		setTargetY(getCurrentY());
		this.xPx = GUI.cellToPixelX(getCurrentX());
		this.yPx = GUI.cellToPixelY(getCurrentY());
		this.dir = dir;
		this.setMoving(false);
		this.initAnimation(spriteSheetAnimation, 64, 64, 100);
		this.team = team;

	}

	/**
	 * Renders the GUICharacters in the given Graphics
	 * 
	 * @param g
	 *            A Graphics to represent the GUICharacter in
	 */
	public void render(Graphics g) {
		g.setColor(new Color(0, 0, 0, .5f));
		g.fillOval((int) xPx - 16, (int) yPx - 8, 32, 16);
		// -32 et -60 to center in cell
		g.drawAnimation(animation_depl[dir.toInt() + (isMoving() ? 4 : 0)], (int) xPx - 32, (int) yPx - 60);
	}

	/**
	 * Updates the GUICharacter relying on the delta delay elapsed since last
	 * call to this method.
	 *
	 * @param gui
	 *            The GUI which the GUICharacters lives in
	 * @param delta
	 *            The delay (in milliseconds) since the last call of this method
	 */
	public void update(GUI gui, int delta) {
		if (isMoving()) {

			float nextXPx = getCurrentXPx(), nextYPx = getCurrentYPx();

			if (getDirection() == Direction.WEST || getDirection() == Direction.EAST) {
				nextXPx = getNextXPx(delta);
			} else {
				nextYPx = getNextYPx(delta);
			}

			if (isInPlace()) {
				setMoving(false);
			} else {
				if (gui.isObstacle(nextXPx, nextYPx)) {
					System.out.println("Obstacle détecté :|");
					setMoving(false);
				} else {
					this.xPx = nextXPx;
					setCurrentX(GUI.pixelToCellX(nextXPx));
					this.yPx = nextYPx;
					setCurrentY(GUI.pixelToCellY(nextYPx));
				}
			}
		}
	}

	// returns true if GUIChararcter is in targetCell
	private boolean isInPlace() {
		// return false ;
		float tolerance = 1f;

		float maximumAcceptableHeight, maximumAcceptableWidth, minimumAcceptableHeight, minimumAcceptableWidth;
		boolean isInPlaceHeight, isInPlaceWidth;

		maximumAcceptableHeight = getTargetYPx() + tolerance;
		maximumAcceptableWidth = getTargetXPx() + tolerance;
		minimumAcceptableHeight = getTargetYPx() - tolerance;
		minimumAcceptableWidth = getTargetXPx() - tolerance;

		isInPlaceWidth = getCurrentXPx() <= maximumAcceptableWidth && getCurrentXPx() >= minimumAcceptableWidth;
		isInPlaceHeight = getCurrentYPx() <= maximumAcceptableHeight && getCurrentYPx() >= minimumAcceptableHeight;

		return getCurrentX() == getTargetX() && getCurrentY() == getTargetY() && isInPlaceHeight && isInPlaceWidth;
		// return getCurrentX() == getTargetX() && getCurrentY() ==
		// getTargetY();
	}

	/**
	 * Makes the GUICharacter move of one cell in the given direction
	 * 
	 * @param dir
	 *            The direction in which the GUICharacter will move
	 */
	public void goToDirection(Direction dir) {
		setDirection(dir);
		switch (dir) {
		case NORTH:
			setTargetY(getCurrentY() - 1);
			break;
		case WEST:
			setTargetX(getCurrentX() - 1);
			break;
		case SOUTH:
			setTargetY(getCurrentY() + 1);
			break;
		case EAST:
			setTargetX(getCurrentX() + 1);
			break;
		}
		setMoving(true);
	}

	private float getNextXPx(int delta) {
		float nextX = getCurrentXPx();

		if (getCurrentXPx() > getTargetXPx()) {
			nextX = getCurrentXPx() - .1f * delta;
		}
		if (getCurrentXPx() < getTargetXPx()) {
			nextX = getCurrentXPx() + .1f * delta;
		}
		return nextX;
	}

	private float getNextYPx(int delta) {
		float nextY = getCurrentYPx();

		if (getCurrentYPx() > getTargetYPx()) {
			nextY = getCurrentYPx() - .1f * delta;
		}
		if (getCurrentYPx() < getTargetYPx()) {
			nextY = getCurrentYPx() + .1f * delta;
		}
		return nextY;
	}

	// TODO : Temporary until adapt to 'boolean isState(State state)'
	protected boolean isMoving() {
		return moving;
	}

	// TODO : Temporaire en attendant 'boolean setState(Etat state)'
	private void setMoving(boolean moving) {
		this.moving = moving;
	}

	private float getCurrentXPx() {
		return xPx;
	}

	private float getCurrentYPx() {
		return yPx;
	}

	private float getTargetXPx() {
		return xPxTarget;
	}

	private float getTargetYPx() {
		return yPxTarget;
	}

	private int getCurrentX() {
		return xCell;
	}

	private int getCurrentY() {
		return yCell;
	}

	private int getTargetX() {
		return xCellTarget;
	}

	private int getTargetY() {
		return yCellTarget;
	}

	public int getTeam() {
		return team;
	}

	private void setCurrentX(int x) {
		xCell = x;
	}

	private void setCurrentY(int y) {
		yCell = y;
	}

	private void setTargetX(int x) {
		xCellTarget = x;
		xPxTarget = GUI.cellToPixelX(xCellTarget);
	}

	private void setTargetY(int y) {
		yCellTarget = y;
		yPxTarget = GUI.cellToPixelY(yCellTarget);
	}

	private Direction getDirection() {
		return dir;
	}

	private void setDirection(Direction dir) {
		this.dir = dir;
	}

}
