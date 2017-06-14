package operateur;

import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import pickable.PickRecall;

public class Recall extends Movement {

	protected Integer time;
	// Used in the case of the need of the cancel method
	// private int lastX;
	// private int lastY;

	/**
	 * Set a new recall by means of its time
	 * 
	 * @param time
	 */
	public Recall(int time) {
		super();
		this.time = time;
	}

	public Recall() {
		super();
	}

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
	public void cancel(Robot r) throws NotDoableException {
		// Call back this robot at his previous position
		// r.getEntityMap().getCell(r.getX(), r.getY()).setExplored(false);
		// r.teleport(this.lastX, this.lastY);
	}

	@Override
	public String toString() {
		return " MR" + time.toString();
	}

	@Override
	public Class<? extends PickAble> getPickable() {
		return PickRecall.class;
	}

}
