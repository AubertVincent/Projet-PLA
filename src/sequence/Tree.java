package sequence;

import operateur.Operator;

public class Tree implements _Sequence {

	Operator op;
	_Sequence left, right;

	public Tree(Operator op, _Sequence left, _Sequence right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}

	@Override
	public boolean IsAction() {
		return false;
	}

	@Override
	public boolean IsTree() {
		return true;
	}

}
