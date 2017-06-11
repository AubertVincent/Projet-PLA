package operateur;

import carte.Coordinates;
import exceptions.NotDoableException;
import personnages.Robot;

public class Tunnel extends Movement {

	protected Integer x;
	protected Integer y;
	private Coordinates lastCoord;

	/**
	 * set a new Tunnel by means of its arrival coordinates
	 * 
	 * @param x
	 *            x coordinate on the map
	 * @param y
	 *            y coordinate on the map
	 */
	public Tunnel(Coordinates coord) {
		super();
		this.x = coord.getX();
		this.y = coord.getY();
	}

	public Tunnel() {
		super();
	}

	public Tunnel(int x2, int y2) {
		super();
		this.x = x2;
		this.y = y2;
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
		this.lastCoord.setX(x);
		this.lastCoord.setY(y);
		r.teleport(lastCoord);
	}

	@Override
	public void cancel(Robot r) throws NotDoableException {
		r.teleport(lastCoord);
	}

	@Override
	public String toString() {
		return super.toString() + "(" + x.toString() + "," + y.toString() + ")";
	}

}
