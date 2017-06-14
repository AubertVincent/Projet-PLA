package operateur;

import carte.Base;
import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import pickable.PickRecall;

public class Recall extends Movement {

	private Integer time;
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

		// test
		// System.out.println("J'execute un Recall");
		// end test

		// TODO Intégrer la notion de temps, ce callback s'excute dès qu'on
		// l'appelle.
		// int time = r.getRecall();;
		// if (r.getRecall() == 0) {

		Base base = r.getBase();
		int xBase = base.getX();
		int yBase = base.getY();
		System.out.println("J'execute le recall !");
		if (this.isDoable(r)) {
			this.lastX = r.getX();
			this.lastY = r.getY();
			r.getEntityMap().getCell(r.getX(), r.getY()).setExplored(true);
			r.teleport(xBase, yBase);
		} else {
			throw new NotDoableException("Impossible to execute this recall");
		}

		// r.setRecall(time--);
	}

	@Override
	public void cancel(Robot r) throws NotDoableException {
		// Call back this robot at his previous position
		r.getEntityMap().getCell(r.getX(), r.getY()).setExplored(false);
		r.teleport(this.lastX, this.lastY);
		// r.cancelRecall();
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
