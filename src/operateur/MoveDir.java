package operateur;

import entite.*;
import personnages.Character;

public class MoveDir extends Movement {

	public MoveDir(int x, int y) {
		super(x, y);
	}

	Direction dir;
	int lg;

	@Override
	public boolean isDoable() {
		// TODO
		return true;
	}

	public void execute(Entity e) throws GameException {

		if (!isDoable()) {
			throw new GameException("Cette action n'est pas réalisable");
		}

		((Character) e).goTo(dir, lg);

	}

}
