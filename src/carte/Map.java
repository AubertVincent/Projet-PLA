package carte;

import java.util.concurrent.ThreadLocalRandom;

import operateur.Operator;

public class Carte {

	private static final int nbrOpInit = 128; // une chance sur 4 de trouver un
												// opérateur sur une cellule
	private static final int largeur = 32;
	private static final int hauteur = 16;
	public static final Cellule[][] carte = new Cellule[hauteur][largeur];

	public Carte() {
		for (int i = 0; i < hauteur; i++) {
			for (int j = 0; j < largeur; j++) {
				carte[i][j] = new Cellule(i, j);
			}
		}
	}

	public static void initCarte() {
		int randomLine;
		int randomColumn;
		int i = 0;
		while (i < nbrOpInit) {
			randomLine = ThreadLocalRandom.current().nextInt(0, hauteur);
			randomColumn = ThreadLocalRandom.current().nextInt(0, largeur);
			if (carte[randomLine][randomColumn].isEmpty()) {
				carte[randomLine][randomColumn].setEntite(Operator.randomOp());
				i++;
			}
		}
	}

	public boolean isEmpty() {

		boolean mon_bool = true;
		for (int i = 0; i < hauteur && mon_bool; i++) {
			for (int j = 0; j < largeur && mon_bool; j++) {
				mon_bool = carte[i][j].isEmpty();
			}
		}
		return mon_bool;
	}

}
