package operateur;
import sequence._Sequence;
import entite.*;
import entite.GameException;
import sequence._Sequence;

public abstract class Action extends Operator implements _Sequence {

	@Override
	public boolean isAction() {
		return true;
	}

	@Override
	public boolean isTree() {

		return false;
	}

}
