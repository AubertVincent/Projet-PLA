package operateur;

import carte.*;
import entite.Entity;
import entite.GameException;
import personnages.Character;

public class Tunnel extends Movement {

	protected int x;
	protected int y;

	/**
	 * set a new Tunnel by means of its arrival coordinates
	 * 
	 * @param x
	 *            x coordinate on the map
	 * @param y
	 *            y coordinate on the map
	 */
	public Tunnel(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Tunnel() {
		super();
	}

	/**
	 * Check if there is no obstacle on the arrival
	 */
	@Override
	protected boolean isDoable(Entity e) {
		Cell test = new Cell(x, y);
		return test.isFree();
	}

	@Override
	protected void execute(Entity e) throws GameException {
		if (!isDoable(e)) {
			throw new GameException("La case d'arrivée est occupée");
		}
		((Character) e).teleport(x, y);
	}

}
