package gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.TextField;

public class GUIBesace {
	private Rectangle frame;
	
	private Besace besace ;

	protected GUIBesace(GameContainer container, int WindowWidth, int WindowHeight, int WidthRect, int HeightRect,
			int cellWidth, int TextFieldHeight, String choices) {
		
		this.frame = new Rectangle(cellWidth * 7, (WindowHeight - 200) - TextFieldHeight * 4, WidthRect, HeightRect);
		
	}

	protected void render(GameContainer container, Graphics g) {
		Color backgroundField = new Color(0f, 0f, 0f, 0.8f);
		g.setColor(backgroundField);
		g.fill(this.frame);
	}
}
