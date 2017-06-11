package operateur;

import java.util.Random;

import exceptions.NotDoableException;
import personnages.Robot;
import sequence._Sequence;

public class RandomBar extends Behavior {

	/**
	 * Set a new behavior by means of its 2 actions
	 * 
	 * @param A
	 *            First action
	 * @param B
	 *            Second action
	 */
	public RandomBar() {

	}

	// /**
	// * check if one of the two actions is doable
	// */
	// @Override
	// protected boolean isDoable(Entity e) {
	// return A.isDoable(e) || B.isDoable(e);
	// }

	// @Override
	// protected void execute(Entity e) throws GameException {
	// if (!(isDoable(e))) {
	// throw new GameException("Aucune des deux actions n'est possible");
	// } else if (!(A.isDoable(e))) {
	// B.execute(e);
	// } else if (!(B.isDoable(e))) {
	// A.execute(e);
	// } else {
	// randomAction(A, B).execute(e);
	// }
	// }

	@Override
	public void execute(Robot r, _Sequence left, _Sequence right) throws NotDoableException {
		Random random = new Random();
		int n = random.nextInt(2);
		try {
			if (n == 0) {
				right.execute(r);
			} else {
				left.execute(r);
			}
		} catch (NotDoableException e) {
			throw new NotDoableException("Action impossible");
		}
	}

	@Override
	public String toString() {
		return "/";
	}

}
