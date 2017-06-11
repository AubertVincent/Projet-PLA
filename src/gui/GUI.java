package gui;

import java.util.ArrayList;
import java.util.List;

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
import personnages.Player;

public class GUI extends BasicGame {

	private GameContainer container;
	private TiledMap map;

	private final int TextFieldHeight = 50;

	private final int WindowHeight;
	private final int WindowWidth;

	private final int cellHeight;
	private final int cellWidth;

	private GUIBehaviorInput inputTextField;

	protected boolean behaviorInputNeeded = true;

	private List<GUIPlayer> guiPlayerList;
	private Engine engine;

	private GUIBesace rectBesace;
	private int WidthRect = 600;
	private int HeightRect = 300;

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
		guiPlayerList = new ArrayList<GUIPlayer>();
	}

	@Override
	public void init(GameContainer container) throws SlickException {

		this.container = container;
		map = new TiledMap("res/map.tmx");

		// try {
		// // perso1 = new GUIPlayer(this, 2, 4, entite.Direction.SOUTH, 100,
		// // Team.ROUGE);
		// // guiCharactersList.add(perso1);
		// } catch (Exception e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// try {
		// // perso2 = new GUIPlayer(this, 31, 15, entite.Direction.SOUTH, 100,
		// // Team.BLEU);
		// // guiCharactersList.add(perso2);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		this.rectBesace = new GUIBesace(container, WindowWidth, WindowHeight, WidthRect, HeightRect, cellWidth,
				TextFieldHeight);

		this.inputTextField = new GUIBehaviorInput(container, this, WindowWidth, WindowHeight, TextFieldHeight,
				"(MC2E | (AC;(MC3W>MT8.3)))");

		engine = new Engine(this);
	}

	public void addGUICharactere(GUIPlayer perso) {
		guiPlayerList.add(perso);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		map.render(0, 0, 3);
		map.render(0, 0, 0);
		map.render(0, 0, 1);
		map.render(0, 0, 2);
		for (GUIPlayer p : this.guiPlayerList) {
			p.render(g);

		}

		map.render(0, 0, 4);
		map.render(0, 0, 5);

		if (behaviorInputNeeded) {
			Player player;
			player = engine.getPlayer(Team.ROUGE);
			this.rectBesace.render(container, g, player.getBesace());
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
		// int tileW = map.getTileWidth();
		// int tileH = map.getTileHeight();
		int logicLayer = map.getLayerIndex("obstacles");
		Image tile = map.getTileImage(x, y, logicLayer);
		boolean collision = tile != null;
		return collision;
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		for (GUIPlayer p : this.guiPlayerList) {

			p.update(this, delta);
		}
		this.inputTextField.update(container);

	}

	private GUICharacter getGUICharactereFromMouse(int x, int y) throws NotDoableException {
		for (GUICharacter p : guiPlayerList) {
			if (p.getCurrentX() == x && p.getCurrentY() == y) {
				return p;
			}
		}
		throw new NotDoableException("Pas de personnage sur cette case ou mauvaise phase de jeu");
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		int mouseXCell = pixelToCellX(x);
		int mouseYCell = pixelToCellY(y);
		GUICharacter perso;
		try {
			perso = getGUICharactereFromMouse(mouseXCell, mouseYCell);
			System.out.println("LeftClick on (" + mouseXCell + ", " + mouseYCell + ")");
			if (engine.getPlayPhase().equals(PlayPhase.behaviorModification)) {
				behaviorInputNeeded = true;
				if (perso.getClass().equals(GUIPlayer.class)) {
					engine.createRobot((GUIPlayer) perso, this);
				} else {
					engine.behaviorModif((GUIRobot) perso);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}

		// engine.mousePressed(button, mouseXCell, mouseYCell);

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
			// if (engine.getPlayPhase() == PlayPhase.behaviorModification) {
			// engine.createRobot();
			// }
			if (engine.getPlayPhase().equals(PlayPhase.playerMovement)) {
				switch (key) {

				case Input.KEY_UP:
					guiPlayerList.get(0).movePlayer(engine, Direction.NORTH);
					break;
				case Input.KEY_LEFT:
					guiPlayerList.get(0).movePlayer(engine, Direction.WEST);
					break;
				case Input.KEY_DOWN:
					guiPlayerList.get(0).movePlayer(engine, Direction.SOUTH);
					break;
				case Input.KEY_RIGHT:
					guiPlayerList.get(0).movePlayer(engine, Direction.EAST);
					break;
				case Input.KEY_Z:
					guiPlayerList.get(1).movePlayer(engine, Direction.NORTH);
					break;
				case Input.KEY_Q:
					guiPlayerList.get(1).movePlayer(engine, Direction.WEST);
					break;
				case Input.KEY_S:
					guiPlayerList.get(1).movePlayer(engine, Direction.SOUTH);
					break;
				case Input.KEY_D:
					guiPlayerList.get(1).movePlayer(engine, Direction.EAST);
					break;
				case Input.KEY_O:
					guiPlayerList.get(0).Attack(engine, Direction.NORTH);
					break;
				case Input.KEY_K:
					guiPlayerList.get(0).Attack(engine, Direction.WEST);
					break;
				case Input.KEY_L:
					guiPlayerList.get(0).Attack(engine, Direction.SOUTH);
					break;
				case Input.KEY_M:
					guiPlayerList.get(0).Attack(engine, Direction.EAST);
					break;
				case Input.KEY_F:
					guiPlayerList.get(1).Attack(engine, Direction.NORTH);
					break;
				case Input.KEY_C:
					guiPlayerList.get(1).Attack(engine, Direction.WEST);
					break;
				case Input.KEY_V:
					guiPlayerList.get(1).Attack(engine, Direction.SOUTH);
					break;
				case Input.KEY_B:
					guiPlayerList.get(1).Attack(engine, Direction.EAST);
					break;
				}
			}
			// } else if
			// (engine.getPlayPhase().equals(PlayPhase.behaviorModification)) {
			// behaviorInputNeeded = true;
			// engine.createRobot();
			// } else {
			// // engine.executeAutomaton();
			// }
			//
			// case Input.KEY_ADD:
			// try {
			// perso1.createRobot(3, 4);
			// } catch (SlickException e) {
			// e.printStackTrace();
			// }
			// break;
			// case Input.KEY_SPACE:
			// behaviorInputNeeded = true;
			// break;
			// }
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

	public GUICharacter getGUICharacterFromTeam(Team team) {
		if (team.equals(Team.ROUGE)) {
			return guiPlayerList.get(0);
		} else {
			return guiPlayerList.get(1);
		}
	}

}