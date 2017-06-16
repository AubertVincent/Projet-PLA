package operateur;

import carte.Map;
import entite.Direction;
import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import pickable.PickMoveDir;

public class MoveDir extends Movement {

	private Direction dir;
	private Integer lg;

	// ↓ Constructor, update and render ↓

	/**
	 * Set a new move by means of its direction and its length
	 * 
	 * @param dir
	 *            Direction of the move
	 * @param lg
	 *            Length of the move
	 */
	public MoveDir(Direction dir, int lg) {
		this.dir = dir;
		this.lg = lg;
	}

	// End(Constructor, update and render)

	// ↓ Miscellaneous methods ↓

	/**
	 * A move can be done if there is no obstacle
	 */
	@Override
	protected boolean isDoable(Robot r) {
		int x = r.getX();
		int y = r.getY();

		Map myMap = r.getMap();
		// Test if there is enough move point and if it didn't go outside the
		// map
		// Check if we got enough move point for this movement
		if (r.getMovePoints() >= lg) {
			// Check all the cell on the path and return false if one of
			// it is unreachable
			for (int i = 1; i <= lg; i++) {
				switch (dir) {
				case NORTH:
					if (y - i < 0 || y - i > myMap.mapHeight() || !myMap.getCell(x, y - i).isReachable()) {
						return false;
					}
					break;
				case EAST:

					if (x + i < 0 || x + i > myMap.mapWidth() || !myMap.getCell(x + i, y).isReachable()) {
						return false;
					}
					break;
				case SOUTH:
					if (y + i < 0 || y + i > myMap.mapHeight() || !myMap.getCell(x, y + i).isReachable()) {
						return false;
					}
					break;
				case WEST:

					if (x - i < 0 || x - i > myMap.mapWidth() || !myMap.getCell(x - i, y).isReachable()) {
						return false;

					}
					break;
				}
			}

		} else {
			return false;
		}
		return true;
	}

	public void execute(Robot r) throws NotDoableException {
		// test
		// System.out.println("J'execute un mouvement !");
		// end test
		if (!isDoable(r)) {
			throw new NotDoableException("You can't go there (Not enough move points or unreachable cell)");
		}
		switch (dir) {
		// For the function explore : put explored on every cell this robot went
		// through
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
	public Class<? extends PickAble> getPickable() {
		return PickMoveDir.class;
	}

	@Override
	public String toString() {
		return "MC" + lg.toString() + dir.toString();
	}

	// End(Miscellaneous methods)

}
