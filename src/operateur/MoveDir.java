package operateur;

import carte.*;
import entite.*;
import exceptions.GameException;
import exceptions.NotDoableException;
import personnages.Character;
import personnages.Robot;

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

	protected boolean isDoable(Robot r) {
		int x = r.getX();
		int y = r.getY();
		Map myMap = r.getEntityMap();

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

	public void execute(Robot r) throws NotDoableException {

		if (!isDoable(r)) {
			throw new NotDoableException("Un obstacle est sur votre chemin");
		}

		r.goTo(dir, lg);

	}

}
