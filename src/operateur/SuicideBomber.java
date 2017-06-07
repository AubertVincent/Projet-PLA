package operateur;

import entite.*;
import personnages.Character;

public class SuicideBomber extends Attack {

	@Override
	public boolean isDoable(Entity e) {
		if (((Character) e).isRobot()) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void execute(Entity e) throws GameException {
		if (!isDoable()) {
			throw new GameException("Cette action n'est pas réalisable");
		}
		if (!e.isCharacter()) {
			throw new GameException("Cette entité n'est pas un personnage");
		} else {
			int x = e.getX();
			int y = e.getY();
			Direction d;

			// TODO
		}

	}

}
