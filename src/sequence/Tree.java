package sequence;

import exceptions.GameException;
import exceptions.NotDoableException;
import operateur.Behavior;
import personnages.Robot;

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
	
	public void execute(Robot r) throws NotDoableException{
		op.execute(r, left, right);
	}

	@Override
	public String toString() {
		return op.toString() + "[" + left.toString() + " , " + right.toString() + "]";
	}

	@Override
	public void cancel(Robot r) throws NotDoableException {
		// TODO Auto-generated method stub
		
	}

	
	
}
