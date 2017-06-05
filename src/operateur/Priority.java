package operateur;

import entite.*;

public class Priority extends Behavior {

	public Priority(int x, int y, Action A, Action B) {
		super(x, y, A, B);
	}

	public void priority(Entity e) throws GameException {
		if (A.isDoable()) {
			A.execute(e);
		} else {
			B.execute(e);
		}
	}
}
