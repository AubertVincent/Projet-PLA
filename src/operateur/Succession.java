package operateur;

import exceptions.NotDoableException;
import personnages.Robot;
import sequence._Sequence;

public class Succession extends Behavior {

	@Override
	public String toString() {
		return ";";
	}

	/**
	 * Set a new behavior by means of its 2 actions
	 * 
	 * @param A
	 *            First action
	 * @param B
	 *            Second action
	 */
	public Succession() {
		super();
	}

	@Override
	public void execute(Robot r, _Sequence left, _Sequence right) throws NotDoableException {
		try {
			left.execute(r);
			right.execute(r);
		} catch (NotDoableException e) {
			throw new NotDoableException("Action impossible");
		}
	}

	/**
	 * check if the two actions is doable
	 */
	// @Override
	// protected boolean isDoable(Entity e) {
	// return A.isDoable(e) && B.isDoable(e);
	// }
	//
	// protected void execute(Entity e) throws GameException {
	// if (!(isDoable(e))) {
	// throw new GameException("Une des deux actions n'est pas réalisable");
	// }
	// A.execute(e);
	// B.execute(e);
	// }

}
