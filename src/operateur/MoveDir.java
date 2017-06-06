package operateur;

import entite.*;
import personnages.Character;

public class MoveDir extends Movement {

	public MoveDir() {
		super();
	}

	protected Direction dir;
	protected int lg;

	@Override
	protected boolean isDoable() {
		// TODO
		return true;
	}

	protected void execute(Entity e) throws GameException {

		if (!isDoable()) {
			throw new GameException("Cette action n'est pas réalisable");
		}

		((Character) e).goTo(dir, lg);

	}

}
