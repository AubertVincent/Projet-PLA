package Test_graphique;

import java.awt.MouseInfo;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.tiled.TiledMap;

public class WindowGame extends BasicGame {

	private GameContainer container;
	private TiledMap map;

	TextField textField;
	boolean mouseleftbutton;

	private float x = 240;
	private float y = 336;
	private float x1 = 300, y1 = 300;
	private int direction = 2;
	private int direction2 = 2;
	private boolean moving = false;
	private boolean moving2 = false;
	private final Animation[] animations = new Animation[8];

	public static void main(String[] args) throws SlickException {
		new AppGameContainer(new WindowGame(), 1024 + 64, 512 + 64, false).start();
	}

	public WindowGame() {
		super("STAR Wars");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		this.container = container;
		this.map = new TiledMap("res/newmap.tmx");

		SpriteSheet spriteSheet = new SpriteSheet("res/SpriteSheetAnim.png", 64, 64);
		this.animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
		this.animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
		this.animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
		this.animations[3] = loadAnimation(spriteSheet, 0, 1, 3);
		this.animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
		this.animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
		this.animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
		this.animations[7] = loadAnimation(spriteSheet, 1, 9, 3);

		Font defaultfont = container.getDefaultFont();
		textField = new TextField(container, defaultfont, 0, (512 + 64) - 50, 1024 + 64, 50);
		textField.setText("Exemple");
	}

	private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
		Animation animation = new Animation();
		for (int x = startX; x < endX; x++) {
			animation.addFrame(spriteSheet.getSprite(x, y), 100);
		}
		return animation;
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		this.map.render(0, 0, 0);
		this.map.render(0, 0, 1);
		this.map.render(0, 0, 2);
		g.setColor(new Color(0, 0, 0, .5f));
		g.fillOval((int) x - 16, (int) y - 8, 32, 16);
		g.drawAnimation(animations[direction + (moving ? 4 : 0)], (int) x - 32, (int) y - 60);
		g.drawAnimation(animations[direction2 + (moving2 ? 4 : 0)], (int) x1, (int) y1);

		this.map.render(0, 0, 4);
		this.map.render(0, 0, 5);

		g.setColor(Color.white);
		Color backgroundField = new Color(0f, 0f, 0f, 0.7f);
		textField.setBackgroundColor(backgroundField);
		textField.setBorderColor(backgroundField);
		textField.render(container, g);
	}

	private void updateBehaviour() {
		if (textField.hasFocus()) {
			if (container.getInput().isKeyDown(Input.KEY_ENTER)) {
				System.out.println("ENTER");
				System.out.println(textField.getText());
			}
		}
		
		if (container.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			mouseleftbutton = true;
			double mouseX = MouseInfo.getPointerInfo().getLocation().getX();
			double mouseY = MouseInfo.getPointerInfo().getLocation().getY();
			// System.out.println("X:" + mouseX);
			// System.out.println("Y:" + mouseY);
		} else {
			if (mouseleftbutton) {
				mouseleftbutton = false;
			}
		}
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		updateCharacter(delta);
		updateBehaviour();
	}

	private void updateCharacter(int delta) {
		if (this.moving) {
			float futurX = getFuturX(delta, this.x, this.y, this.direction);
			float futurY = getFuturY(delta, this.x, this.y, this.direction);
			boolean collision = isCollision(futurX, futurY);
			if (collision) {
				this.moving = false;
			} else {
				this.x = futurX;
				this.y = futurY;
			}
		}

		if (this.moving2) {
			float futurX = getFuturX(delta, this.x1, this.y1, this.direction2);
			float futurY = getFuturY(delta, this.x1, this.y1, this.direction2);
			boolean collision = isCollision(futurX, futurY);
			if (collision) {
				this.moving2 = false;
			} else {
				this.x1 = futurX;
				this.y1 = futurY;
			}
		}
	}

	private boolean isCollision(float x, float y) {
		int tileW = this.map.getTileWidth();
		int tileH = this.map.getTileHeight();
		int barrierLayer = this.map.getLayerIndex("obstacles");
		Image tile = this.map.getTileImage((int) x / tileW, (int) y / tileH, barrierLayer);
		boolean collision = tile != null;
		if (collision) {
			Color color = tile.getColor((int) x % tileW, (int) y % tileH);
			collision = color.getAlpha() > 0;
		}
		return collision;
	}

	private float getFuturX(int delta, float x, float y, int direction) {
		float futurX = x;
		switch (direction) {
		case 1:
			futurX = x - .1f * delta;
			break;
		case 3:
			futurX = x + .1f * delta;
			break;
		}
		return futurX;
	}

	private float getFuturY(int delta, float x, float y, int direction) {
		float futurY = y;
		switch (direction) {
		case 0:
			futurY = y - .1f * delta;
			break;
		case 2:
			futurY = y + .1f * delta;
			break;
		}
		return futurY;
	}

	@Override
	public void keyReleased(int key, char c) {
		this.moving = false;
		if (Input.KEY_ESCAPE == key) {
			this.container.exit();
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_UP:
			this.direction = 0;
			this.moving = true;
			break;
		case Input.KEY_LEFT:
			this.direction = 1;
			this.moving = true;
			break;
		case Input.KEY_DOWN:
			this.direction = 2;
			this.moving = true;
			break;
		case Input.KEY_RIGHT:
			this.direction = 3;
			this.moving = true;
			break;
		case Input.KEY_Z:
			this.direction2 = 0;
			this.moving2 = true;
			break;
		case Input.KEY_Q:
			this.direction2 = 1;
			this.moving2 = true;
			break;
		case Input.KEY_S:
			this.direction2 = 2;
			this.moving2 = true;
			break;
		case Input.KEY_D:
			this.direction2 = 3;
			this.moving2 = true;
			break;
		}
	}
}