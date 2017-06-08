package operateur;

import carte.*;
import entite.*;
import exceptions.NotDoableException;
import personnages.Robot;

public class MoveDir extends Movement {

	protected Direction dir;
	protected Integer lg;

	@Override
	public String toString() {
		return (super.toString() + "(" + dir.toString() + ", " + lg.toString() + ")");
	}

	/**
	 * Set a new move by means of its direction and its length
	 * 
	 * @param dir
	 *            Direction of the move
	 * @param lg
	 *            Length of the move
	 */
	public MoveDir(Direction dir, int lg) {
		super();
		this.dir = dir;
		this.lg = lg;
	}

	public MoveDir() {
		super();
	}

	/**
	 * A move can be done if there is no obstacle
	 */
	@Override

	protected boolean isDoable(Robot r) {
		int x = r.getX();
		int y = r.getY();
		Map myMap = r.getEntityMap();

		for (int i = 0; i < lg; i++) {
			switch (dir) {
			case NORTH:
				if (!myMap.isReachable(x, y - i)) {
					return false;
				}
				break;
			case EAST:
				if (!myMap.isReachable(x + i, y)) {
					return false;
				}
				break;
			case SOUTH:
				if (!myMap.isReachable(x, y + i)) {
					return false;
				}
				break;
			case WEST:
				if (!myMap.isReachable(x - i, y)) {
					return false;
				}
				break;
			}
		}
		return true;
	}

	public void execute(Robot r) throws NotDoableException {

		if (!isDoable(r)) {
			throw new NotDoableException("Un obstacle est sur votre chemin");
		}
		r.goTo(dir, lg);

	}

	@Override
	public void cancel(Robot r) throws NotDoableException {
		if (!isDoable(r)) {
			throw new NotDoableException("Un obstacle est sur votre chemin");
		}
		switch (dir) {
		case NORTH:
			dir = Direction.SOUTH;
			break;
		case SOUTH:
			dir = Direction.NORTH;
			break;
		case EAST:
			dir = Direction.WEST;
			break;
		case WEST:
			dir = Direction.EAST;
			break;
		}
		r.goTo(dir, lg);
	}

}
