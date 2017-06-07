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
		return true;
	}

	@Override
	public void execute(Robot r) throws NotDoableException {
		// TODO Intégrer la notion de temps, ce callback s'excute dès qu'on
		// l'appelle.
		// int time = r.getRecall();;
		// if (r.getRecall() == 0) {
		if (r.getPlayer() == 1) {
			r.teleport(2, 4);
		} else if (r.getPlayer() == 2) {
			r.teleport(31, 15);
		}

		// r.setRecall(time--);
	}

}
