package operateur;

import entite.*;

public abstract class Operator {
	
	/**
	 * execute an operator on an Entity
	 * @param e The Entity which will execute the action
	 * @throws GameException
	 */
	protected abstract void execute(Entity e) throws GameException;

	/**
	 * Check if an action can be done
	 * @return true is it can be done, else return false
	 */
	protected abstract boolean isDoable();

	public static Entity randomOp() {
		// TODO 
		return null;
	}

}
