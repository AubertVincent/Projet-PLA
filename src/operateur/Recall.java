package operateur;

import exceptions.NotDoableException;
import personnages.Robot;

public class Recall extends Movement {

	protected Integer time;

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

		r.teleport(0, 0); // TODO différencier pour
							// les deux joueurs

	}

	@Override
	public String toString() {
		return super.toString() + "(" + time.toString() + ")";
	}

	
	
}
