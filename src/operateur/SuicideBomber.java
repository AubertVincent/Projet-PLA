package operateur;

import java.util.Iterator;
import java.util.List;

import carte.*;
import entite.*;
import personnages.*;
import personnages.Character;

public class SuicideBomber extends Attack {

	// public SuicideBomber(Character opponent, Character attacker) {
	// super(opponent, attacker);
	// }
	//
	// public SuicideBomber() {
	// super();
	// }

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
