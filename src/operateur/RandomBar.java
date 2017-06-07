package operateur;

import java.util.Random;
import entite.*;

public class RandomBar extends Behavior {

	public RandomBar(int x, int y, Action A, Action B) {
		super(x, y, A, B);
	}

	public boolean test() {
		Random r = new Random();
		int n = r.nextInt(2);
		return (n == 0);
	}

	public void random(Entity e) throws GameException {
		// TODO Créer une nouvelle classe qui prend une séquence et qui gère
		// l'execution des actions
		if (test()) {
			A.execute(e);
		} else {
			B.execute(e);
		}
	}

}
