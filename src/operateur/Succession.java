package operateur;

import entite.*;

public class Succession extends Behavior {

	/**
	 * Set a new behavior by means of its 2 actions
	 * 
	 * @param A
	 *            First action
	 * @param B
	 *            Second action
	 */
	public Succession(Action A, Action B) {
		super(A, B);
	}

	/**
	 * check if the two actions is doable
	 */
	@Override
	protected boolean isDoable(Entity e) {
		return A.isDoable(e) && B.isDoable(e);
	}

	protected void execute(Entity e) throws GameException {
		if (!(isDoable(e))) {
			throw new GameException("Une des deux actions n'est pas réalisable");
		}
		A.execute(e);
		B.execute(e);
	}

}
