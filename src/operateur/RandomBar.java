package operateur;

import java.util.Random;

import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import pickable.PickRandomBar;
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

	@Override
	public void execute(Robot r, _Sequence left, _Sequence right) throws NotDoableException {
		// test
		// System.out.println(" Voici un random !");
		// end test
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
		return "/   ";
	}

	@Override
	public Class<? extends PickAble> getPickable() {
		return PickRandomBar.class;
	}

}
