package operateur;
import sequence._Sequence;

public abstract class Action extends Operator implements _Sequence {

	public Action(int x, int y) {
		super();
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

}
