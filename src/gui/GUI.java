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

import entite.Direction;
import moteurDuJeu.Engine;

public class GUI extends BasicGame {

	private GameContainer container;
	private static TiledMap map;
	private final static int WindowHeight = 576;
	private final static int WindowWidth = 1088;
	private final int TextFieldHeight = 50;

	protected static final int cellHeight = 32;
	protected static final int cellWidth = 32;

	private GUIBehaviorInput inputTextField;
	protected static boolean behaviorInputNeeded = false;

	private GUICharacter perso1;
	private GUICharacter perso2;

	// private Map ma_map;
	private Engine engine;

	public static void main(String[] args) throws SlickException {

		new AppGameContainer(new GUI(), WindowWidth, WindowHeight, false).start();
	}

	public GUI() {
		super("STAR Wars");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		this.container = container;
		map = new TiledMap("res/map.tmx");
		this.perso1 = new GUICharacter(2, 4, entite.Direction.SOUTH, "res/SpriteSheetAnim.png");
		this.perso2 = new GUICharacter(31, 15, entite.Direction.SOUTH, "res/SpriteSheetAnim.png");
		this.inputTextField = new GUIBehaviorInput(container, WindowWidth, WindowHeight, TextFieldHeight, "{D3H | D}*");
		engine = new Engine(this);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		map.render(0, 0, 3);
		map.render(0, 0, 0);
		map.render(0, 0, 1);
		map.render(0, 0, 2);
		perso1.render(g);
		perso2.render(g);
		map.render(0, 0, 4);
		map.render(0, 0, 5);

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
	public boolean isCollision(float x, float y) {
		int tileW = map.getTileWidth();
		int tileH = map.getTileHeight();
		int logicLayer = map.getLayerIndex("obstacles");
		Image tile = map.getTileImage((int) x / tileW, (int) y / tileH, logicLayer);
		boolean collision = tile != null;
		if (collision) {
			Color color = tile.getColor((int) x % tileW, (int) y % tileH);
			collision = color.getAlpha() > 0;
		}
		return collision;
	}

	public boolean isObstacle(float x, float y) {
		int tileW = map.getTileWidth();
		int tileH = map.getTileHeight();
		int logicLayer = map.getLayerIndex("obstacles");
		Image tile = map.getTileImage((int) x / tileW, (int) y / tileH, logicLayer);
		boolean collision = tile != null;
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
		// if (!perso1.isMoving() && !perso2.isMoving()) {
		switch (key) {
		case Input.KEY_UP:
			if (!perso1.isMoving()) {
				engine.update(Direction.NORTH, perso1, 1, engine.ma_map, this);
			}
			break;
		case Input.KEY_LEFT:
			if (!perso1.isMoving()) {
				engine.update(Direction.WEST, perso1, 1, engine.ma_map, this);
			}
			break;
		case Input.KEY_DOWN:
			if (!perso1.isMoving()) {

				engine.update(Direction.SOUTH, perso1, 1, engine.ma_map, this);
			}
			break;
		case Input.KEY_RIGHT:
			if (!perso1.isMoving()) {
				engine.update(Direction.EAST, perso1, 1, engine.ma_map, this);
			}
			break;
		case Input.KEY_Z:
			if (!perso2.isMoving()) {
				engine.update(Direction.NORTH, perso2, 2, engine.ma_map, this);
			}
			break;
		case Input.KEY_Q:
			if (!perso2.isMoving()) {
				engine.update(Direction.WEST, perso2, 2, engine.ma_map, this);
			}
			break;
		case Input.KEY_S:
			if (!perso2.isMoving()) {
				engine.update(Direction.SOUTH, perso2, 2, engine.ma_map, this);
			}
			break;
		case Input.KEY_D:
			if (!perso2.isMoving()) {
				engine.update(Direction.EAST, perso2, 2, engine.ma_map, this);
			}
			break;
		}
		// }
	}
}