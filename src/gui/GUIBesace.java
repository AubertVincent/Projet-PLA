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

	// private Besace besace ;

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

		displayBesace(besace);

		// for (Iterator<Class<? extends PickAble>> mapIter =
		// besace.getIterator(); mapIter.hasNext();) {
		// String pick;
		// Class<? extends PickAble> currentClass;
		// // currentClass = mapIter.next();
		// pick = mapIter.toString();
		// g.drawString(pick, this.x, this.y);
		// }
	}

	private void displayBesace(Besace besace) {
		for (Iterator<Entry<Class<? extends PickAble>, Integer>> iterator = besace.get().entrySet().iterator(); iterator
				.hasNext();) {
			Map.Entry<Class<? extends PickAble>, Integer> mapentry = (Map.Entry<Class<? extends PickAble>, Integer>) iterator
					.next();
			System.out.println("clé: " + mapentry.getKey() + " | valeur: " + mapentry.getValue());
		}

		// for (Iterator<Entry<Class<? extends PickAble>, Integer>> iterator =
		// besace.get().entrySet().iterator(); iterator
		// .hasNext();) {
		// Map.Entry<Class<? extends PickAble>, Integer> mapentry =
		// (Map.Entry<Class<? extends PickAble>, Integer>) iterator
		// .next();
		//
		// System.out.println("clé: " + mapentry.getKey() + " | valeur: " +
		// mapentry.getValue());
		// // String pick = mapentry.toString();
		// // g.drawString(pick, this.x, this.y);
		// }
	}
}
