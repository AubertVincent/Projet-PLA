package operateur;

import entite.Entite;
import personnages.Personnage;

public class ClassiqueAtq extends Attaque {

	public void execute(Personnage attaquant, Personnage ennemi) {
		int vieA = attaquant.getVie();
		int vieE = ennemi.getVie();
		int atqA = attaquant.getAttaque();
		int atqE = ennemi.getAttaque();

		vieA = java.lang.Math.max(vieA - atqE, 0);
		vieE = java.lang.Math.max(vieE - atqA, 0);

		attaquant.setVie(vieA);
		ennemi.setVie(vieE);
	}

	@Override
	public void execute(Entite e) {
		// TODO Auto-generated method stub
		
	}
}
