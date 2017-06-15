package operateur;

import java.util.Random;

import carte.Map;
import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import pickable.PickRandomBar;
import sequence._Sequence;

public class RandomBar extends Behavior {

	// ↓ Constructor, update and render ↓
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

	// End(Constructor, update and render)

	// ↓ Miscellaneous methods ↓

	@Override
	public void execute(Robot r, _Sequence left, _Sequence right) throws NotDoableException {
		try {
			// test
			// System.out.println(" Voici un random !");
			// end test
			Random random = new Random();
			int n = random.nextInt(2);
			if (n == 0) {
				if (left.isAction() && (((Action) left).isDoable(r))) {
					r.addActionToActionList((Action) left);
				} else {
					left.addActionToActionList(r);
				}
			} else {
				if (right.isAction() && (((Action) right).isDoable(r))) {
					r.addActionToActionList((Action) right);
				} else {
					right.addActionToActionList(r);
				}
			}
		} catch (NotDoableException e) {
			throw new NotDoableException("Undoable sequences");
		}
	}

	@Override
	public String toString() {
		return "/   ";
	}

	@Override
	public Class<? extends PickAble> getPickable() {
		return PickRandomBar.class;
	}

	@Override
	public PickAble behaviorToPickAble(int x, int y, Map map) {
		return new PickRandomBar(x, y, map);
	}

	// End(Miscellaneous methods)

}
