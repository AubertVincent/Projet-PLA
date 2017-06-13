package personnages;

import sequence._IncompleteSequence;

public class EmptyLeaf implements _IncompleteSequence {

	@Override
	public boolean isAction() {
		return false;
	}

	@Override
	public boolean isTree() {
		return false;
	}

	@Override
	public boolean isEmptyRootTree() {
		return false;
	}

	@Override
	public boolean isEmptyLeaf() {
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
