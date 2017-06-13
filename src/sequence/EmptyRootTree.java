package sequence;

public class EmptyRootTree implements _IncompleteSequence {

	_IncompleteSequence left, right;

	public _IncompleteSequence getLeft() {
		return left;
	}

	public _IncompleteSequence getRight() {
		return right;
	}

	public EmptyRootTree(_IncompleteSequence left, _IncompleteSequence right) {
		super();
		this.left = left;
		this.right = right;
	}

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
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

	@Override
	public boolean isEmptyLeaf() {
		return false;
	}

}
