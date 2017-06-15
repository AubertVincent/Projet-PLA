package gui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import entite.Direction;
import entite.Team;
import personnages.Character;
import personnages.Player;
import personnages.Robot;
import personnages.State;

// Contenu a rajouter a personnages.Personnage 
public abstract class GUICharacter {

	private static final int spriteSheetWidth = 64;
	private static final int spriteSheetHeight = 64;

	protected GUI mainUserInterface;

	// Coordinates in pixels
	private float xPx, yPx;
	// Coordinates to reach in pixels
	private float xPxTarget, yPxTarget;
	// Coordinates in cell's position in map
	private int xCell, yCell;
	// Coordinates to reach in cell's position in map
	private int xCellTarget, yCellTarget;

	private Direction dir;

	private Team team;

	// Tableau action -> animation[]
	Map<Class<?>, Animation[]> animationsList = new HashMap<Class<?>, Animation[]>();
	Animation[] deathAnimation;

	// Pour l'instant : animation[]

	private boolean actionRequest;
	private int animationDuration;
	private int beginTimeAnimation;

	private Character mySelf;

	protected Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y, int animationDuration) {
		Animation animation = new Animation();
		for (int x = startX; x < endX; x++) {
			animation.addFrame(spriteSheet.getSprite(x, y), animationDuration);
		}
		return animation;
	}

	protected void initAnimations(int animationDuration) {

		// Get possibleActionList of the current Character
		List<Class<?>> possibleActionList = new LinkedList<Class<?>>();
		if (this instanceof GUIPlayer) {
			possibleActionList = Player.getPossibleActionsList();
		} else if (this instanceof GUIRobot) {
			possibleActionList = Robot.getPossibleActionsList();
		} else {
			try {
				throw new Exception("Unknown Character subclass");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// load the animation of each action element of the possibleActionList
		for (Iterator<Class<?>> action = possibleActionList.iterator(); action.hasNext();) {
			Class<?> currentAction = action.next();
			SpriteSheet currentSpriteSheet = null;
			int currentNumberOfSprites = 0;
			if (this instanceof GUIPlayer) {
				try {
					currentSpriteSheet = new SpriteSheet(GUIPlayer.actionSpritePath.get(currentAction),
							spriteSheetWidth, spriteSheetHeight);
				} catch (SlickException e) {
					e.printStackTrace();
				}
				currentNumberOfSprites = GUIPlayer.actionSpriteNumberOfSprites.get(currentAction);
			} else if (this instanceof GUIRobot) {
				try {
					currentSpriteSheet = new SpriteSheet(GUIRobot.actionSpritePath.get(currentAction), spriteSheetWidth,
							spriteSheetHeight);
				} catch (SlickException e) {
					e.printStackTrace();
				}
				currentNumberOfSprites = GUIRobot.actionSpriteNumberOfSprites.get(currentAction);
			} else {
				try {
					throw new Exception("Unknown Charcter subclass");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			Animation[] currentAnimation = new Animation[8];

			currentAnimation[0] = loadAnimation(currentSpriteSheet, 0, 1, 0, animationDuration);
			currentAnimation[1] = loadAnimation(currentSpriteSheet, 0, 1, 1, animationDuration);
			currentAnimation[2] = loadAnimation(currentSpriteSheet, 0, 1, 2, animationDuration);
			currentAnimation[3] = loadAnimation(currentSpriteSheet, 0, 1, 3, animationDuration);
			currentAnimation[4] = loadAnimation(currentSpriteSheet, 1, currentNumberOfSprites, 0, animationDuration);
			currentAnimation[5] = loadAnimation(currentSpriteSheet, 1, currentNumberOfSprites, 1, animationDuration);
			currentAnimation[6] = loadAnimation(currentSpriteSheet, 1, currentNumberOfSprites, 2, animationDuration);
			currentAnimation[7] = loadAnimation(currentSpriteSheet, 1, currentNumberOfSprites, 3, animationDuration);

			animationsList.put(currentAction, currentAnimation);

		}

		// Get death animation informations and then load it

		int deathNumberOfSprites;
		SpriteSheet deathtSpriteSheet;
		if (this instanceof GUIPlayer) {
			deathNumberOfSprites = 6;
			try {
				deathtSpriteSheet = new SpriteSheet("res/Player/SpriteSheetDeath.png", spriteSheetWidth,
						spriteSheetHeight);
				// Load death animation
				deathAnimation = new Animation[1];
				deathAnimation[0] = loadAnimation(deathtSpriteSheet, 0, deathNumberOfSprites, 0, animationDuration);

			} catch (SlickException e) {
				e.printStackTrace();
			}
		} else if (this instanceof GUIRobot) {
			deathNumberOfSprites = 6;
			try {
				deathtSpriteSheet = new SpriteSheet("res/Robot/SpriteSheetDeath.png", spriteSheetWidth,
						spriteSheetHeight);
				// Load death animation
				deathAnimation = new Animation[1];
				deathAnimation[0] = loadAnimation(deathtSpriteSheet, 0, deathNumberOfSprites, 0, animationDuration);

			} catch (SlickException e) {
				e.printStackTrace();
			}
		} else {
			try {
				throw new Exception("Unimplemented GUICharacter subclass");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

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
	 * @param animationDuration
	 *            Atomic duration of an animation (in milliseconds)
	 * @throws SlickException
	 *             Indicates a failure of the loading of a sprite sheet
	 */

	public GUICharacter(GUI userInterface, int x, int y, Direction dir, int animationDuration, Team team,
			Character character) {

		super();
		this.mainUserInterface = userInterface;
		this.xCell = x;
		this.yCell = y;
		setTargetX(getCurrentX());
		setTargetY(getCurrentY());
		this.xPx = mainUserInterface.cellToPixelX(getCurrentX());
		this.yPx = mainUserInterface.cellToPixelY(getCurrentY());
		this.dir = dir;
		initAnimations(animationDuration);

		this.animationDuration = animationDuration * 6;

		this.team = team;
		this.mySelf = character;
	}

	/**
	 * Renders the GUICharacters in the given Graphics
	 * 
	 * @param g
	 *            A Graphics to represent the GUICharacter in
	 * @throws Exception
	 */
	public void render(Graphics g) throws Exception {
		g.setColor(new Color(0, 0, 0, .5f));
		g.fillOval((int) xPx - 16, (int) yPx - 8, 32, 16);
		// -32 et -60 to center in cell

		int xAnim = (int) xPx - 32;
		int yAnim = (int) yPx - 60;

		switch (mySelf.getState()) {
		case ClassiqueMove:
			g.drawAnimation(animationsList.get(operateur.MoveDir.class)[dir.toInt() + 4], xAnim, yAnim);
			break;
		case TeleportMove:

			break;
		case ClassicAttack:
			g.drawAnimation(animationsList.get(operateur.ClassicAck.class)[dir.toInt() + 4], xAnim, yAnim);
			break;
		case SuicideBomberAttack:
			g.drawAnimation(animationsList.get(operateur.SuicideBomber.class)[dir.toInt() + 4], xAnim, yAnim);

			break;
		case Dying:
			g.drawAnimation(deathAnimation[0], xAnim, yAnim);

			break;
		case RobotCreation:
			g.drawAnimation(animationsList.get(operateur.CreateRobot.class)[dir.toInt() + 4], xAnim, yAnim);
			break;
		case Wait:
			g.drawAnimation(animationsList.get(operateur.MoveDir.class)[dir.toInt()], xAnim, yAnim);
			break;
		case Dead:
			// throw new Exception("Trying to render a dead character");
		}
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
		setDirection(getMyself().getDirection());
		switch (mySelf.getState()) {
		case ClassiqueMove:
			float nextXPx = getCurrentXPx(), nextYPx = getCurrentYPx();

			if (getDirection() == Direction.WEST || getDirection() == Direction.EAST) {
				nextXPx = getNextXPx(delta);
			} else {
				nextYPx = getNextYPx(delta);
			}

			if (isInPlace()) {
				mySelf.setState(State.Wait);
				setCurrentX(getTargetX());
				setCurrentY(getTargetY());
			} else {
				int nextCellX = mainUserInterface.pixelToCellX(nextXPx);
				int nextCellY = mainUserInterface.pixelToCellY(nextYPx);
				if (gui.isObstacle(nextCellX, nextCellY)) {
					mySelf.setState(State.Wait);
				} else {
					this.xPx = nextXPx;
					setCurrentX(nextCellX);
					this.yPx = nextYPx;
					setCurrentY(nextCellY);
				}
			}
			break;
		case TeleportMove:
			if (actionRequest) {
				beginTimeAnimation = (int) System.currentTimeMillis();
				actionRequest = false;
			}
			if ((beginTimeAnimation + animationDuration) <= (int) System.currentTimeMillis()) {
				setCurrentX(mySelf.getX());
				setCurrentY(mySelf.getY());
				xPx = mainUserInterface.cellToPixelX(mySelf.getX());
				yPx = mainUserInterface.cellToPixelY(mySelf.getY());
				mySelf.setState(State.Wait);
			}
			break;
		case ClassicAttack:
			if (actionRequest) {
				beginTimeAnimation = (int) System.currentTimeMillis();
				actionRequest = false;
			}
			if ((beginTimeAnimation + this.animationDuration) <= (int) System.currentTimeMillis()) {
				mySelf.setState(State.Wait);
			}
			break;
		case SuicideBomberAttack:
			if (actionRequest) {
				beginTimeAnimation = (int) System.currentTimeMillis();
				actionRequest = false;
			}
			if ((beginTimeAnimation + animationDuration) <= (int) System.currentTimeMillis()) {
				mySelf.setState(State.Dying);
			}
			break;
		case Dying:
			if (actionRequest) {
				beginTimeAnimation = (int) System.currentTimeMillis();
				actionRequest = false;
			}
			if ((beginTimeAnimation + animationDuration) <= (int) System.currentTimeMillis()) {
				mySelf.setState(State.Dead);
			}
			break;
		case Dead:
			// State dead means that dying animation is over means that the
			// Character should be deleted
			// It's achieved by calling Die and setting it to null
			mySelf.die();
			mySelf = null;
			break;
		case RobotCreation:
			if (actionRequest) {
				beginTimeAnimation = (int) System.currentTimeMillis();
				actionRequest = false;
			}
			if ((beginTimeAnimation + animationDuration) <= (int) System.currentTimeMillis()) {
				mySelf.setState(State.Wait);
			}
			break;
		case Wait:
			break;
		}

	}

	public Team getTeam() {
		return this.team;
	}

	// returns true if GUIChararcter is in targetCell
	private boolean isInPlace() {

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

	private float getCurrentXPx() {
		return xPx;
	}

	private float getCurrentYPx() {
		return yPx;
	}

	private float getTargetXPx() {
		int xplayer = this.getMyself().getX();
		return mainUserInterface.cellToPixelX(xplayer);
		// return xPxTarget;
	}

	private float getTargetYPx() {
		int yplayer = this.getMyself().getY();
		return mainUserInterface.cellToPixelY(yplayer);
		// return yPxTarget;
	}

	public int getCurrentX() {
		return xCell;
	}

	public int getCurrentY() {
		return yCell;
	}

	private int getTargetX() {
		return mySelf.getX();
	}

	private int getTargetY() {
		return mySelf.getY();
	}

	private void setCurrentX(int x) {
		xCell = x;
	}

	private void setCurrentY(int y) {
		yCell = y;
	}

	private void setTargetX(int x) {
		xCellTarget = x;
		xPxTarget = mainUserInterface.cellToPixelX(xCellTarget);
	}

	private void setTargetY(int y) {
		yCellTarget = y;
		yPxTarget = mainUserInterface.cellToPixelY(yCellTarget);
	}

	private Direction getDirection() {
		return dir;
	}

	private void setDirection(Direction dir) {
		this.dir = dir;
	}

	public abstract Character getMyself();

	public GUI getGUI() {
		return mainUserInterface;
	}

	public void setActionRequest(boolean actionRequest) {
		this.actionRequest = actionRequest;
	}
}
