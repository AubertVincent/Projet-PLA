package operateur;

import entite.Direction;
import entite.Entity;
import entite.GameException;
import personnages.Character;

public class Recall extends Movement {

	int time;

	public Recall(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(Entity e) throws GameException {
		// TODO Intégrer la notion de temps, ce callback s'excute dès qu'on
		// l'appelle.
		((Character) e).goTo(Direction.SOUTH, 0); // ??
	}

	@Override
	public boolean isDoable() {
		// TODO
		// Toujours vraie ?
		return true;
	}

}
