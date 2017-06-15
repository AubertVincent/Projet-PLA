package gui;

import java.util.ArrayList;
import java.util.Iterator;
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
import moteurDuJeu.Engine;
import moteurDuJeu.PlayPhase;
import personnages.Player;
import personnages.Robot;
import personnages.State;
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

	protected boolean behaviorInputNeeded;

	private Engine engine;

	private GUIBesace rectBesace;
	private int WidthRect = 32 * 20;
	private int HeightRect = 300;
	private PlayPhase phase;
	private String guiphase;

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
		map = new TiledMap("res/map_final.tmx");
		inputTextField = new GUIBehaviorInput(container, this, WindowWidth, WindowHeight);
		rectBesace = new GUIBesace(container, WindowHeight, WidthRect, HeightRect, cellWidth);

		engine = new Engine(this);

		setBehaviorInputNeeded(false);
		// TODO It's a backup, may be used in GUIBehviorInput.drawCorrectedList
		// font = new Font("Verdana", Font.BOLD, 20);
		// ttf = new TrueTypeFont(font, true);

	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		map.render(0, 0, 0);
		map.render(0, 0, 1);
		map.render(0, 0, 2);
		map.render(0, 0, 3);

		phase = engine.getPlayPhase();
		guiphase = PlayPhase.toString(phase);
		g.drawString(guiphase, WindowWidth / 3, 4);

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

		if (behaviorInputNeeded) {
			this.inputTextField.update(container, engine.getCurrentModifier());
		}

		List<Player> playerListCopy = new ArrayList<Player>(engine.getPlayerList());
		for (Iterator<Player> playerIterator = playerListCopy.listIterator(); playerIterator.hasNext();) {
			Player currentPlayer = playerIterator.next();
			GUIPlayer guiCurrentPlayer = currentPlayer.getMyselfGUI();
			guiCurrentPlayer.update(this, delta);

			for (Iterator<Robot> robotIterator = currentPlayer.getRobotList().listIterator(); robotIterator
					.hasNext();) {
				Robot currentRobot = robotIterator.next();
				GUIRobot guiCurrentRobot = currentRobot.getMyselfGUI();
				guiCurrentRobot.update(this, delta);
			}
		}
		if (inputTextField.getUpdateness()) {
			inputTextField.setUpdateness(false);
			setBehaviorInputNeeded(false);
			engine.setRobotBehavior(this, inputTextField.getReceivedSequence());
		}

		try {
			if (everyoneWaiting() && engine.getPlayPhase().equals(PlayPhase.automatonExecution)) {
				engine.step();
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

	private boolean everyoneWaiting() {
		boolean allWaiting = true;
		for (Player currentPlayer : engine.getPlayerList()) {
			for (Robot currentRobot : currentPlayer.getRobotList()) {
				if (!currentRobot.getState().equals(State.Wait)) {
					allWaiting = false;
				}
			}
		}
		return allWaiting;

	}

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
		System.out.println("Phase de jeu : " + engine.getPlayPhase().toString());

		if (engine.getPlayPhase().equals(PlayPhase.playerMovement)) {
			switch (key) {

			case Input.KEY_Z:
				engine.goTo(engine.getPlayer(Team.ROUGE), Direction.NORTH);
				break;
			case Input.KEY_Q:
				engine.goTo(engine.getPlayer(Team.ROUGE), Direction.WEST);
				break;
			case Input.KEY_S:
				engine.goTo(engine.getPlayer(Team.ROUGE), Direction.SOUTH);
				break;
			case Input.KEY_D:
				engine.goTo(engine.getPlayer(Team.ROUGE), Direction.EAST);
				break;
			case Input.KEY_UP:
				engine.goTo(engine.getPlayer(Team.BLEU), Direction.NORTH);
				break;
			case Input.KEY_LEFT:
				engine.goTo(engine.getPlayer(Team.BLEU), Direction.WEST);
				break;
			case Input.KEY_DOWN:
				engine.goTo(engine.getPlayer(Team.BLEU), Direction.SOUTH);
				break;
			case Input.KEY_RIGHT:
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

	public int getWindowHeight() {
		return WindowHeight;
	}

	public int getWindowWidth() {
		return WindowWidth;
	}

	public void inputRequest() {
		setBehaviorInputNeeded(true);
	}

	private void setBehaviorInputNeeded(boolean behaviorInputNeeded) {
		this.behaviorInputNeeded = behaviorInputNeeded;

	}

	public void setPlayPhase(PlayPhase endofgame) {
		engine.setPlayPhase(PlayPhase.endOfGame);

	}

}