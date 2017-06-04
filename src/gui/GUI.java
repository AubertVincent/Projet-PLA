package gui;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
//import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.tiled.TiledMap;

import java.awt.Font;
import org.newdawn.slick.UnicodeFont;

import entite.Direction;

//Fichier de test graphique

public class GUI extends BasicGame {

	private GameContainer container;
	private TiledMap map;
	private static final int WindowHeight = 576;
	private static final int WindowWidth = 1088;

	protected static final int cellHeight = 32;
	protected static final int cellWidth = 32;

	private GUIBehaviourInput inputTextField;

	private GUICharacter perso1;
	// private float x = 300, y = 300;
	// private int direction = 2;
	// private boolean moving = false;
	// private final Animation[] animations = new Animation[8];

	public static void main(String[] args) throws SlickException {
		new AppGameContainer(new GUI(), WindowWidth, WindowHeight, false).start();
	}

	public GUI() {
		super("STAR Wars");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		this.container = container;
		this.map = new TiledMap("res/map.tmx");
		this.perso1 = new GUICharacter(5, 12, entite.Direction.SOUTH, "res/SpriteSheetAnim.png");
		System.out.println("Position courante : (" + perso1.getCurrentX() + ", " + perso1.getCurrentY() + ").");
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		this.map.render(0, 0, 3);
		this.map.render(0, 0, 0);
		this.map.render(0, 0, 1);
		this.map.render(0, 0, 2);
		this.perso1.render(g);
		this.map.render(0, 0, 4);
		this.map.render(0, 0, 5);
		// this.inputTextField.render(container, g);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		perso1.update(this, delta);
	}

	public static int pixelToCellX(float x) {
		// System.out.println("pixelToCellX(" + x + ") = " + ((int) (x - (0.5 *
		// cellWidth)) / cellWidth + 1));
		return (int) (x - (0.5 * cellWidth)) / cellWidth + 1;

	}

	public static int pixelToCellY(float y) {
		// System.out.println("pixelToCellY(" + y + ") = " + ((int) (y - (0.5 *
		// cellWidth)) / cellWidth + 1));
		return (int) (y - (0.5 * cellWidth)) / cellWidth + 1;
	}

	public static float cellToPixelX(int x) {
		return (x + 0.5f) * cellWidth;
	}

	public static float cellToPixelY(int y) {
		return (y + 0.5f) * cellHeight;
	}

	protected boolean isObstacle(float x, float y) {
		int tileW = this.map.getTileWidth();
		int tileH = this.map.getTileHeight();
		int logicLayer = this.map.getLayerIndex("obstacles");
		Image tile = this.map.getTileImage((int) x / tileW, (int) y / tileH, logicLayer);
		boolean collision = tile != null;
		if (collision) {
			Color color = tile.getColor((int) x % tileW, (int) y % tileH);
			collision = color.getAlpha() > 0;
		}
		return collision;
	}

	@Override
	public void keyReleased(int key, char c) {
		this.perso1.setMoving(false);
		if (Input.KEY_ESCAPE == key) {
			this.container.exit();
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_UP:
			perso1.setDirection(Direction.NORTH);
			System.out.println("A droite");
			System.out.println("Position x (px) : " + xPx + "position y" );
			// Coordinates in pixels
			private float xPx, yPx;
			// Coordinates to reach in pixels
			private float xPxTarget, yPxTarget;
			// Coordinates in cell's position in map
			private int xCell, yCell;
			// Coordinates to reach in cell's position in map
			private int xCellTarget, yCellTarget;

			System.out.println("On demande : getCurrentY -> " + (perso1.getCurrentY() - 1));
			perso1.setTargetY(perso1.getCurrentY() - 1);
			break;
		case Input.KEY_LEFT:
			perso1.setDirection(Direction.WEST);
			System.out.println("On demande : getCurrentX -> " + (perso1.getCurrentX() - 1));
			perso1.setTargetX(perso1.getCurrentX() - 1);
			break;
		case Input.KEY_DOWN:
			perso1.setDirection(Direction.SOUTH);
			System.out.println("On demande : getCurrentY -> " + (perso1.getCurrentY() + 1));
			perso1.setTargetY(perso1.getCurrentY() + 1);
			break;
		case Input.KEY_RIGHT:
			perso1.setDirection(Direction.EAST);
			System.out.println("On demande : getCurrentX -> " + (perso1.getCurrentX() + 1));
			perso1.setTargetX(perso1.getCurrentX() + 1);
			break;
		}
	}
}