package operateur;

import exceptions.NotDoableException;
import personnages.Robot;

public class Recall extends Movement {

	protected int time;

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
		if (r.getPlayer() == 1 && r.getEntityMap().isReachable(2, 4)){
			return true;
		}
		if (r.getPlayer() == 2 && r.getEntityMap().isReachable(31, 15)){
			return true;
		}
		return false;
	}

	@Override
	public void execute(Robot r) throws NotDoableException {
		// TODO Intégrer la notion de temps, ce callback s'excute dès qu'on
		// l'appelle.
		// int time = r.getRecall();;
		// if (r.getRecall() == 0) {
		int xBase = r.getXBase();
		int yBase = r.getYBase();
		r.teleport(xBase, yBase);

		// r.setRecall(time--);
	}

	@Override
	public void cancel(Robot r) throws NotDoableException {
		// TODO Auto-generated method stub
		
	}

}
