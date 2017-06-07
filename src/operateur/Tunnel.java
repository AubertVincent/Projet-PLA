package operateur;

import exceptions.NotDoableException;
import personnages.Robot;

public class Tunnel extends Movement {

	protected Integer x;
	protected Integer y;

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
	// TODO
	protected boolean isDoable(Robot r) {
		// Cell test = new Cell(x, y);
		// return test.isFree();
		return true;
	}

	@Override
	public void execute(Robot r) throws NotDoableException {
		if (!isDoable(r)) {
			throw new NotDoableException("La case d'arrivée est occupée");
		}

		r.teleport(x, y);

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + "(" + x.toString() + "," + y.toString() + ")";
	}

	
	
}
