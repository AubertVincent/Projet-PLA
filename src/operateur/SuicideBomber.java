package operateur;

import entite.*;
import personnages.*;
import personnages.Character;

public class SuicideBomber extends Attack {

	@Override
	protected boolean isDoable(Entity e) {
		if (((Character) e).isRobot()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	protected void execute(Entity e) throws GameException {
		if (!isDoable(e)) {
			throw new GameException("Cette entité n'est pas un robot");

		} else {
			((Robot) e).suicideBomber(e);
		}
	}

}
