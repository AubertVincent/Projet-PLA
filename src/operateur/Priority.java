package operateur;

import entite.*;

public class Priority extends Behavior {

	public Priority(Action A, Action B) {
		super(A, B);
	}

	@Override
	protected boolean isDoable(Entity e) {
		return A.isDoable(e) || B.isDoable(e);
	}

	@Override
	protected void execute(Entity e) throws GameException {
		if (isDoable(e)) {
			throw new GameException("Aucune des deux actions n'est possible");
		}
		if (A.isDoable(e)) {
			A.execute(e);
		} else {
			B.execute(e);
		}
	}
}
