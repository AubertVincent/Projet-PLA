package operateur;

import java.util.Random;
import entite.*;

public class RandomBar extends Behavior {

	public RandomBar(Action A, Action B) {
		super(A, B);
	}

	@Override
	protected boolean isDoable(Entity e) {
		return A.isDoable(e) || B.isDoable(e);
	}

	private Action randomAction(Action A, Action B) {
		Random r = new Random();
		int n = r.nextInt(2);
		if (n == 0) {
			return A;
		} else {
			return B;
		}
	}

	@Override
	protected void execute(Entity e) throws GameException {
		if (!(isDoable(e))) {
			throw new GameException("Aucune des deux actions n'est possible");
		} else if (!(A.isDoable(e))) {
			B.execute(e);
		} else if (!(B.isDoable(e))) {
			A.execute(e);
		} else {
			randomAction(A, B).execute(e);
		}
	}

}
