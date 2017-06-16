package operateur;

import carte.Map;
import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import pickable.PickSuccession;
import sequence._Sequence;

public class Succession extends Behavior {

	// ↓ Constructor, update and render ↓

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

	// End(Constructor, update and render)

	// ↓ Miscellaneous methods ↓

	@Override
	public void execute(Robot r, _Sequence left, _Sequence right) throws NotDoableException {
		try {
			// test
			// System.out.println("Une succession !");
			// end test
			if (left.isAction() && (((Action) left).isDoable(r))) {
				r.addActionToActionList((Action) left);
			} else {
				left.addActionToActionList(r);
			}
			if (right.isAction() && (((Action) right).isDoable(r))) {
				r.addActionToActionList((Action) right);
			} else {
				right.addActionToActionList(r);
			}
		} catch (NotDoableException e) {
			throw new NotDoableException("Undoable sequences");
		}
	}

	@Override
	public Class<? extends PickAble> getPickable() {
		return PickSuccession.class;
	}

	public PickAble behaviorToPickAble(int x, int y, Map map) {
		return new PickSuccession(x, y, map);
	}

	@Override
	public String toString() {
		return ";   ";
	}

	// End(Miscellaneous methods)

}
