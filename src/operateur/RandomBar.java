package operateur;

import java.util.Random;
import entite.*;

public class RandomBar extends Behavior {

	/**
	 * Set a new behavior by means of its 2 actions
	 * 
	 * @param A
	 *            First action
	 * @param B
	 *            Second action
	 */
	public RandomBar(Action A, Action B) {
		super(A, B);
	}

	/**
	 * check if one of the two actions is doable
	 */
	@Override
	protected boolean isDoable(Entity e) {
		return A.isDoable(e) || B.isDoable(e);
	}

	private Action randomAction(Action A, Action B) {
		Random r = new Random();
		int n = r.nextInt(2);
		if (n == 0) {
			return A;
		} else {
			return B;
		}
	}

	@Override
	protected void execute(Entity e) throws GameException {
		if (!(isDoable(e))) {
			throw new GameException("Aucune des deux actions n'est possible");
		} else if (!(A.isDoable(e))) {
			B.execute(e);
		} else if (!(B.isDoable(e))) {
			A.execute(e);
		} else {
			randomAction(A, B).execute(e);
		}
	}

}
