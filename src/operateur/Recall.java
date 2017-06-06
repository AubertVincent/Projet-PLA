package operateur;

import entite.*;
import personnages.Character;

public class Recall extends Movement {

	int time;

	public Recall() {
		super();
	}

	@Override
	protected boolean isDoable() {
		// TODO
		// Toujours vraie ?
		return true;
	}
	
	@Override
	protected void execute(Entity e) throws GameException {
		// TODO Intégrer la notion de temps, ce callback s'excute dès qu'on
		// l'appelle.
		((Character) e).teleport((Character) e, 0, 0); // TODO différencier pour
														// les deux joueurs
	}



}
