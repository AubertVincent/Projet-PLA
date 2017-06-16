package operateur;

import carte.Map;
import entite.Direction;
import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import pickable.PickRandomMove;

public class RandomMove extends Action implements _Random {

	// ↓ Constructor, update and render ↓

	public RandomMove() {
		super();
	}

	// End(Constructor, update and render)

	// ↓ Miscellaneous methods ↓

	/**
	 * return true if the cell the robot should go is reachable
	 * 
	 * @param r
	 *            : The robot we have to move
	 * @param dir
	 *            : Direction where the robot should go to
	 * @param lg
	 *            : Number of cells that the robot needs to traverse
	 * @return If there is any obstacle on this robot's path, or if he's going
	 *         outside the map the function return false
	 */
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
					if (y + i < 0 || y + i > r.getMap().mapHeight() || !(r.getMap().getCell(x, y + i).isReachable())) {
						return false;
					}
					break;
				case EAST:
					if (x + i < 0 || x + i > r.getMap().mapWidth() || !(r.getMap().getCell(x + i, y).isReachable())) {
						return false;
					}
					break;
				case WEST:
					if (x - i < 0 || x - i > r.getMap().mapWidth() || !(r.getMap().getCell(x - i, y).isReachable())) {
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

	/**
	 * @param r
	 *            : The robot which is going to execution this action
	 * @return Nothing
	 * 
	 */
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
		Direction dir = null;
		do {
			lg = (int) (Math.random() * r.getMovePoints()) + 1;
			d = (int) (Math.random() * 4);
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
			// if the cell we got is not on map or not reachable, we try to get
			// another direction thanks to the random while we didn't get a one
			// which is reachable or on the map
		} while (!isReachable(r, dir, lg));
		r.setDirection(dir);

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

	// End(Miscellaneous methods)

}
