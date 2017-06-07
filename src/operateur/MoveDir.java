package operateur;

import carte.*;
import entite.*;
import personnages.Character;

public class MoveDir extends Movement {

	protected Direction dir;
	protected int lg;

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
	protected boolean isDoable(Entity e) {
		int x = e.getX();
		int y = e.getY();
		Map myMap = e.getEntityMap();

		for (int i = 0; i < lg; i++) {
			switch (dir) {
			case NORTH:
				if (!myMap.isFree(x, y - i)) {
					return false;
				}
				break;
			case EAST:
				if (!myMap.isFree(x + i, y)) {
					return false;
				}
				break;
			case SOUTH:
				if (!myMap.isFree(x, y + i)) {
					return false;
				}
				break;
			case WEST:
				if (!myMap.isFree(x - i, y)) {
					return false;
				}
				break;
			}
		}
		return true;
	}

	protected void execute(Entity e) throws GameException {

		if (!isDoable(e)) {
			throw new GameException("Un obstacle est sur votre chemin");
		}

		((Character) e).goTo(dir, lg);

	}

}
