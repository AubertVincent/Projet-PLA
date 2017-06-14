package operateur;

import carte.Map;
import entite.Direction;
import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import pickable.PickRandomMove;

public class RandomMove extends Action implements _Random {

	// Length of the movement
	private int lg;
	// Direction of the movement
	private Direction direction;

	public RandomMove() {
		super();
	}

	protected boolean isReachable(Robot r, Direction dir, int lg) {
		int x = r.getX();
		int y = r.getY();
		if (r.getMovePoints() >= lg) {
			for (int i = 1; i <= lg; i++) {
				switch (dir) {
				case NORTH:

					// check if we are not outside the map and then check if the
					// cell is reachable
					if (y - i < 0 || y - i > r.getMap().mapHeight() || !(r.getMap().getCell(x, y - i).isReachable())) {
						return false;
					}
					break;
				case SOUTH:
					if (y + i < 0 || y + i > r.getMap().mapHeight() || !(r.getMap().getCell(x, y + 1).isReachable())) {
						return false;
					}
					break;
				case EAST:
					if (x + i < 0 || x + i > r.getMap().mapWidth() || !(r.getMap().getCell(x + 1, y).isReachable())) {
						return false;
					}
					break;
				case WEST:
					if (x - i < 0 || x - i > r.getMap().mapWidth() || !(r.getMap().getCell(x - 1, y).isReachable())) {
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
				if (r.getY() < r.map.mapHeight() - 1) {
					isOnMap = true;
					dir = Direction.SOUTH;
				}
				break;
			case 3:
				if (r.getX() < r.map.mapWidth() - 1) {
					isOnMap = true;
					dir = Direction.EAST;
				}
				break;
			}
			r.setDirection(dir);
			// if the cell we got is not on map or not reachable, we try to get
			// another direction thanks to the random while we didn't get a one
			// which is reachable or on the map
		} while (!isOnMap || !isReachable(r, dir, lg));
		this.direction = dir;
		this.lg = lg;
		// Set all the cell being explored to explored in the explorationMap of
		// the robot
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

		Map myMap = r.getMap();
		// We have to check if there is at least one reachable cell around this
		// robot
		// (North, South, Est, West)
		if (x < r.getMap().mapWidth() && myMap.getCell(x + 1, y).isReachable()) {
			return true;
		}
		if (x > 0 && myMap.getCell(x - 1, y).isReachable()) {
			return true;
		}
		if (y > 0 && myMap.getCell(x, y - 1).isReachable()) {
			return true;
		}
		if (y < r.getMap().mapHeight() && myMap.getCell(x, y + 1).isReachable()) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return " RM ";
	}

	@Override
	public Class<? extends PickAble> getPickable() {
		return PickRandomMove.class;
	}

}
