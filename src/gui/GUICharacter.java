package gui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import carte.Coordinates;
import entite.Direction;
import entite.Team;
import exceptions.NotDoableException;
import moteurDuJeu.Engine;
import operateur.Action;
import personnages.Player;
import personnages.Robot;

// Contenu a rajouter a personnages.Personnage 
public abstract class GUICharacter {

	private static final int spriteSheetWidth = 64;
	private static final int spriteSheetHeight = 64;

	GUI mainUserInterface;

	// Coordinates in pixels
	private float xPx, yPx;
	// Coordinates to reach in pixels
	private float xPxTarget, yPxTarget;
	// Coordinates in cell's position in map
	private Coordinates coordCell;
	// Coordinates to reach in cell's position in map
	private Coordinates coordCellTarget;

	private Direction dir;

	// TODO
	// Tableau etat -> booleen
	// Map<entite.Etat, Boolean> tableauEtat = new HashMap<entite.Etat,
	// Boolean>();
	// Pour l'instant : booleen moving
	private boolean moving;

	// Tableau action -> animation[]
	Map<Class<? extends operateur.Action>, Animation[]> animationsList = new HashMap<Class<? extends operateur.Action>, Animation[]>();

	// Pour l'instant : animation[]

	private boolean AckRequest;
	private boolean attacking;
	private int beginAck;
	private int AckDuration;

	private Team team;

	protected Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y, int animationDuration) {
		Animation animation = new Animation();
		for (int x = startX; x < endX; x++) {
			animation.addFrame(spriteSheet.getSprite(x, y), animationDuration);
		}
		return animation;
	}

	protected void initAnimations(int animationDuration) throws SlickException, Exception {

		// Get possibleActionList of the current Character
		List<Class<? extends Action>> possibleActionList;
		if (this instanceof GUIPlayer) {
			possibleActionList = Player.getPossibleActionsList();
		} else if (this instanceof GUIRobot) {
			possibleActionList = Robot.getPossibleActionsList();
		} else {
			throw new Exception("Unknown Character subclass");
		}
		// load the animation of each action element of the possibleActionList
		for (Iterator<Class<? extends Action>> action = possibleActionList.iterator(); action.hasNext();) {
			Class<? extends Action> currentAction = action.next();
			SpriteSheet currentSpriteSheet = null;
			int currentNumberOfSprites = 0;
			if (this instanceof GUIPlayer) {
				currentSpriteSheet = new SpriteSheet(GUIPlayer.actionSpritePath.get(currentAction), spriteSheetWidth,
						spriteSheetHeight);
				currentNumberOfSprites = GUIPlayer.actionSpriteNumberOfSprites.get(currentAction);
			} else if (this instanceof GUIRobot) {
				currentSpriteSheet = new SpriteSheet(GUIRobot.actionSpritePath.get(currentAction), spriteSheetWidth,
						spriteSheetHeight);
				currentNumberOfSprites = GUIRobot.actionSpriteNumberOfSprites.get(currentAction);
			} else {
				throw new Exception("Unknown Charcter subclass");
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

			// System.out.println(this);
			// System.out.println(currentAnimation.toString());
			// System.out.println(currentAction.toString());

			animationsList.put(currentAction, currentAnimation);

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
	public GUICharacter(GUI userInterface, Coordinates coord, Direction dir, int animationDuration, Team team)
			throws SlickException, Exception {

		super();
		this.mainUserInterface = userInterface;
		this.coordCell = coord;
		setTargetCoord(getCurrentCoord());
		this.xPx = mainUserInterface.cellToPixelX(getCurrentCoord().getX());
		this.yPx = mainUserInterface.cellToPixelY(getCurrentCoord().getY());
		this.dir = dir;
		this.setMoving(false);
		initAnimations(animationDuration);

		// TODO If animation is longer than animationDuration, set it here
		AckDuration = animationDuration * 6;

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
		// -32 and -60 to center in cell
		if (isAttacking()) {
			g.drawAnimation(animationsList.get(operateur.ClassicAck.class)[dir.toInt() + (isAttacking() ? 4 : 0)],
					(int) xPx - 32, (int) yPx - 60);
		} else {
			g.drawAnimation(animationsList.get(operateur.MoveDir.class)[dir.toInt() + (isMoving() ? 4 : 0)],
					(int) xPx - 32, (int) yPx - 60);
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
		if (isAttacking()) {
			if (AckRequest) {
				System.out.println("Ordonne l'attaque");
				setAckRequest(false);
				beginAck = (int) System.currentTimeMillis();
			} else {
				if ((beginAck + AckDuration) <= (int) System.currentTimeMillis()) {
					setAttacking(false);
				}
			}
		}

		if (isMoving()) {

			setAttacking(false);

			float nextXPx = getCurrentXPx(), nextYPx = getCurrentYPx();

			if (getDirection() == Direction.WEST || getDirection() == Direction.EAST) {
				nextXPx = getNextXPx(delta);
			} else {
				nextYPx = getNextYPx(delta);
			}

			if (isInPlace()) {
				setMoving(false);
			} else {
				int nextCellX = mainUserInterface.pixelToCellX(nextXPx);
				int nextCellY = mainUserInterface.pixelToCellY(nextYPx);
				Coordinates nextCellCoord = new Coordinates(nextCellX, nextCellY);
				if (gui.isObstacle(nextCellCoord)) {
					System.out.println("Obstacle détecté :|");
					setMoving(false);
				} else {
					this.xPx = nextXPx;
					this.yPx = nextYPx;
					setCurrentCoord(nextCellCoord);
				}
			}
		}
	}

	public Team getTeam() {
		return this.team;
	}

	protected void movePlayer(Engine engine, Direction direction) {
		if (!isMoving() && !isAttacking()) {
			if (engine.doMove(direction, this, engine.getMap())) {
				this.goToDirection(direction);
			}
		}
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

		return getCurrentCoord() == getTargetCoord() && isInPlaceHeight && isInPlaceWidth;
	}

	/**
	 * Makes the GUICharacter move of one cell in the given direction
	 * 
	 * @param dir
	 *            The direction in which the GUICharacter will move
	 */
	public void goToDirection(Direction dir) {
		if (!isMoving() && !isAttacking()) {

			Coordinates coordTarget = getTargetCoord();
			setDirection(dir);
			switch (dir) {
			case NORTH:
				coordTarget.setY(getCurrentCoord().getY() - 1);
				break;
			case WEST:
				coordTarget.setX(getCurrentCoord().getX() - 1);
				break;
			case SOUTH:
				coordTarget.setY(getCurrentCoord().getY() + 1);
				break;
			case EAST:
				coordTarget.setX(getCurrentCoord().getX() + 1);
				break;
			}
			setTargetCoord(coordTarget);
			setMoving(true);
		}
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

	// TODO : Temporary until 'void setState(Etat state, boolean bool)'
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

	public Coordinates getCurrentCoord() {
		return coordCell;
	}

	public Coordinates getTargetCoord() {
		return coordCellTarget;
	}

	private void setCurrentCoord(Coordinates coord) {
		coordCell = coord;
	}

	private void setTargetCoord(Coordinates coord) {
		coordCellTarget = coord;
		xPxTarget = mainUserInterface.cellToPixelX(coordCellTarget.getX());
		yPxTarget = mainUserInterface.cellToPixelY(coordCellTarget.getY());
	}

	private Direction getDirection() {
		return dir;
	}

	private void setDirection(Direction dir) {
		this.dir = dir;
	}

	public void Attack(Engine engine, Direction dir) throws NotDoableException {
		try {
			if (!isMoving() && !isAttacking()) {
				setDirection(dir);
				setAttackTarget(dir);
				engine.doAttack(dir, this, engine.ma_map);
				setAttackTarget(dir);

				setAckRequest(true);
				setAttacking(true);
			}
		} catch (NotDoableException e) {
			throw new NotDoableException("Personne à attaquer");
		}
	}

	private void setAckRequest(boolean ackRequest) {
		this.AckRequest = ackRequest;
	}

	private boolean isAttacking() {
		return attacking;
	}

	private void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	private void setAttackTarget(Direction dir) {
		// TODO Attack the cell on the abscissa

	}

}
