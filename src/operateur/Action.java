package operateur;

import exceptions.GameException;
import personnages.Robot;
import sequence._Sequence;

public abstract class Action implements _Sequence {

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

}
