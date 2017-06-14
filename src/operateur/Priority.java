package operateur;

import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import pickable.PickPriority;
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
			if (((Action) left).isDoable(r)) {
				r.addActionToActionList((Action) left);
			} else if (((Action) right).isDoable(r)) {
				r.addActionToActionList((Action) right);
			}
		} else {
			throw new NotDoableException("Priority only takes 2 Action and not a Sequence");
		}
	}

	@Override
	public String toString() {
		return ">   ";
	}

	@Override
	public Class<? extends PickAble> getPickable() {
		return PickPriority.class;
	}

	// @Override
	// protected void execute(Entity e) throws GameException {
	// if (isDoable(e)) {
	// throw new GameException("Aucune des deux actions n'est possible");
	// }
	// if (A.isDoable(e)) {
	// A.execute(e);
	// } else {
	// B.execute(e);
	// }
	// }
}
