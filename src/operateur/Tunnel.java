package operateur;

import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import pickable.PickTunnel;

public class Tunnel extends Movement {

	protected Integer x;
	protected Integer y;
	// Used in the case of the need of the cancel method
	// private int lastX;
	// private int lastY;
	// private boolean lastExploration = false;

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

		// If the cell targeted by the Tunnel is on the map and is reachable,
		// return true
		if (x < r.getMap().mapWidth() && y < r.getMap().mapHeight() && x > 0 && y > 0) {
			return (r.getMap().getCell(x, y).isReachable());
		} else {
			return false;
		}
	}

	@Override
	public void execute(Robot r) throws NotDoableException {
		// test
		// System.out.println("J'execute le tunnel !");
		// endtest
		if (!isDoable(r)) {
			throw new NotDoableException("La case d'arrivée est inateignable");
		}
		// Used in the case of the need of the cancel method
		// this.lastX = x;
		// this.lastY = y;
		// Teleport the robot to the coordinates given
		r.teleport(x, y);
		// Used in the case of the need of the cancel method
		// if (r.getExplorationMap().getCell(x, y).isExplored()) {
		// lastExploration = true;
		// } else {
		// Set the Cell reached to explored in the explorationMap of the
		// robot.
		r.getExplorationMap().getCell(x, y).setExplored(true);
		// }

	}

	@Override
	public void cancel(Robot r) throws NotDoableException {
		// r.teleport(lastX, lastY);
		// if (lastExploration) {
		// r.getExplorationMap().getCell(x, y).setExplored(false);
		// }

	}

	@Override
	public String toString() {
		return "MT" + x.toString() + "." + y.toString();
	}

	@Override
	public Class<? extends PickAble> getPickable() {
		return PickTunnel.class;
	}

}
