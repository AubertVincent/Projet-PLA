package gui;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.TextField;

public class GUIBesace {
	private Rectangle frame;
	private String contenant;
	private int x, y;
	
//	private Besace besace ;

	protected GUIBesace(GameContainer container, int WindowWidth, int WindowHeight, int WidthRect, int HeightRect,
			int cellWidth, int TextFieldHeight) {
		
		this.x = cellWidth * 7;
		this.y = (WindowHeight - 200) - TextFieldHeight * 4;

		this.frame = new Rectangle(x, y, WidthRect, HeightRect);
		
	}

	protected void render(GameContainer container, Graphics g, List<String> listContents) {
		Color backgroundField = new Color(0f, 0f, 0f, 0.8f);
		g.setColor(backgroundField);
		g.fill(this.frame);
		
		for(String s : listContents){
			g.drawString(this.contenant, this.x, this.y);
		}
	}
}
