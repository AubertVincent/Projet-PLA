package sequence;

import java.util.LinkedList;
import java.util.List;

import carte.Map;
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
			// Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<PickAble> sequenceToPickAbleList(int x, int y, Map map) {
		List<PickAble> pickAbleList = new LinkedList<PickAble>();
		Behavior behavior = this.getOp();
		pickAbleList.add(behavior.behaviorToPickAble(x, y, map));
		pickAbleList.addAll(getLeft().sequenceToPickAbleList(x, y, map));
		pickAbleList.addAll(getRight().sequenceToPickAbleList(x, y, map));
		return pickAbleList;
	}

}
