package operateur;

import entite.Entite;
import entite.GameException;
import entite.Entite.Direction;
import personnages.Personnage;

public class KamikazeAtq extends Attaque {

	@Override
	public void execute(Entite e) {
		int x = e.getX();
		int y = e.getY();
		Direction d;
		if (!e.isPersonnage()){
			throw new GameException("Cette entité n'est pas un personnage");
		}else{
			
	}

}



}
