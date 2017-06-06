package operateur;

import entite.*;

public abstract class Operator {

	/**
	 * execute an operator on an Entity
	 * 
	 * @param e
	 *            The Entity which will execute the action
	 * @throws GameException
	 */
	protected abstract void execute(Entity e) throws GameException;

	/**
	 * Check if an action can be done
	 * 
	 * @return true is it can be done, else return false
	 */

	public static Entity randomOp() {
		// TODO
		return null;
	}

	/**
	 * Check is an entity is able to execute an action
	 * @param e The entity which do the action
	 * @return
	 */
	protected abstract boolean isDoable(Entity e);
}
