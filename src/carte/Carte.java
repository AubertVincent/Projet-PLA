package carte;

import carte.Cellule;

public class Carte {

	private static final int size = 32;
	protected final Cellule[][] carte = new Cellule[size][size];

	public Carte() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				carte[i][j] = new Cellule(i,j);
			}
		}
	
	}
}
