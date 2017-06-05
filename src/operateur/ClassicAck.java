package operateur;

import carte.Cellule;
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

		if (!isDoable()) {
			throw new GameException("Cette action n'est pas réalisable");
		}
		int x = e.getX();
		int y = e.getY();
		Direction d;
		Cellule testEast = new Cellule(x + 1, y);
		Cellule testSouth = new Cellule(x, y - 1);
		Cellule testNorth = new Cellule(x, y + 1);
		Cellule testWest = new Cellule(x - 1, y);
		if (!e.isCaracter()) {
			throw new GameException("Cette entité n'est pas un personnage");
		} else {
			if (!(testEast.isEmpty())) {
				d = Direction.EAST;
				((Caracter) e).setDirection(d);
			} else if (!(testNorth.isEmpty())) {
				d = Direction.NORTH;
				((Caracter) e).setDirection(d);
			} else if (!(testWest.isEmpty())) {
				d = Direction.WEST;
				((Caracter) e).setDirection(d);
			} else if (!(testSouth.isEmpty())) {
				d = Direction.SOUTH;
				((Caracter) e).setDirection(d);
			} else {
				throw new GameException("Il n'y a personne à attaquer");
			}
			((Caracter) e).classicAtk();

		}
	}

}
