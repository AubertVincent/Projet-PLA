package operateur;

import exceptions.NotDoableException;
import personnages.Robot;
import sequence._Sequence;

public class Priority extends Behavior {

	/**
	 * Set a new behavior by means of its 2 actions
	 * 
	 * @param A
	 *            First action
	 * @param B
	 *            Second action
	 */
	public Priority() {
		super();
	}

	// /**
	// * check if one of the two actions is doable
	// */
	// @Override
	// protected boolean isDoable(Entity e) {
	// return A.isDoable(e) || B.isDoable(e);
	// }

	@Override
	public void execute(Robot r, _Sequence left, _Sequence right) throws NotDoableException {
		// test
		// System.out.println("Voici une priorité !");
		// end test
		if (left instanceof Action && right instanceof Action) {
			try {
				left.execute(r);
			} catch (NotDoableException e) {
				try {
					right.execute(r);
				} catch (NotDoableException e2) {
					throw new NotDoableException("impossible Action");
				}
			}
		} else {
			throw new NotDoableException("Succession only take 2 Action and not a Sequence");
		}
	}

}
