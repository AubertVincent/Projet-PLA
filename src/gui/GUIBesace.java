package gui;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import personnages.Besace;
import pickable.PickAble;

public class GUIBesace {
	private Rectangle frame;
	private int x, y;

	protected GUIBesace(GameContainer container, int WindowWidth, int WindowHeight, int WidthRect, int HeightRect,
			int cellWidth, int TextFieldHeight) {

		this.x = cellWidth * 7;
		this.y = (WindowHeight - 200) - TextFieldHeight * 4;

		this.frame = new Rectangle(x, y, WidthRect, HeightRect);

	}

	protected void render(GameContainer container, Graphics g, Besace besace) {
		Color backgroundField = new Color(0f, 0f, 0f, 0.8f);
		g.setColor(backgroundField);
		g.fill(this.frame);

		displayBesace(g, besace);
	}

	private void displayBesace(Graphics g, Besace besace) {
		int interligne;
		interligne = this.y - 10;
		for (Iterator<Entry<Class<? extends PickAble>, Integer>> iterator = besace.get().entrySet().iterator(); iterator
				.hasNext();) {
			Map.Entry<Class<? extends PickAble>, Integer> mapentry = (Map.Entry<Class<? extends PickAble>, Integer>) iterator
					.next();

			String key = mapentry.getKey().getSimpleName();
			g.setColor(Color.white);
			g.drawString(PickAble.classToString(key) + " : " + mapentry.getValue(), this.x + 17, interligne += 27);
		}
	}
}