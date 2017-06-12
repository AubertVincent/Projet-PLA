package operateur;

import carte.Map;
import entite.Direction;
import exceptions.NotDoableException;
import personnages.Robot;

public class RandomMove extends Action implements _Random {

	private int lg; // Length of the movement
	private Direction direction; // Direction of the movement

	public RandomMove() {
		super();
	}

	protected boolean isReachable(Robot r, Direction dir, int lg) {
		int x = r.getCoordinates().getX();
		int y = r.getCoordinates().getY();
		for (int i = 1; i <= lg; i++) {
			switch (dir) {
			case NORTH:
				if (r.getEntityMap().isReachable(x, y - 1)) {
					return false;
				}
				break;
			case SOUTH:
				if (r.getEntityMap().isReachable(x, y + 1)) {
					return false;
				}
				break;
			case EAST:
				if (r.getEntityMap().isReachable(x + 1, y)) {
					return false;
				}
				break;
			case WEST:
				if (r.getEntityMap().isReachable(x - 1, y)) {
					return false;
				}
				break;
			}

		}
		return true;
	}

	@Override
	public void execute(Robot r) throws NotDoableException {
		int d = 0;
		int lg = 0;
		Direction dir = null;
		do {
			d = (int) (Math.random() * 4);
			lg = (int) (Math.random() * r.getMovePoints()) + 1;
			switch (d) {
			case 0:
				dir = Direction.NORTH;
				break;
			case 1:
				dir = Direction.WEST;
				break;
			case 2:
				dir = Direction.SOUTH;
				break;
			case 3:
				dir = Direction.EAST;
				break;
			}
			r.setDirection(dir);
		} while (!isReachable(r, dir, lg));
		this.direction = dir;
		this.lg = lg;
		r.goTo(dir, lg);
	}

	@Override
	public void cancel(Robot r) throws NotDoableException {
		switch (direction) {
		case NORTH:
			direction = Direction.SOUTH;
			break;
		case SOUTH:
			direction = Direction.NORTH;
			break;
		case WEST:
			direction = Direction.EAST;
			break;
		case EAST:
			direction = Direction.WEST;
			break;
		}
		r.goTo(direction, lg);
	}

	@Override
	protected boolean isDoable(Robot r) {
		int x = r.getCoordinates().getX();
		int y = r.getCoordinates().getY();
		Map myMap = r.getEntityMap();
		if (!myMap.isReachable(x, y - 1) && !myMap.isReachable(x + 1, y) && !myMap.isReachable(x, y + 1)
				&& !myMap.isReachable(x - 1, y)) {
			return false;
		}
		return true;
	}
}
