package gui;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import carte.Map;
import entite.Direction;
import moteurDuJeu.Engine;

public class GUI extends BasicGame {

	private GameContainer container;
	private TiledMap map;
	private static final int WindowHeight = 576;
	private static final int WindowWidth = 1088;
	private static final int TextFieldHeight = 50;

	protected static final int cellHeight = 32;
	protected static final int cellWidth = 32;

	private GUIBehaviorInput inputTextField;
	protected static boolean behaviorInputNeeded = false;

	private GUICharacter perso1;
	private GUICharacter perso2;

	public static void main(String[] args) throws SlickException {
		new Map();
		Map.initMap();
		new AppGameContainer(new GUI(), WindowWidth, WindowHeight, false).start();
	}

	public GUI() {
		super("STAR Wars");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		this.container = container;
		this.map = new TiledMap("res/map.tmx");
		this.perso1 = new GUICharacter(2, 4, entite.Direction.SOUTH, "res/SpriteSheetAnim.png");
		this.perso2 = new GUICharacter(30, 15, entite.Direction.SOUTH, "res/SpriteSheetAnim.png");
		this.inputTextField = new GUIBehaviorInput(container, WindowWidth, WindowHeight, TextFieldHeight, "{D3H | D}*");
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		this.map.render(0, 0, 3);
		this.map.render(0, 0, 0);
		this.map.render(0, 0, 1);
		this.map.render(0, 0, 2);
		this.perso1.render(g);
		this.perso2.render(g);
		this.map.render(0, 0, 4);
		this.map.render(0, 0, 5);

		if (behaviorInputNeeded) {
			this.inputTextField.render(container, g);
		}
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		perso1.update(this, delta);
		perso2.update(this, delta);
		this.inputTextField.update(container);
	}

	public static int pixelToCellX(float x) {
		return (int) (x - (x % cellWidth)) / cellWidth;
	}

	public static int pixelToCellY(float y) {
		return (int) (y - (y % cellHeight)) / cellHeight;
	}

	public static float cellToPixelX(int x) {
		return (x + 0.5f) * cellWidth;
	}

	public static float cellToPixelY(int y) {
		return (y + 0.5f) * cellHeight;
	}

	/**
	 * return true if there is an obstacle on the given cell of the GUI
	 * 
	 * @param x
	 *            x coordinate of the cell
	 * @param y
	 *            y coordinate of the cell
	 * @return
	 */
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
		if (Input.KEY_ESCAPE == key) {
			this.container.exit();
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_UP:
			Engine.update(Direction.NORTH, perso1);
			// perso1.goToDirection(Direction.NORTH);
			break;
		case Input.KEY_LEFT:
			Engine.update(Direction.WEST, perso1);
			// perso1.goToDirection(Direction.WEST);
			break;
		case Input.KEY_DOWN:
			Engine.update(Direction.SOUTH, perso1);
			// perso1.goToDirection(Direction.SOUTH);
			break;
		case Input.KEY_RIGHT:
			Engine.update(Direction.EAST, perso1);
			// perso1.goToDirection(Direction.EAST);
			break;
		case Input.KEY_Z:
			Engine.update(Direction.NORTH, perso2);
			// perso1.goToDirection(Direction.NORTH);
			break;
		case Input.KEY_Q:
			Engine.update(Direction.WEST, perso2);
			// perso1.goToDirection(Direction.WEST);
			break;
		case Input.KEY_W:
			Engine.update(Direction.SOUTH, perso2);
			// perso1.goToDirection(Direction.SOUTH);
			break;
		case Input.KEY_S:
			Engine.update(Direction.EAST, perso2);
			// perso1.goToDirection(Direction.EAST);
			break;
		}
	}
}