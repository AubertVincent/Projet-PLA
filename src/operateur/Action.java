package operateur;

import entite.*;
import entite.GameException;
import sequence._Sequence;

public abstract class Action extends Operator implements _Sequence {

	public Action(int x, int y) {
		super(x, y);
	}

	@Override
	public boolean isNil() {
		return false;
	}

	@Override
	public boolean isAction() {
		return true;
	}

	@Override
	public boolean isTree() {
		return false;
	}

	public abstract void execute(Entity e) throws GameException;

	public abstract boolean isDoable();
}
