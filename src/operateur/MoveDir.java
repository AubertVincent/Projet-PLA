package operateur;

import carte.*;
import entite.*;
import personnages.Character;

public class MoveDir extends Movement {

	protected Direction dir;
	protected int lg;

	public MoveDir(Direction dir, int lg) {
		super();
		this.dir = dir;
		this.lg = lg;
	}

	public MoveDir() {
		super();
	}

	@Override
	protected boolean isDoable(Entity e) {
		int x = e.getX();
		int y = e.getY();

		for (int i = 0; i < lg; i++) {
			switch (dir) {
			case NORTH:
				if (!Map.isFree(x, y - i)) {
					return false;
				}
				break;
			case EAST:
				if (!Map.isFree(x + i, y)) {
					return false;
				}
				break;
			case SOUTH:
				if (!Map.isFree(x, y + i)) {
					return false;
				}
				break;
			case WEST:
				if (!Map.isFree(x - i, y)) {
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
