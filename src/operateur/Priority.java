package operateur;

import entite.*;

public class Priority extends Behavior {


	public Priority(Action A, Action B) {
		super(A, B);
	}

	@Override
	protected void execute(Entity e) throws GameException {
		if (A.isDoable()) {
			A.execute(e);
		} else if (B.isDoable()){
			B.execute(e);
		}else{
			throw new GameException("Aucune des deux actions n'est possible");
		}
	}

	@Override
	protected boolean isDoable() {
		// TODO Auto-generated method stub
		return false;
	}
}
