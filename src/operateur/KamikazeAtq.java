package operateur;

import entite.Entite;
import entite.GameException;
import personnages.*;

public class KamikazeAtq extends Attaque {

	@Override
	public boolean isDoable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(Entite e) throws GameException {
		if (!isDoable()) {
			throw new GameException("Cette action n'est pas réalisable");
		}
		int x = e.getX();
		int y = e.getY();
		Direction d;
		if (!e.isPersonnage()) {
			throw new GameException("Cette entité n'est pas un personnage");
		} else {
			// TODO
		}

	}

}
