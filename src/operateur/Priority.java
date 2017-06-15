package operateur;

import carte.Map;
import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import pickable.PickPriority;
import sequence._Sequence;

public class Priority extends Behavior {

	// ↓ Constructor, update and render ↓

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

	// End(Constructor, update and render)

	// ↓ Miscellaneous methods ↓

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

	@Override
	public PickAble behaviorToPickAble(int x, int y, Map map) {
		return new PickPriority(x, y, map);
	}

	// End(Miscellaneous methods)

}
