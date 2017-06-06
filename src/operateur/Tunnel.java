package operateur;

import carte.*;
import entite.Entity;
import entite.GameException;
import personnages.Character;

public class Tunnel extends Movement {

	protected int x;
	protected int y;

	public Tunnel(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Tunnel() {
		super();
	}

	@Override
	protected boolean isDoable(Entity e) {
		Cell test = new Cell(x, y);
		return test.isEmpty();
	}

	@Override
	protected void execute(Entity e) throws GameException {
		if (!isDoable(e)) {
			throw new GameException("La case d'arrivée est occupée");
		}
		((Character) e).teleport((Character) e, x, y);
	}

}
