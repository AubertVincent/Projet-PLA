package operateur;

import entite.*;

public class SuicideBomber extends Attack {

	public SuicideBomber(int x, int y) {
		super(x, y);
	}

	@Override
	public boolean isDoable() {
		// TODO Auto-generated method stub
		return false;
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
