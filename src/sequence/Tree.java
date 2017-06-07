package sequence;

import operateur.Behavior;

public class Tree implements _Sequence {

	_Sequence left, right;
	Behavior node;

	@Override
	public boolean isNil() {
		return false;
	}

	@Override
	public boolean isAction() {
		return false;
	}

	@Override
	public boolean isTree() {
		return true;
	}

}
