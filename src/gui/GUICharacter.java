package gui;

import java.util.Map;
import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import entite.Direction;

import com.sun.org.apache.xpath.internal.operations.Bool;

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

	// Tableau etat -> booleen
	// Map<entite.Etat, Boolean> tableauEtat = new HashMap<entite.Etat,
	// Boolean>();
	// Pour l'instant : booleen moving
	private boolean moving;

	// Tableau action -> animation[] (plus tard)
	// Map<operateur.Action, Animation[]> listeAnimations = new
	// HashMap<operateur.Action, Animation[]>();
	// Pour l'instant : animation[]
	private final Animation[] animation_depl = new Animation[8];

	private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y, int animationDuration) {
		Animation animation = new Animation();
		for (int x = startX; x < endX; x++) {
			animation.addFrame(spriteSheet.getSprite(x, y), animationDuration);
		}
		return animation;
	}

	// TODO : modifier pour adapter a map
	public void init_animation(String spriteSheetLocation, int spriteSheetWidth, int spriteSheetHeight,
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
		this.init_animation(spriteSheetAnimation, 64, 64, 100);
	}

	public void render(Graphics g) {
		g.setColor(new Color(0, 0, 0, .5f));
		g.fillOval((int) xPx - 16, (int) yPx - 8, 32, 16);
		// -32 et -60 to center in cell
		g.drawAnimation(animation_depl[dir.toInt() + (isMoving() ? 4 : 0)], (int) xPx - 32, (int) yPx - 60);
	}

	protected void update(GUI gui, int delta) {
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

	public Direction getNextDirection() {
		if (isInPlace()) {
			return getDirection();
		} else {
			if (getCurrentY() > getTargetY()) {
				return Direction.NORTH;
			}
			if (getCurrentY() < getTargetY()) {
				return Direction.SOUTH;
			}
			if (getCurrentX() > getTargetX()) {
				return Direction.WEST;
			}
			if (getCurrentX() < getTargetX()) {
				return Direction.EAST;
			}
		}
		return null;
	}

	// TODO : Temporaire en attendant 'boolean isState(Etat state)'
	public boolean isMoving() {
		return moving;
	}

	// TODO : Temporaire en attendant 'boolean setState(Etat state)'
	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public float getCurrentXPx() {
		return xPx;
	}

	public float getCurrentYPx() {
		return yPx;
	}

	public float getTargetXPx() {
		return xPxTarget;
	}

	public float getTargetYPx() {
		return yPxTarget;
	}

	public int getCurrentX() {
		return xCell;
	}

	public int getCurrentY() {
		return yCell;
	}

	public int getTargetX() {
		return xCellTarget;
	}

	public int getTargetY() {
		return yCellTarget;
	}

	public void setCurrentX(int x) {
		xCell = x;
		// xPx = GUI.cellToPixelX(xCell);
	}

	public void setCurrentY(int y) {
		yCell = y;
		// xPx = GUI.cellToPixelY(xCell);
	}

	public void setTargetX(int x) {
		xCellTarget = x;
		xPxTarget = GUI.cellToPixelX(xCellTarget);
	}

	public void setTargetY(int y) {
		yCellTarget = y;
		yPxTarget = GUI.cellToPixelY(yCellTarget);
	}

	public Direction getDirection() {
		return dir;
	}

	public void setDirection(Direction dir) {
		this.dir = dir;
	}
}
