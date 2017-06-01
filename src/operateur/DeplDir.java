package operateur;

import entite.Entite;

public class DeplDir extends Mouvement {
	
	
	public void deplacement(Entite e, int longueur, Direction d){
		int x = e.getX();
		int y = e.getY();
		
		e.setD(d);
		
		switch(d){
		case NORD : y = y+longueur; e.setY(y); break;
		case SUD : y = y-longueur; e.setY(y); break;
		case EST : x = x+longueur; e.setX(x); break;
		case OUEST : x = x-longueur; e.setX(x); break;
		}	
		
	}
}
