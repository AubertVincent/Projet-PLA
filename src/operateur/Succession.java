package operateur;

import entite.*;

public class Succession extends Behavior {

	public Succession(Action A, Action B) {
		super(A, B);
	}

	protected void execute(Entity e) throws GameException {
		A.execute(e);
		B.execute(e);
	}

	@Override
	protected boolean isDoable() {
		return A.isDoable() && B.isDoable();
	}
}
