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
import exceptions.NotDoableException;
import moteurDuJeu.Engine;
import moteurDuJeu.PlayPhase;
import personnages.Besace;
import personnages.Player;
import personnages.Robot;

public class GUI extends BasicGame {

	private GameContainer container;
	private TiledMap map;

	private final int TextFieldHeight = 50;

	private final int WindowHeight;
	private final int WindowWidth;

	private final int cellHeight;
	private final int cellWidth;

	private GUIBehaviorInput inputTextField;

	protected boolean behaviorInputNeeded = false;

	// TODO Bound to be some kind of list ?
	// private GUIPlayer perso1;
	// private GUIPlayer perso2;

	private Engine engine;

	Character myself;

	public static void main(String[] args) throws SlickException {
		GUI mainUI = new GUI();
		new AppGameContainer(new GUI(), mainUI.WindowWidth, mainUI.WindowHeight, false).start();
	}

	public GUI() throws SlickException {
		super("STAR Wars");
		WindowHeight = 576;
		WindowWidth = 1088;
		cellHeight = 32;
		cellWidth = 32;
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		this.container = container;
		map = new TiledMap("res/map.tmx");
		this.inputTextField = new GUIBehaviorInput(container, this, WindowWidth, WindowHeight, TextFieldHeight,
				"(MC2E | (AC;(MC3W>MT8.3)))");
		engine = new Engine(this);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		map.render(0, 0, 3);
		map.render(0, 0, 0);
		map.render(0, 0, 1);
		map.render(0, 0, 2);

		for (Iterator<Player> itrPlayer = engine.getPlayerList().iterator(); itrPlayer.hasNext();) {
			Player currentPlayer = itrPlayer.next();
			GUIPlayer guiPlayer = currentPlayer.getMyselfGUI();
			guiPlayer.render(g);
			for (Iterator<Robot> itrRobot = currentPlayer.getRobotList().iterator(); itrRobot.hasNext();) {
				Robot currentRobot = itrRobot.next();
				GUIRobot guiRobot = currentRobot.getMyselfGUI();
				guiRobot.render(g);
			}

		}

		for (Iterator<GUIPickAble> itr = engine.getMap().getPickAbleList().iterator(); itr.hasNext();) {
			GUIPickAble currentPickabke = itr.next();
			currentPickabke.render();
		}

		map.render(0, 0, 4);
		map.render(0, 0, 5);

		if (behaviorInputNeeded) {
			Besace besace;
			besace = engine.getPlayer(Team.ROUGE).getBesace();
			this.rectBesace.render(container, g, besace);
			this.inputTextField.render(container, g);
		}
	}

	/**
	 * Equivalent to isObstacle if the obstacle is another Character
	 * 
	 * @param x
	 * @param y
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

	/**
	 * Returns true if there is an obstacle on the given cell of the GUI
	 * 
	 * @param x
	 *            x coordinate of the cell
	 * @param y
	 *            y coordinate of the cell
	 * @return
	 */
	public boolean isObstacle(int x, int y) {
		int logicLayer = map.getLayerIndex("obstacles");
		Image tile = map.getTileImage(x, y, logicLayer);
		boolean collision = tile != null;
		return collision;
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		for (GUIPlayer p : this.guiPlayerList) {
			p.update(this, delta);
			for (GUIRobot r : p.getGuiRobotList()) {
				r.update(this, delta);
			}
		}
		this.inputTextField.update(container);
	}

	private GUICharacter getGUICharactereFromMouse(int x, int y) throws NotDoableException {
		for (GUIPlayer p : guiPlayerList) {
			if (p.getCurrentX() == x && p.getCurrentY() == y) {
				return p;
			}
			for (GUIRobot r : p.getGuiRobotList()) {
				if (r.getCurrentX() == x && r.getCurrentY() == y) {
					return r;
				}
			}
		}
		throw new NotDoableException("Pas de personnage sur cette case ou mauvaise phase de jeu");
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		int mouseXCell = pixelToCellX(x);
		int mouseYCell = pixelToCellY(y);
		GUICharacter guiPerso;
		System.out.println("LeftClick on (" + mouseXCell + ", " + mouseYCell + ")");

		try {
			guiPerso = getGUICharactereFromMouse(mouseXCell, mouseYCell);
			if (guiPerso instanceof GUIPlayer) {
				engine.createRobot(guiPerso.getPlayer(), this);
			} else {
				try {
					engine.behaviorModif(guiPerso.getRobot(), this);
				} catch (Exception e) {
					e.getMessage();
				}
			}
		} catch (NotDoableException e) {
			e.getMessage();
		}

	}

	public Engine getEngine() {
		return this.engine;
	}

	@Override
	public void keyReleased(int key, char c) {
		if (Input.KEY_ESCAPE == key) {
			this.container.exit();
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		try {
			System.out.println("Phase de jeu : " + engine.getPlayPhase().toString());

			if (engine.getPlayPhase().equals(PlayPhase.playerMovement)) {
				switch (key) {

				case Input.KEY_UP:
					engine.goTo(engine.getPlayer(Team.ROUGE), Direction.NORTH);
					break;
				case Input.KEY_LEFT:
					engine.goTo(engine.getPlayer(Team.ROUGE), Direction.WEST);
					break;
				case Input.KEY_DOWN:
					engine.goTo(engine.getPlayer(Team.ROUGE), Direction.SOUTH);
					break;
				case Input.KEY_RIGHT:
					engine.goTo(engine.getPlayer(Team.ROUGE), Direction.EAST);
					break;
				case Input.KEY_Z:
					engine.goTo(engine.getPlayer(Team.BLEU), Direction.NORTH);
					break;
				case Input.KEY_Q:
					engine.goTo(engine.getPlayer(Team.BLEU), Direction.WEST);
					break;
				case Input.KEY_S:
					engine.goTo(engine.getPlayer(Team.BLEU), Direction.SOUTH);
					break;
				case Input.KEY_D:
					engine.goTo(engine.getPlayer(Team.BLEU), Direction.EAST);
					break;
				case Input.KEY_O:
					engine.classicAtk(engine.getPlayer(Team.BLEU), Direction.NORTH);
					break;
				case Input.KEY_K:
					engine.classicAtk(engine.getPlayer(Team.BLEU), Direction.WEST);
					break;
				case Input.KEY_L:
					engine.classicAtk(engine.getPlayer(Team.BLEU), Direction.SOUTH);
					break;
				case Input.KEY_M:
					engine.classicAtk(engine.getPlayer(Team.BLEU), Direction.EAST);
					break;
				case Input.KEY_F:
					engine.classicAtk(engine.getPlayer(Team.ROUGE), Direction.NORTH);
					break;
				case Input.KEY_C:
					engine.classicAtk(engine.getPlayer(Team.ROUGE), Direction.WEST);
					break;
				case Input.KEY_V:
					engine.classicAtk(engine.getPlayer(Team.ROUGE), Direction.SOUTH);
					break;
				case Input.KEY_B:
					engine.classicAtk(engine.getPlayer(Team.ROUGE), Direction.EAST);
					break;
				}
			}

			if (engine.getPlayPhase().equals(PlayPhase.behaviorModification)) {
				if (key == Input.KEY_SPACE) {
					engine.setPlayPhase(Input.KEY_SPACE);
					engine.executeAutomaton(this);
				}
			}

		} catch (NotDoableException e) {

		}
	}

	@Override
	public boolean closeRequested() {
		System.exit(0);
		return false;
	}

	// ↓ Getters and setters ↓

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

	public void setBehaviorInputNeeded(boolean behaviorInputNeeded) {
		this.behaviorInputNeeded = behaviorInputNeeded;
	}

}