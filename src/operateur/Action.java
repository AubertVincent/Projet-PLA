package operateur;

import exceptions.GameException;
import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import sequence._IncompleteSequence;
import sequence._Sequence;

public abstract class Action implements _Sequence, _IncompleteSequence {

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * Check is an entity is able to execute an action
	 * 
	 * @param e
	 *            The entity which do the action
	 * @return
	 */
	protected abstract boolean isDoable(Robot r);

	/**
	 * execute an operator on an Entity
	 * 
	 * @param e
	 *            The Entity which will execute the action
	 * @throws GameException
	 */
	// protected abstract void execute(Robot r) throws NotDoableException;

	@Override
	public boolean isAction() {
		return true;
	}

	@Override
	public boolean isTree() {
		return false;
	}

	@Override
	public boolean isEmptyLeaf() {
		return false;
	}

	// May become useful in the case of a game update
	public abstract void cancel(Robot r) throws NotDoableException;

	public abstract Class<? extends PickAble> getPickable();

	@Override
	public boolean isEmptyRootTree() {
		return false;
	}

}
