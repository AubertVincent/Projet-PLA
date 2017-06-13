package operateur;

import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import pickable.PickTunnel;

public class Tunnel extends Movement {

	protected Integer x;
	protected Integer y;
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
		return (r.getEntityMap().getCell(x, y).isReachable());
	}

	@Override
	public void execute(Robot r) throws NotDoableException {
		if (!isDoable(r)) {
			throw new NotDoableException("La case d'arrivée est occupée");
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
