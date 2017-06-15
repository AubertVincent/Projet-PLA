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

	// ↓ Constructor and render ↓

	/**
	 * 
	 * @param container
	 *            The context in which GUI components are created and rendered
	 * @param WindowHeight
	 *            Height of the game window
	 * @param WidthRect
	 *            Width of the game window
	 * @param HeightRect
	 *            Height of the displayed rectangle
	 * @param cellWidth
	 *            Width of a map cell
	 */
	protected GUIBesace(GameContainer container, int WindowHeight, int WidthRect, int HeightRect, int cellWidth) {

		this.x = cellWidth * 7;
		this.y = (WindowHeight / 2) - HeightRect / 2;

		this.frame = new Rectangle(x, y, WidthRect, HeightRect);

	}

	/**
	 * 
	 * @param container
	 *            The context in which GUI components are rendered
	 * @param g
	 *            A graphics context used to render primitives
	 * @param besace
	 *            The Besace to be displayed
	 */
	protected void render(GameContainer container, Graphics g, Besace besace) {
		Color backgroundField = new Color(0f, 0f, 0f, 0.8f);
		g.setColor(backgroundField);
		g.fill(this.frame);

		displayBesace(g, besace);
	}

	// End(Constructor and render)

	// ↓ Miscellaneous methods ↓

	/**
	 * Displays the content of the given besace as strings of the format
	 * "'Pickable' : 'Quantity'"
	 * 
	 * @param g
	 *            A graphics context used to render primitives
	 * @param besace
	 *            The Besace to be displayed
	 */
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

	// End(Miscellaneous methods)
}