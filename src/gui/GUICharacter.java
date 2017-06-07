package gui;

import java.util.Map;
import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import entite.Direction;

// Contenu a rajouter a personnages.Personnage 
public class GUICharacter {

	// Coordinates in pixels
	private float xPx, yPx;
	// Coordinates to reach in pixels
	private float xPxTarget, yPxTarget;
	// Coordinates in cell's position in map
	private int xCell, yCell;
	// Coordinates to reach in cell's position in map
	private int xCellTarget, yCellTarget;

	private Direction dir;

	// TODO
	// Tableau etat -> booleen
	// Map<entite.Etat, Boolean> tableauEtat = new HashMap<entite.Etat,
	// Boolean>();
	// Pour l'instant : booleen moving
	private boolean moving;

	// TODO
	// Tableau action -> animation[] (plus tard)
	// Map<operateur.Action, Animation[]> listeAnimations = new
	// HashMap<operateur.Action, Animation[]>();
	// Pour l'instant : animation[]
	private final Animation[] animation_depl = new Animation[8];
	
	private boolean AckRequest;
	private boolean attacking;
	private int beginAck;
	private int AckDuration;
	private int realTime;

	private final Animation[] animation_atk = new Animation[8];

	private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y, int animationDuration) {
		Animation animation = new Animation();
		for (int x = startX; x < endX; x++) {
			animation.addFrame(spriteSheet.getSprite(x, y), animationDuration);
		}
		return animation;
	}

	// TODO : adapt to HashMap
	private void initAnimation(String spriteSheetLocation, int spriteSheetWidth, int spriteSheetHeight,
			int animationDuration) throws SlickException {
		SpriteSheet spriteSheet = new SpriteSheet(spriteSheetLocation, spriteSheetWidth, spriteSheetHeight);
		this.animation_depl[0] = loadAnimation(spriteSheet, 0, 1, 0, animationDuration);
		this.animation_depl[1] = loadAnimation(spriteSheet, 0, 1, 1, animationDuration);
		this.animation_depl[2] = loadAnimation(spriteSheet, 0, 1, 2, animationDuration);
		this.animation_depl[3] = loadAnimation(spriteSheet, 0, 1, 3, animationDuration);
		this.animation_depl[4] = loadAnimation(spriteSheet, 1, 9, 0, animationDuration);
		this.animation_depl[5] = loadAnimation(spriteSheet, 1, 9, 1, animationDuration);
		this.animation_depl[6] = loadAnimation(spriteSheet, 1, 9, 2, animationDuration);
		this.animation_depl[7] = loadAnimation(spriteSheet, 1, 9, 3, animationDuration);
	}

	private void initAnimationAtk(String spriteSheetLocation, int spriteSheetWidth, int spriteSheetHeight,
			int animationDuration) throws SlickException {
		SpriteSheet spriteSheet = new SpriteSheet("res/SpriteSheetAnimAttack.png", spriteSheetWidth, spriteSheetHeight);
		this.animation_atk[0] = loadAnimation(spriteSheet, 0, 1, 0, animationDuration);
		this.animation_atk[1] = loadAnimation(spriteSheet, 0, 1, 1, animationDuration);
		this.animation_atk[2] = loadAnimation(spriteSheet, 0, 1, 2, animationDuration);
		this.animation_atk[3] = loadAnimation(spriteSheet, 0, 1, 3, animationDuration);
		this.animation_atk[4] = loadAnimation(spriteSheet, 1, 6, 0, animationDuration);
		this.animation_atk[5] = loadAnimation(spriteSheet, 1, 6, 1, animationDuration);
		this.animation_atk[6] = loadAnimation(spriteSheet, 1, 6, 2, animationDuration);
		this.animation_atk[7] = loadAnimation(spriteSheet, 1, 6, 3, animationDuration);
		AckDuration = animationDuration*6;
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
	 * @param spriteSheetAnimation
	 *            Path to the sprite sheet of the moving animation (TODO : adapt
	 *            to any animation)
	 * @throws SlickException
	 *             Indicates a failure of the loading of a sprite sheet
	 */
	public GUICharacter(int x, int y, Direction dir, String spriteSheetAnimation) throws SlickException {
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
		this.initAnimationAtk(spriteSheetAnimation, 64, 64, 100);
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
		if (isAttacking()) {
			g.drawAnimation(animation_atk[dir.toInt() + (isAttacking() ? 4 : 0)], (int) xPx - 32, (int) yPx - 60);
		} else {
			g.drawAnimation(animation_depl[dir.toInt() + (isMoving() ? 4 : 0)], (int) xPx - 32, (int) yPx - 60);
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
			if(AckRequest){
				System.out.println("Ordonne l'attaque");
				setAckRequest(false);
				beginAck = (int) System.currentTimeMillis();
			}else{
				System.out.println("beginAck+Duration : " + beginAck + " -- " + AckDuration + " -- time : " + (int) System.currentTimeMillis());
				if((beginAck+AckDuration) <= (int) System.currentTimeMillis()){
					setAttacking(false);
					realTime = 0;
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
	private boolean isMoving() {
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

	public void Attack(Direction dir) {
		setDirection(dir);
		switch (dir) {
		case NORTH:
			setAttackTargetY(getCurrentY() - 1);
			break;
		case WEST:
			setAttackTargetX(getCurrentX() - 1);
			break;
		case SOUTH:
			setAttackTargetY(getCurrentY() + 1);
			break;
		case EAST:
			setAttackTargetX(getCurrentX() + 1);
			break;
		}
		setAckRequest(true);
		setAttacking(true);
	}
	
	private void setAckRequest(boolean ackRequest){
		this.AckRequest = ackRequest;
	}

	private boolean isAttacking() {
		return attacking;
	}

	private void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	private void setAttackTargetX(int i) {
		// TODO Attack the cell on the abscissa

	}

	private void setAttackTargetY(int i) {
		// TODO Attack the cell on the ordinate

	}
}
