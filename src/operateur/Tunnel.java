package operateur;

import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import pickable.PickTunnel;

public class Tunnel extends Movement {

	private Integer x;
	private Integer y;
	private int lastX;
	private int lastY;
	private boolean lastExploration = false;

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
		if (x < r.getEntityMap().mapWidth() && y < r.getEntityMap().mapHeight() && x > 0 && y > 0) {
			return (r.getEntityMap().getCell(x, y).isReachable());
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
		this.lastX = x;
		this.lastY = y;
		r.teleport(x, y);
		if (r.getExplorationMap().getCell(x, y).isExplored()) {
			lastExploration = true;
		} else {
			r.getExplorationMap().getCell(x, y).setExplored(true);
		}
	}

	@Override
	public void cancel(Robot r) throws NotDoableException {
		r.teleport(lastX, lastY);
		if (lastExploration) {
			r.getExplorationMap().getCell(x, y).setExplored(false);
		}
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
