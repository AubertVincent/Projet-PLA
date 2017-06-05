package operateur;

import entite.*;

public class Succession extends Behavior {

	public Succession(int x, int y, Action A, Action B) {
		super(x, y, A, B);
	}

	public void succession(Entity e) throws GameException {
		A.execute(e);
		B.execute(e);
	}
}
