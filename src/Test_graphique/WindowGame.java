package Test_graphique;

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

//Fichier de test graphique

public class WindowGame extends BasicGame {

	private GameContainer container;
	private TiledMap map;

	private float x = 300, y = 300;
	private int direction = 2;
	private boolean moving = false;
	private final Animation[] animations = new Animation[8];
	private TextField nameInput;
	protected String message;

 
	
	
	
	
	public static void main(String[] args) throws SlickException {
		new AppGameContainer(new WindowGame(), 1024+64, 512+64, false).start();
	}

	public WindowGame() {
		super("STAR Wars");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		this.container = container;
		this.map = new TiledMap("/home/enzo/newmap.tmx");

		SpriteSheet spriteSheet = new SpriteSheet("/home/enzo/SpriteSheetAnim.png", 64, 64);
		this.animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
		this.animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
		this.animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
		this.animations[3] = loadAnimation(spriteSheet, 0, 1, 3);
		this.animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
		this.animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
		this.animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
		this.animations[7] = loadAnimation(spriteSheet, 1, 9, 3);
	
		Font font = new Font("Verdana", Font.BOLD, 20);
		UnicodeFont uFont = new UnicodeFont(font, font.getSize(), font.isBold(), font.isItalic());
		nameInput = new TextField(container, uFont, 150,20,500,35, new ComponentListener()
	        {
	         public void componentActivated(AbstractComponent source) {
	            message = "Entered1: "+nameInput.getText();
	            nameInput.setFocus(true);
	         }

	    });
		
//		ComponentListener listener = new ComponentListener();
//		TextField nameInput = new TextField(arg0, truetypefont, 150,20,500,35, listener);
//		
//		{
//	        public void componentActivated(AbstractComponent source) {
//	           System.out.println("Entered1: "+nameInput.getText());
//	        }
//	   });
	
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
		this.map.render(0, 0, 3);
		this.map.render(0, 0, 0);
		g.setColor(new Color(0, 0, 0, .5f));
		g.fillOval((int) x - 16, (int) y - 8, 32, 16);
		g.drawAnimation(animations[direction + (moving ? 4 : 0)], (int) x - 32, (int) y - 60);
		this.map.render(0, 0, 1);
		this.map.render(0, 0, 2);
		nameInput.render(container, g);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		updateCharacter(delta);
	}

	private void updateCharacter(int delta) {
		if (this.moving) {
			float futurX = getFuturX(delta);
			float futurY = getFuturY(delta);
			boolean collision = isCollision(futurX, futurY);
			if (collision) {
				this.moving = false;
			} else {
				this.x = futurX;
				this.y = futurY;
			}
		}
	}

	private boolean isCollision(float x, float y) {
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

	private float getFuturX(int delta) {
		float futurX = this.x;
		switch (this.direction) {
		case 1:
			futurX = this.x - .1f * delta;
			break;
		case 3:
			futurX = this.x + .1f * delta;
			break;
		}
		return futurX;
	}

	private float getFuturY(int delta) {
		float futurY = this.y;
		switch (this.direction) {
		case 0:
			futurY = this.y - .1f * delta;
			break;
		case 2:
			futurY = this.y + .1f * delta;
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
		}
	}
}