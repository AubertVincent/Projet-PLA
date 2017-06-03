package operateur;

import entite.*;
import personnages.*;

public class ClassicAck extends Attack {

	

	public ClassicAck(int x, int y) {
		super(x, y);
	}

	public void execute(Caracter attacker, Caracter opponent) {
		int lifeA = attacker.getLife();
		int lifeE = opponent.getLife();
		int atkA = attacker.getAttack();
		int atkE = opponent.getAttack();

		lifeA = java.lang.Math.max(lifeA - atkE, 0);
		lifeE = java.lang.Math.max(lifeE - atkA, 0);

		attacker.setLife(lifeA);
		opponent.setLife(lifeE);
	}

	@Override
	public boolean isDoable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(Entity e) throws GameException {
		
		if (!isDoable()){
			throw new GameException("Cette action n'est pas réalisable");
		}	
		int x = e.getX();
		int y = e.getY();
		Direction d;
		if (!e.isCaracter()){
			throw new GameException("Cette entité n'est pas un personnage");
		}else{
			if (/*TODO cellule Est pleine*/){
				d = Direction.EAST;
				((Caracter) e).setDirection(d);			
			}else if (/*TODO cellule Nord pleine*/){
				d = Direction.NORTH;
				((Caracter) e).setDirection(d);
			}else if (/*TODO cellule Ouest pleine*/){
				d = Direction.WEST;
				((Caracter) e).setDirection(d);
			}else if (/*TODO cellule Sud pleine*/){
				d = Direction.SOUTH;
				((Caracter) e).setDirection(d);
			}
			else{
				throw new GameException("Il n'y a personne à attaquer");}
			
		}
	}


}
