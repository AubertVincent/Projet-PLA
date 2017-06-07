package operateur;

import entite.*;

public class Priority extends Behavior {

	/**
	 * Set a new behavior by means of its 2 actions
	 * 
	 * @param A
	 *            First action
	 * @param B
	 *            Second action
	 */
	public Priority(Action A, Action B) {
		super(A, B);
	}

	/**
	 * check if one of the two actions is doable
	 */
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
