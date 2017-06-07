package operateur;

import entite.*;
import exceptions.GameException;
import personnages.Character;
import personnages.Robot;

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
		if (!isDoable(e)) {
			throw new GameException("Cette entité n'est pas un robot");
		} else {
			((Robot) e).suicideBomber(e);
		}

	}

}
