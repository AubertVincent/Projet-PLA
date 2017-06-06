package operateur;

import entite.*;
import entite.GameException;

public abstract class Action extends Operator {

	public Action(int x, int y) {
		super(x, y);
	}

	public abstract void execute(Entity e) throws GameException;

	public abstract boolean isDoable();
}
