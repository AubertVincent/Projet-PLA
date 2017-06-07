package operateur;

import carte.Cellule;
import entite.*;
import personnages.Character;

public class ClassicAck extends Attack {

	public ClassicAck(int x, int y) {
		super(x, y);
	}

	public void execute(Character attacker, Character opponent) {
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
		// TODO il faudrait la position de l'opponent ? ça veut dire quoi une
		// attaque classique est faisable ?
		// Ou vérifier que la personne qu'on ne va pas se faire tuer en
		// attaquant la personne?
		return false;
	}

	@Override
	public void execute(Entity e) throws GameException {

		if (!isDoable()) {
			throw new GameException("Cette action n'est pas réalisable");
		}
		if (!e.isCharacter()) {
			throw new GameException("Cette entité n'est pas un personnage");
		} else {
			int x = e.getX();
			int y = e.getY();
			Direction d;
			Cellule testEast = new Cellule(x + 1, y);
			Cellule testSouth = new Cellule(x, y - 1);
			Cellule testNorth = new Cellule(x, y + 1);
			Cellule testWest = new Cellule(x - 1, y);
			if (!(testEast.isEmpty())) {
				d = Direction.EAST;
				((Character) e).setDirection(d);
			} else if (!(testNorth.isEmpty())) {
				d = Direction.NORTH;
				((Character) e).setDirection(d);
			} else if (!(testWest.isEmpty())) {
				d = Direction.WEST;
				((Character) e).setDirection(d);
			} else if (!(testSouth.isEmpty())) {
				d = Direction.SOUTH;
				((Character) e).setDirection(d);
			} else {
				throw new GameException("Il n'y a personne à attaquer");
			}
			((Character) e).classicAtk();

		}
	}
	
	

}
