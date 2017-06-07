package operateur;

import exceptions.NotDoableException;
import personnages.Robot;

public class Tunnel extends Movement {

	protected int x;
	protected int y;

	/**
	 * set a new Tunnel by means of its arrival coordinates
	 * 
	 * @param x
	 *            x coordinate on the map
	 * @param y
	 *            y coordinate on the map
	 */
	public Tunnel(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Tunnel() {
		super();
	}

	/**
	 * Check if there is no obstacle on the arrival
	 */
	@Override
	protected boolean isDoable(Robot r) {
		return (r.getEntityMap().isReachable(x, y));
	}

	@Override
	public void execute(Robot r) throws NotDoableException {
		if (!isDoable(r)) {
			throw new NotDoableException("La case d'arrivée est occupée");
		}
		r.teleport(x, y);
	}

	@Override
	public void cancel(Robot r) throws NotDoableException {
		// TODO Auto-generated method stub

	}

}
