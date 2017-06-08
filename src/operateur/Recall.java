package operateur;

import entite.Team;
import exceptions.NotDoableException;
import personnages.Robot;

public class Recall extends Movement {

	protected int time;
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
		if (r.getTeam() == Team.ROUGE && r.getEntityMap().isReachable(2, 4)) {
			return true;
		}
		if (r.getTeam() == Team.BLEU && r.getEntityMap().isReachable(31, 15)) {
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
		if (this.isDoable(r)) {
			this.lastX = r.getX();
			this.lastY = r.getY();
			r.teleport(xBase, yBase);
		}
		// r.setRecall(time--);
	}

	
	@Override
	public void cancel(Robot r) throws NotDoableException {
		//teleport le robot à la position avant le recall
		r.teleport(this.lastX, this.lastY);
		//r.cancelRecall();
	}

}
