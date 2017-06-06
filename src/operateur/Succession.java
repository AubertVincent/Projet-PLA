package operateur;

import entite.*;

public class Succession extends Behavior {

	public Succession(Action A, Action B) {
		super(A, B);
	}

	@Override
	protected boolean isDoable(Entity e) {
		return A.isDoable(e) && B.isDoable(e);
	}

	protected void execute(Entity e) throws GameException {
		if(!(isDoable(e))){
			throw new GameException("Une des deux actions n'est pas réalisable");
		}
		A.execute(e);
		B.execute(e);
	}

}
