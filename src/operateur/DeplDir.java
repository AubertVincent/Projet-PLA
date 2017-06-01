package operateur;

import entite.Entite;

public class DeplDir extends Mouvement {
	
	Direction dir;
	int lg;
	
	
	public void execute(Entite e){
		int x = e.getX();
		int y = e.getY();
		
		e.setD(dir);
		
		switch(dir){
		case NORD : y = y+lg; e.setY(y); break;
		case SUD : y = y-lg; e.setY(y); break;
		case EST : x = x+lg; e.setX(x); break;
		case OUEST : x = x-lg; e.setX(x); break;
		}	
		
	}
}
