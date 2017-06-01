package operateur;

import entite.Entite;
import personnages.Personnage;

public abstract class Attaque extends Action {
	
	Personnage ennemi;
	Personnage attaquant;
	
	public abstract void execute(Personnage attaquant, Personnage ennemi);

}
