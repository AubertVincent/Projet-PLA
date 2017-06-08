package operateur;

import exceptions.NotDoableException;
import personnages.Robot;

public class Tunnel extends Movement {

	protected Integer x;
	protected Integer y;
	private int lastX;
	private int lastY;

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
		this.lastX = x;
		this.lastY = y;
		r.teleport(x, y);
	}

	@Override
	public void cancel(Robot r) throws NotDoableException {
		r.teleport(lastX, lastY);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + "(" + x.toString() + "," + y.toString() + ")";
	}

}
