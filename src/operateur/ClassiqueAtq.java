package operateur;

import entite.Entite;
import entite.GameException;
import personnages.*;

public class ClassiqueAtq extends Attaque {

	Personnage ennemi;

	// Uniquement executable d'un joueur vers un personnage
	public void execute(Joueur attaquant, Personnage ennemi) {
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
		int x = e.getX();
		int y = e.getY();
		Direction d;
		if (!e.isPersonnage()){
			throw new GameException("Cette entité n'est pas un personnage");
		}else{
			if (/*TODO cellule Est pleine*/){
				d = Direction.EST;
				e.setD(d);			
			}else if (/*TODO cellule Nord pleine*/){
				d = Direction.NORD;
				e.setD(d);
			}else if (/*TODO cellule Ouest pleine*/){
				d = Direction.OUEST;
				e.setD(d);
			}else if (/*TODO cellule Sud pleine*/){
				d = Direction.SUD;
				e.setD(d);
			}
			else{throw new GameException("Il n'y a personne à attaquer");}
			
		}
	}

}
