package operateur;

import java.util.Random;

public class RandomBar extends Behavior {

	public RandomBar(int x, int y) {
		super(x, y);
	}

	public boolean test() {
		Random r = new Random();
		int n = r.nextInt(2);
		return (n == 0);
	}

	public void aleatoire(Action a, Action b) {
		// TODO Créer une nouvelle classe qui prend une séquence et qui gère
		// l'execution des actions
		if (test()) {
			// TODO a.execute();
		} else {
			// TODO b.execute();
		}
	}

}
