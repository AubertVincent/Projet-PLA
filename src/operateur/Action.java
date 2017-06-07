package operateur;

import entite.*;
import entite.GameException;
import sequence._Sequence;

public abstract class Action extends Operator implements _Sequence {

	@Override
	public boolean IsAction() {
		return true;
	}

	@Override
	public boolean IsTree() {
		return false;
	}

}
