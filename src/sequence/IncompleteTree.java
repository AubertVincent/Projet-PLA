package sequence;

import operateur.Behavior;

public class IncompleteTree implements _IncompleteSequence {

	Behavior op;
	_IncompleteSequence left, right;

	public IncompleteTree(Behavior op, _IncompleteSequence left, _IncompleteSequence right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}

	public Behavior getOp() {
		return op;
	}

	public _IncompleteSequence getLeft() {
		return left;
	}

	public _IncompleteSequence getRight() {
		return right;
	}

	@Override
	public boolean isAction() {
		return false;
	}

	@Override
	public boolean isTree() {
		return true;
	}

	@Override
	public boolean isEmptyRootTree() {
		return false;
	}

	@Override
	public String toString() {
		return op.toString() + "[" + left.toString() + " , " + right.toString() + "]";
	}

	@Override
	public boolean isEmptyLeaf() {
		return false;
	}

}
