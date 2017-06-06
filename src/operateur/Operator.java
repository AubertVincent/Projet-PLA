package operateur;

import entite.*;

public abstract class Operator {

	public Operator() {
		super();
	}
	
	protected abstract void execute(Entity e) throws GameException;

	protected abstract boolean isDoable();
	
	protected boolean isCharacter() {
		return false;
	}

	protected boolean isOperator() {
		return true;
	}

	public static Entity randomOp() {
		// TODO 
		return null;
	}

}
