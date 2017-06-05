package operateur;

import entite.Entity;
import entite.GameException;
import personnages.Character;

public class Tunnel extends Movement {

	public Tunnel(int x, int y) {
		super(x, y);
	}

	int x;
	int y;

	@Override
	public void execute(Entity e) throws GameException {
		((Character) e).teleport((Character) e,x, y);
	}

	@Override
	public boolean isDoable() {
		// TODO Auto-generated method stub
		return false;
	}

}
