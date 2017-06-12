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

	// FIXME This function is going to check in all the direction but it need to
	// check all the robot's path
	// Or mabye we just need to check in one direction ? In this case, just
	// replace 1 by i
	protected boolean isReachable(Robot r, Direction dir, int lg) {
		int x = r.getX();
		int y = r.getY();
		if (r.getMovePoints() >= lg) {
			for (int i = 1; i <= lg; i++) {
				switch (dir) {
				case NORTH:
					if (y - i < 0 || y - i > r.getEntityMap().mapHeight()
							|| !(r.getEntityMap().getCell(x, y - 1).isReachable())) {
						return false;
					}
					break;
				case SOUTH:
					if (y + i < 0 || y + i > r.getEntityMap().mapHeight()
							|| !(r.getEntityMap().getCell(x, y + 1).isReachable())) {
						return false;
					}
					break;
				case EAST:
					if (x + i < 0 || x + i > r.getEntityMap().mapWidth()
							|| !(r.getEntityMap().getCell(x + 1, y).isReachable())) {
						return false;
					}
					break;
				case WEST:
					if (x - i < 0 || x - i > r.getEntityMap().mapWidth()
							|| !(r.getEntityMap().getCell(x - 1, y).isReachable())) {
						return false;
					}
					break;
				}

			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void execute(Robot r) throws NotDoableException {
		// Test
		// System.out.println("J'execute le mouvement au hasard !");
		// end test

		if (!isDoable(r)) {
			throw new NotDoableException("Entité entourée de cases inaccessibles");
		}
		int d = 0;
		int lg = 0;
		// To verify if this the direction we got is not outside the map
		boolean isOnMap = false;
		Direction dir = null;
		do {
			lg = (int) (Math.random() * r.getMovePoints()) + 1;
			d = (int) (Math.random() * 4);
			switch (d) {
			case 0:
				if (r.getY() > 0) {
					isOnMap = true;
					dir = Direction.NORTH;
				}
				break;
			case 1:
				if (r.getX() > 0) {
					isOnMap = true;
					dir = Direction.WEST;
				}
				break;
			case 2:
				if (r.getY() < r.entityMap.mapHeight() - 1) {
					isOnMap = true;
					dir = Direction.SOUTH;
				}
				break;
			case 3:
				if (r.getX() < r.entityMap.mapWidth() - 1) {
					isOnMap = true;
					dir = Direction.EAST;
				}
				break;
			}
			r.setDirection(dir);
		} while (!isOnMap || !isReachable(r, dir, lg));
		this.direction = dir;
		this.lg = lg;
		switch (dir) {
		case NORTH:
			for (int i = 1; i <= lg; i++) {
				r.getExplorationMap().getCell(r.getX(), r.getY() - i).setExplored(true);
			}
			break;
		case EAST:
			for (int i = 1; i <= lg; i++) {
				r.getExplorationMap().getCell(r.getX() + 1, r.getY()).setExplored(true);
			}
			break;
		case SOUTH:
			for (int i = 1; i <= lg; i++) {
				r.getExplorationMap().getCell(r.getX(), r.getY() + i).setExplored(true);
			}
			break;
		case WEST:
			for (int i = 1; i <= lg; i++) {
				r.getExplorationMap().getCell(r.getX() - i, r.getY()).setExplored(true);
			}
			break;
		}
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
		int x = r.getX();
		int y = r.getY();
		Map myMap = r.getEntityMap();
		boolean isInCorner = ((x == 0 && y == 0) || (x == 0 && y == myMap.mapHeight() - 1)
				|| (x == myMap.mapWidth() - 1 && y == 0) || (x == myMap.mapWidth() - 1 && y == myMap.mapHeight() - 1));
		if (x == 0 && y == 0 && !myMap.getCell(x, y + 1).isReachable() && !myMap.getCell(x + 1, y).isReachable()) {
			return false;
		} else if (x == myMap.mapWidth() - 1 && y == 0 && !myMap.getCell(x, y + 1).isReachable()
				&& !myMap.getCell(x - 1, y).isReachable()) {
			return false;
		} else if (x == myMap.mapWidth() - 1 && y == myMap.mapHeight() - 1 && !myMap.getCell(x, y - 1).isReachable()
				&& !myMap.getCell(x - 1, y).isReachable()) {
			return false;
		} else if (x == 0 && y == myMap.mapHeight() - 1 && !myMap.getCell(x, y - 1).isReachable()
				&& !myMap.getCell(x + 1, y).isReachable()) {
			return false;
		} else if (!isInCorner && x == 0 && !myMap.getCell(x, y - 1).isReachable()
				&& !myMap.getCell(x, y + 1).isReachable() && !myMap.getCell(x + 1, y).isReachable()) {
			return false;
		} else if (!isInCorner && y == 0 && !myMap.getCell(x + 1, y).isReachable()
				&& !myMap.getCell(x, y + 1).isReachable() && !myMap.getCell(x - 1, y).isReachable()) {
			return false;
		} else if (!isInCorner && y == myMap.mapHeight() - 1 && !myMap.getCell(x + 1, y).isReachable()
				&& !myMap.getCell(x, y - 1).isReachable() && !myMap.getCell(x - 1, y).isReachable()) {
			return false;
		} else if (!isInCorner && x == myMap.mapWidth() - 1 && !myMap.getCell(x, y - 1).isReachable()
				&& !myMap.getCell(x, y + 1).isReachable() && !myMap.getCell(x - 1, y).isReachable()) {
			return false;
		} else {
			return true;
		}
	}
}
