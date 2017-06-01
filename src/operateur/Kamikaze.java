package operateur;

import entite.Entite;
import personnages.Personnage;

public class Kamikaze extends Attaque {

	@Override
	public void execute(Personnage attaquant, Personnage ennemi) {
		attaquant.setVie(0);
		ennemi.setVie(0);

	}

	@Override
	public void execute(Entite e) {
		// TODO Auto-generated method stub

	}

}
