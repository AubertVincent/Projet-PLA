package operateur;

import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import pickable.PickRecall;

public class Recall extends Movement {

	protected Integer time;
	// ↓ Constructor, update and render ↓

	/**
	 * Set a new recall by means of its time
	 * 
	 * @param time
	 *            before the robot can go outside his base after a recall
	 */
	public Recall(int time) {
		super();
		this.time = time;
	}

	public Recall() {
		super();
	}
	// End(Constructor, update and render)

	// ↓ Miscellaneous methods ↓

	/**
	 * A recall is always doable
	 */
	@Override
	protected boolean isDoable(Robot r) {
		// Always doAble
		return true;
	}

	@Override
	public void execute(Robot r) throws NotDoableException {
		if (!isDoable(r)) {
			throw new NotDoableException("Impossible to execute this recall");
		}
		r.recall(time);

	}

	@Override
	public String toString() {
		return " MR" + time.toString();
	}

	@Override
	public Class<? extends PickAble> getPickable() {
		return PickRecall.class;
	}
	// End(Miscellaneous methods)

}
