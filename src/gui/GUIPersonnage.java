package gui;

import java.util.Map;
import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.sun.org.apache.xpath.internal.operations.Bool;

// Contenu a rajouter a personnages.Personnage 
public class GUIPersonnage {

	private float x, y;
	private int x_pieds = (int) x - 32, y_pieds = (int) y - 60;
	private entite.Direction dir;

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

	public GUIPersonnage(float x, float y, entite.Direction dir, String spriteSheetAnimation) throws SlickException {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.setMoving(false);
		this.init_animation(spriteSheetAnimation, 64, 64, 100);
	}

	public void renderGUIPersonnage(Graphics g) {
		g.setColor(new Color(0, 0, 0, .5f));
		//g.fillOval((int) x - 16, (int) y - 8, 32, 16);
		g.drawAnimation(animation_depl[dir.toInt() + (isMoving() ? 4 : 0)], (int) x - 32, (int) y - 60);

	}

	protected void updateGUIPersonnage(GUI gui, int delta) {
		if (this.isMoving()) {
			float futurX = getFuturX(delta);
			float futurY = getFuturY(delta);
			boolean collision = gui.isCollision(futurX, futurY);
			if (collision) {
				this.setMoving(false);
			} else {
				this.x = futurX;
				this.y = futurY;
			}
		}
	}

	private float getFuturX(int delta) {
		float futurX = this.x;
		switch (this.dir) {
		case OUEST:
			futurX = this.x - .1f * delta;
			break;
		case EST:
			futurX = this.x + .1f * delta;
			break;
		default:
			break;
		}
		return futurX;
	}

	private float getFuturY(int delta) {
		float futurY = this.y;
		switch (this.dir) {
		case NORD:
			futurY = this.y - .1f * delta;
			break;
		case SUD:
			futurY = this.y + .1f * delta;
			break;
		default:
			break;
		}
		return futurY;
	}

	public entite.Direction getDirection() {
		return dir;
	}

	public void setDirection(entite.Direction dir) {
		this.dir = dir;
	}

	// TODO : Temporaire en attendant 'boolean isState(Etat state)'
	public boolean isMoving() {
		return moving;
	}

	// TODO : Temporaire en attendant 'boolean setState(Etat state)'
	public void setMoving(boolean moving) {
		this.moving = moving;
	}

}
