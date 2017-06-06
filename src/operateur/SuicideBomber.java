package operateur;

import entite.*;
import personnages.Character;

public class SuicideBomber extends Attack {

	public SuicideBomber(Character opponent, Character attacker) {
		super(opponent, attacker);
	}

	public SuicideBomber() {
		super();
	}

	@Override
	protected boolean isDoable(Entity e) {
		// TODO Verify the ennemy presence
		return false;
	}

	@Override
	protected void execute(Entity e) throws GameException {
		if (!isDoable(e)) {
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
