package sequence;

import exceptions.NotDoableException;
import operateur.Behavior;
import personnages.Robot;
import pickable.PickAble;

public class Tree implements _Sequence {

	Behavior op;
	_Sequence left, right;

	public Tree(Behavior op, _Sequence left, _Sequence right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}

	@Override
	public boolean isAction() {
		return false;
	}

	@Override
	public boolean isTree() {
		return true;
	}

	public void execute(Robot r) throws NotDoableException {
		op.execute(r, left, right);
	}

	@Override
	public String toString() {
		return op.toString() + "[" + left.toString() + " , " + right.toString() + "]";
	}

	public Behavior getOp() {
		return op;
	}

	public _Sequence getLeft() {
		return left;
	}

	public _Sequence getRight() {
		return right;
	}

	public Class<? extends PickAble> getOpPickable() {
		try {
			return op.getPickable();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
