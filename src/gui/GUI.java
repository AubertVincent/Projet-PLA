package gui;

import java.util.Iterator;

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
import entite.Team;
import moteurDuJeu.Engine;
import moteurDuJeu.PlayPhase;
import personnages.Player;
import personnages.Robot;
import pickable.PickAble;
import pickable.PickPickUp;

public class GUI extends BasicGame {

	private GameContainer container;
	private TiledMap map;

	private final int WindowHeight;
	private final int WindowWidth;

	private final int cellHeight;
	private final int cellWidth;

	private GUIBehaviorInput inputTextField;

	private boolean behaviorInputNeeded;

	private Engine engine;

	private GUIBesace rectBesace;
	private int WidthRect = 32 * 20;
	private int HeightRect = 300;
	private PlayPhase phase;
	private String guiphase;
	private Image image;

	/**
	 * The method used to launch the game
	 * 
	 * @param args
	 * @throws SlickException
	 */
	public static void main(String[] args) throws SlickException {
		GUI mainUI = new GUI();
		new AppGameContainer(new GUI(), mainUI.WindowWidth, mainUI.WindowHeight, false).start();
	}

	// ↓ Constructor, update and render ↓

	/**
	 * Creates the game window with firmed height and width
	 * 
	 * @throws SlickException
	 */
	public GUI() throws SlickException {
		super("STAR Wars");
		WindowHeight = 576;
		WindowWidth = 1088;
		cellHeight = 32;
		cellWidth = 32;

	}

	/**
	 * Updates the whole GUI. Is responsible for calling every update method
	 * located in gui package
	 */
	@Override
	public void update(GameContainer container, int delta) throws SlickException {

		if (behaviorInputNeeded) {
			this.inputTextField.update(container, engine.getCurrentModifier());
		}

		// FIXME
		for (Player currentPlayer : engine.getPlayerList()) {
			GUIPlayer guiCurrentPlayer = currentPlayer.getMyselfGUI();
			guiCurrentPlayer.update(this, delta);
			for (Robot currentRobot : currentPlayer.getRobotList()) {
				GUIRobot guiCurrentRobot = currentRobot.getMyselfGUI();
				guiCurrentRobot.update(this, delta);
			}
		}
		if (inputTextField.getUpdateness()) {
			inputTextField.setUpdateness(false);
			setBehaviorInputNeeded(false);
			engine.setRobotBehavior(this, inputTextField.getReceivedSequence());
		}

		if (engine.everyoneWaiting() && engine.getPlayPhase().equals(PlayPhase.automatonExecution)) {
			engine.step();
		}
		setBehaviorInputNeeded(false);

	}

	/**
	 * Renders the whole GUI. Is responsible for calling every render method
	 * located in gui package
	 */
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		map.render(0, 0, 0);
		map.render(0, 0, 1);
		map.render(0, 0, 2);
		map.render(0, 0, 3);

		g.setColor(Color.white);
		phase = engine.getPlayPhase();
		guiphase = PlayPhase.toString(phase);
		g.drawString(guiphase, WindowWidth / 3, 4);
		g.drawString("33.00", WindowWidth - 50, 4);
		g.drawString("33.17", WindowWidth - 55, WindowHeight - 25);
		g.drawString("00.17", 5, WindowHeight - 25);

		for (Iterator<PickAble> itr = engine.getMap().getPickAbleList().iterator(); itr.hasNext();) {
			PickAble currentPickable = itr.next();
			if (!(currentPickable instanceof PickPickUp)) {
				currentPickable.getGUIPickAble().render();
			}
		}

		for (Iterator<Player> itrPlayer = engine.getPlayerList().iterator(); itrPlayer.hasNext();) {
			Player currentPlayer = itrPlayer.next();
			GUIPlayer guiPlayer = currentPlayer.getMyselfGUI();
			try {
				guiPlayer.render(g);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			for (Iterator<Robot> itrRobot = currentPlayer.getRobotList().iterator(); itrRobot.hasNext();) {
				Robot currentRobot = itrRobot.next();
				GUIRobot guiRobot = currentRobot.getMyselfGUI();
				try {
					if (guiRobot.getMyself().getIsVisible()) {
						guiRobot.render(g);
					}
				} catch (Exception e) {

					e.printStackTrace();
				}
			}

		}
		map.render(0, 0, 5);
		map.render(0, 0, 6);
		if (behaviorInputNeeded) {
			this.rectBesace.render(container, g, engine.getCurrentModifier().getBesace());
			this.inputTextField.render(container, g);
		}

		if (engine.getPlayPhase().equals(PlayPhase.endOfGame)) {
			if (engine.getWinningTeam().equals(Team.ROUGE)) {
				image = new Image("res/RedWinning.png");
				image.draw(0, 0);
			} else if (engine.getWinningTeam().equals(Team.BLEU)) {
				image = new Image("res/BlueWinning.png");
				image.draw(0, 0);
			}
		}
	}

	// End(Constructor, update and render)

	// ↓ Miscellaneous methods ↓

	/**
	 * Initialiser of the game window, automatically called, loads the map and
	 * the engine
	 */
	@Override
	public void init(GameContainer container) throws SlickException {

		this.container = container;
		map = new TiledMap("res/map.tmx");
		inputTextField = new GUIBehaviorInput(container, this);
		rectBesace = new GUIBesace(container, WindowHeight, WidthRect, HeightRect, cellWidth);

		engine = new Engine(this);

		setBehaviorInputNeeded(false);

	}

	/**
	 * Returns true if there is another Character on the given cell of the GUI
	 * 
	 * @param x
	 *            x coordinate of the cell
	 * @param y
	 *            y coordinate of the cell
	 * @return true if there is another Character on the given cell of the GUI,
	 *         false otherwise
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

	/**
	 * Returns true if there is an obstacle on the given cell of the GUI
	 * 
	 * @param x
	 *            x coordinate of the cell
	 * @param y
	 *            y coordinate of the cell
	 * @return true if there is an obstacle on the given cell of the GUI, false
	 *         otherwise
	 */
	public boolean isObstacle(int x, int y) {
		int logicLayer = map.getLayerIndex("obstacles");
		Image tile = map.getTileImage(x, y, logicLayer);
		boolean collision = tile != null;
		return collision;
	}

	/**
	 * Called whenever a button of the mouse is pressed, used to launch a robot
	 * creation/behavior modification
	 * 
	 * @param button
	 *            Refers to the pressed button
	 * @param x
	 *            x coordinate of the mouse at the moment of the click
	 * @param y
	 *            y coordinate of the mouse at the moment of the click
	 */
	@Override
	public void mousePressed(int button, int x, int y) {
		int mouseXCell = pixelToCellX(x);
		int mouseYCell = pixelToCellY(y);
		System.out.println("LeftClick on (" + mouseXCell + ", " + mouseYCell + ")");

		if (getEngine().getPlayPhase().equals(PlayPhase.behaviorModification)) {

			GUICharacter guiPerso;

			guiPerso = engine.getGUICharactereFromMouse(mouseXCell, mouseYCell);
			try {
				if (guiPerso != null) {

					if (guiPerso instanceof GUIPlayer) {
						engine.behaviorCreation(this, (Player) guiPerso.getMyself());
					} else {
						try {
							engine.behaviorModification(this, (Robot) guiPerso.getMyself());
						} catch (Exception e) {
							e.getMessage();
						}
					}
				}
			} catch (Exception e) {
				;
			}
		}
	}

	/**
	 * Called whenever a key is released, used to exit the game at any moment
	 * with escape
	 * 
	 * @param key
	 *            Pressed key
	 * @param c
	 *            The character of pressed key
	 */
	@Override
	public void keyReleased(int key, char c) {
		if (Input.KEY_ESCAPE == key) {
			this.container.exit();
		}
	}

	/**
	 * Called whenever a key is pressed, used to control players and skip to
	 * next game phase
	 * 
	 * @param key
	 *            Pressed key
	 * @param c
	 *            The character of pressed key
	 */
	@Override
	public void keyPressed(int key, char c) {
		System.out.println("Phase de jeu : " + engine.getPlayPhase().toString());

		if (engine.getPlayPhase().equals(PlayPhase.playerMovement)) {
			switch (key) {

			case Input.KEY_Z:
				if (!engine.getPlayer(Team.ROUGE).isDie()) {
					engine.goTo(engine.getPlayer(Team.ROUGE), Direction.NORTH);
				}
				break;
			case Input.KEY_Q:
				if (!engine.getPlayer(Team.ROUGE).isDie()) {
					engine.goTo(engine.getPlayer(Team.ROUGE), Direction.WEST);
				}
				break;
			case Input.KEY_S:
				if (!engine.getPlayer(Team.ROUGE).isDie()) {
					engine.goTo(engine.getPlayer(Team.ROUGE), Direction.SOUTH);
				}
				break;
			case Input.KEY_D:
				if (!engine.getPlayer(Team.ROUGE).isDie()) {
					engine.goTo(engine.getPlayer(Team.ROUGE), Direction.EAST);
				}
				break;
			case Input.KEY_UP:
				if (!engine.getPlayer(Team.BLEU).isDie()) {
					engine.goTo(engine.getPlayer(Team.BLEU), Direction.NORTH);
				}
				break;
			case Input.KEY_LEFT:
				if (!engine.getPlayer(Team.BLEU).isDie()) {
					engine.goTo(engine.getPlayer(Team.BLEU), Direction.WEST);
				}
				break;
			case Input.KEY_DOWN:
				if (!engine.getPlayer(Team.BLEU).isDie()) {
					engine.goTo(engine.getPlayer(Team.BLEU), Direction.SOUTH);
				}
				break;
			case Input.KEY_RIGHT:
				if (!engine.getPlayer(Team.BLEU).isDie()) {
					engine.goTo(engine.getPlayer(Team.BLEU), Direction.EAST);
				}
				break;
			case Input.KEY_O:
				if (!engine.getPlayer(Team.BLEU).isDie()) {
					engine.classicAtk(engine.getPlayer(Team.BLEU), Direction.NORTH);
				}
				break;
			case Input.KEY_K:
				if (!engine.getPlayer(Team.BLEU).isDie()) {
					engine.classicAtk(engine.getPlayer(Team.BLEU), Direction.WEST);
				}
				break;
			case Input.KEY_L:
				if (!engine.getPlayer(Team.BLEU).isDie()) {
					engine.classicAtk(engine.getPlayer(Team.BLEU), Direction.SOUTH);
				}
				break;
			case Input.KEY_M:
				if (!engine.getPlayer(Team.BLEU).isDie()) {
					engine.classicAtk(engine.getPlayer(Team.BLEU), Direction.EAST);
				}
				break;
			case Input.KEY_F:
				if (!engine.getPlayer(Team.ROUGE).isDie()) {
					engine.classicAtk(engine.getPlayer(Team.ROUGE), Direction.NORTH);
				}
				break;
			case Input.KEY_C:
				if (!engine.getPlayer(Team.ROUGE).isDie()) {
					engine.classicAtk(engine.getPlayer(Team.ROUGE), Direction.WEST);
				}
				break;
			case Input.KEY_V:
				if (!engine.getPlayer(Team.ROUGE).isDie()) {
					engine.classicAtk(engine.getPlayer(Team.ROUGE), Direction.SOUTH);
				}
				break;
			case Input.KEY_B:
				if (!engine.getPlayer(Team.ROUGE).isDie()) {
					engine.classicAtk(engine.getPlayer(Team.ROUGE), Direction.EAST);
				}
				break;
			case Input.KEY_SPACE:
				engine.setPlayPhase(PlayPhase.behaviorModification);
				break;
			}
		} else if (engine.getPlayPhase().equals(PlayPhase.behaviorModification)) {
			switch (key) {
			case Input.KEY_SPACE:
				engine.setPlayPhase(PlayPhase.automatonExecution);
				break;
			}
		}
	}

	/**
	 * Is called whenever the game window is closed, is used to quit the game
	 * 
	 * @return false
	 */
	@Override
	public boolean closeRequested() {
		this.container.exit();
		return false;
	}

	// End(Miscellaneous methods)

	// ↓ Getters and setters ↓

	/**
	 * Returns the game engine
	 * 
	 * @return The game engine
	 */
	public Engine getEngine() {
		return this.engine;
	}

	/**
	 * Returns a cell x coordinate given a pixel x coordinate
	 * 
	 * @param x
	 *            The x coordinate to translate (in pixels)
	 * @return The x coordinate translated in cell coordinate
	 */
	public int pixelToCellX(float x) {
		return (int) (x - (x % cellWidth)) / cellWidth;
	}

	/**
	 * Returns a cell y coordinate given a pixel y coordinate
	 * 
	 * @param y
	 *            The y coordinate to translate (in pixels)
	 * @return The y coordinate translated in cell coordinate
	 */
	public int pixelToCellY(float y) {
		return (int) (y - (y % cellHeight)) / cellHeight;
	}

	/**
	 * Returns a pixel x coordinate corresponding to a given cell x coordinate's
	 * center
	 * 
	 * @param x
	 *            The x coordinate to translate (in cell)
	 * @return The x cell coordinate translated into pixels
	 */
	public float cellToPixelX(int x) {
		return (x + 0.5f) * cellWidth;
	}

	/**
	 * Returns a pixel y coordinate corresponding to a given cell y coordinate's
	 * center
	 * 
	 * @param y
	 *            The y coordinate to translate (in cell)
	 * @return The y coordinate translated into pixels
	 */
	public float cellToPixelY(int y) {
		return (y + 0.5f) * cellHeight;
	}

	/**
	 * Returns the game window's height
	 * 
	 * @return The game window's height
	 */
	public int getWindowHeight() {
		return WindowHeight;
	}

	/**
	 * Returns the game window's width
	 * 
	 * @return The game window's width
	 */
	public int getWindowWidth() {
		return WindowWidth;
	}

	/**
	 * Asks the gui to launch its input interface
	 */
	public void inputRequest() {
		setBehaviorInputNeeded(true);
	}

	/**
	 * Sets the behaviorInputNeeded boolean
	 * 
	 * @param behaviorInputNeeded
	 *            The value at which the boolean should be set
	 */
	private void setBehaviorInputNeeded(boolean behaviorInputNeeded) {
		this.behaviorInputNeeded = behaviorInputNeeded;

	}

	// End(Getters and setters)

}