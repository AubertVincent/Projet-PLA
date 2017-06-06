package operateur;

import entite.Entity;
import entite.GameException;
import personnages.Character;

public class Tunnel extends Movement {

	protected int x;
	protected int y;

	@Override
	protected boolean isDoable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void execute(Entity e) throws GameException {
		((Character) e).teleport((Character) e, x, y);
	}

}
