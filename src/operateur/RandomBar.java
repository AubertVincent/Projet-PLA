package operateur;

import java.util.Random;
import entite.*;

public class RandomBar extends Behavior {

	/**
	 * 
	 * @param x
	 * @param y
	 * @param A
	 * @param B
	 */
	public RandomBar(Action A, Action B) {
		super(A, B);
	}

	
	@Override
	protected boolean isDoable() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean test() {
		Random r = new Random();
		int n = r.nextInt(2);
		return (n == 0);
	}

	@Override
	protected void execute(Entity e) throws GameException {
		// TODO Créer une nouvelle classe qui prend une séquence et qui gère
		// l'execution des actions
		if (!(A.isDoable())) {
			if (!(B.isDoable())) {
				throw new GameException("aucune des deux actions n'est possible");
			} else {
				B.execute(e);
			}
		} else if (!(B.isDoable())) {
			A.execute(e);
		} else if (test()) {
			A.execute(e);
		} else {
			B.execute(e);
		}
	}

	

}
