package gui;

import java.awt.Font;
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
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.tiled.TiledMap;

import entite.Direction;
import moteurDuJeu.Engine;
import operateur.Action;
import operateur.ClassicAck;
import operateur.MoveDir;
import operateur.Priority;
import operateur.RandomBar;
import operateur.Succession;
import personnages.Besace;
import sequence.EmptyRootTree;
import sequence.IncompleteTree;
import sequence.Tree;
import sequence._IncompleteSequence;
import sequence._Sequence;
import test.SequenceCorrector;
import util.Correct;
import util.Pair;

public class GUI extends BasicGame {

	// Test
	Font font;
	TrueTypeFont ttf;
	// End(Test)

	private GameContainer container;
	private TiledMap map;

	private final int TextFieldHeight = 50;

	private final int WindowHeight;
	private final int WindowWidth;

	private final int cellHeight;
	private final int cellWidth;

	private GUIBehaviorInput inputTextField;

	protected boolean behaviorInputNeeded = true;

	// TODO Bound to be some kind of list ?
	private GUIPlayer perso1;
	private GUIPlayer perso2;

	private Engine engine;

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
		// try {
		// perso1 = new GUIPlayer(this, 2, 4, entite.Direction.SOUTH, 100,
		// Team.BLEU);
		// } catch (Exception e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// try {
		// perso2 = new GUIPlayer(this, 31, 15, entite.Direction.SOUTH, 100,
		// Team.ROUGE);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// Test
		font = new Font("Verdana", Font.BOLD, 20);
		ttf = new TrueTypeFont(font, true);
		// End(Test)

		this.inputTextField = new GUIBehaviorInput(container, this, WindowWidth, WindowHeight, TextFieldHeight,
				"(MC5N;AC) | (MC7N;AC))");
		// engine = new Engine(this);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		map.render(0, 0, 3);
		map.render(0, 0, 0);
		map.render(0, 0, 1);
		map.render(0, 0, 2);
		// perso1.render(g);
		// perso2.render(g);

		// for (GUIRobot s : perso1.listRobot) {
		// s.render(g);
		// }

		map.render(0, 0, 4);
		map.render(0, 0, 5);

		if (behaviorInputNeeded) {
			this.inputTextField.render(container, g);
		}

		// Test

		Besace besace;

		_Sequence seq = new Tree(new RandomBar(),
				new Tree(new Priority(), new MoveDir(Direction.NORTH, 5), new ClassicAck()),
				new Tree(new Succession(), new MoveDir(Direction.NORTH, 7), new ClassicAck()));

		_IncompleteSequence incSeq = new IncompleteTree(new RandomBar(),
				new IncompleteTree(new Priority(), (_IncompleteSequence) new MoveDir(Direction.NORTH, 5),
						(_IncompleteSequence) new ClassicAck()),
				new EmptyRootTree(new MoveDir(Direction.NORTH, 7), new ClassicAck()));

		List<Pair<? extends _Sequence, Correct>> maListos = SequenceCorrector.correct(besace, seq);
		drawCorrectedList(g, 10, this.WindowHeight - 30, maListos);
		// End(Test)
	}

	private void drawCorrectedList(Graphics g, int x, int y, List<Pair<? extends _Sequence, Correct>> list) {
		for (Iterator<Pair<? extends _Sequence, Correct>> itr = list.iterator(); itr.hasNext();) {
			Pair<? extends _Sequence, Correct> currentPair = itr.next();
			Correct currentEltCorrectness = currentPair.getSecond();
			_Sequence currentSeq = currentPair.getFirst();
			switch (currentEltCorrectness) {
			case CORRECT:
				g.setColor(Color.green);
				break;
			case INCORRECT:
				g.setColor(Color.red);
				break;
			}
			if (currentSeq instanceof Action) {
				g.drawString(currentSeq.toString(), x, y);
				x += 40;
			} else if (currentSeq instanceof Tree) {
				g.drawString(((Tree) currentSeq).getOp().toString(), x, y);
				x += 10;
			}

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
		// perso1.update(this, delta);
		// perso2.update(this, delta);
		this.inputTextField.update(container);

	}

	@Override
	public void mousePressed(int button, int x, int y) {
		int mouseXCell = pixelToCellX(x);
		int mouseYCell = pixelToCellY(y);
		System.out.println("LeftClick on (" + mouseXCell + ", " + mouseYCell + ")");
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
		switch (key) {
		case Input.KEY_UP:
			perso1.movePlayer(engine, Direction.NORTH);
			break;
		case Input.KEY_LEFT:
			perso1.movePlayer(engine, Direction.WEST);
			break;
		case Input.KEY_DOWN:
			perso1.movePlayer(engine, Direction.SOUTH);
			break;
		case Input.KEY_RIGHT:
			perso1.movePlayer(engine, Direction.EAST);
			break;
		case Input.KEY_Z:
			perso2.movePlayer(engine, Direction.NORTH);
			break;
		case Input.KEY_Q:
			perso2.movePlayer(engine, Direction.WEST);
			break;
		case Input.KEY_S:
			perso2.movePlayer(engine, Direction.SOUTH);
			break;
		case Input.KEY_D:
			perso2.movePlayer(engine, Direction.EAST);
			break;
		case Input.KEY_O:
			perso1.Attack(Direction.NORTH);
			break;
		case Input.KEY_K:
			perso1.Attack(Direction.WEST);
			break;
		case Input.KEY_L:
			perso1.Attack(Direction.SOUTH);
			break;
		case Input.KEY_M:
			perso1.Attack(Direction.EAST);
			break;
		case Input.KEY_F:
			perso2.Attack(Direction.NORTH);
			break;
		case Input.KEY_C:
			perso2.Attack(Direction.WEST);
			break;
		case Input.KEY_V:
			perso2.Attack(Direction.SOUTH);
			break;
		case Input.KEY_B:
			perso2.Attack(Direction.EAST);
			break;
		case Input.KEY_ADD:
			try {
				perso1.createRobot(3, 4);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			break;
		case Input.KEY_SPACE:
			behaviorInputNeeded = true;
			break;
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

}