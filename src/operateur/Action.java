package operateur;

import entite.*;
import entite.GameException;
import sequence._Sequence;

public abstract class Action extends Operator implements _Sequence {

	public Action(int x, int y) {
		super(x, y);
	}

	@Override
	public boolean IsAction() {
		return true;
	}

	@Override
	public boolean IsTree() {
		return false;
	}

	public abstract void execute(Entity e) throws GameException;

	public abstract boolean isDoable();
}
