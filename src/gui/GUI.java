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

	private GUIPersonnage perso1;
	// private float x = 300, y = 300;
	// private int direction = 2;
	// private boolean moving = false;
	// private final Animation[] animations = new Animation[8];

	public static void main(String[] args) throws SlickException {
		new AppGameContainer(new GUI(), 1024 + 64, 512 + 64, false).start();
	}

	public GUI() {
		super("STAR Wars");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		this.container = container;
		this.map = new TiledMap("res/map.tmx");
		this.perso1 = new GUIPersonnage(300, 300, entite.Direction.SUD, "res/SpriteSheetAnim.png");
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		this.map.render(0, 0, 3);
		this.map.render(0, 0, 0);
		this.map.render(0, 0, 1);
		this.map.render(0, 0, 2);
		this.perso1.renderGUIPersonnage(g);
		this.map.render(0, 0, 4);
		this.map.render(0, 0, 5);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		perso1.updateGUIPersonnage(this, delta);
	}

	protected boolean isCollision(float x, float y) {
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
			perso1.setDirection(Direction.NORD);
			perso1.setMoving(true);
			break;
		case Input.KEY_LEFT:
			perso1.setDirection(Direction.OUEST);
			perso1.setMoving(true);
			break;
		case Input.KEY_DOWN:
			perso1.setDirection(Direction.SUD);
			perso1.setMoving(true);
			break;
		case Input.KEY_RIGHT:
			perso1.setDirection(Direction.EST);
			perso1.setMoving(true);
			break;
		}
	}
}