package operateur;

import carte.Base;
import carte.Cell;
import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import pickable.PickRecall;

public class Recall extends Movement {

	protected Integer time;
	private int lastX;
	private int lastY;

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
		Base b = r.getBase();
		int x = b.getX();
		int y = b.getY();
		if (r.getEntityMap().getCell(x, y).isReachable()) {
			return true;
		}
		return false;
	}

	@Override
	public void execute(Robot r) throws NotDoableException {
		Base base = r.getBase();
		int x = base.getX();
		int y = base.getY();
		if (!isDoable(r)) {
			throw new NotDoableException("Impossible to execute this recall");
		}
		this.lastX = r.getX();
		this.lastY = r.getY();
		r.getEntityMap().getCell(r.getX(), r.getY()).setExplored(true);
		Cell baseNearestCell = r.getEntityMap().nearestFreeCell(x, y);
		r.getExplorationMap().getCell(baseNearestCell.getX(), baseNearestCell.getY()).setExplored(true);
		r.teleport(baseNearestCell.getX(), baseNearestCell.getY());
	}

	@Override
	public void cancel(Robot r) throws NotDoableException {
		// Call back this robot at his previous position
		r.getEntityMap().getCell(r.getX(), r.getY()).setExplored(false);
		r.teleport(this.lastX, this.lastY);
	}

	@Override
	public String toString() {
		return "MR" + time.toString();
	}

	@Override
	public Class<? extends PickAble> getPickable() {
		return PickRecall.class;
	}

}
