package operateur;

import entite.*;

public abstract class Operator extends entite.Entity {

	public Operator(int x, int y) {
		super(x, y);
	}

	public boolean isCharacter() {
		return false;
	}

	public boolean isOperator() {
		return true;
	}

	public static Entity randomOp() {
		// TODO 
		return null;
	}

}
