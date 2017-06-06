package operateur;

import entite.Entity;
import entite.GameException;
import personnages.Character;

public class PickUp extends Action {

	@Override
	protected boolean isDoable(Entity e) {
		return true;
	}

	@Override
	protected void execute(Entity e) throws GameException {
		if (isDoable(e)) {
			throw new GameException("Imoossible de ramasser"); //Should never happen
		}
		else{
			((Character) e).pickUp(e);
		}
	}

}
