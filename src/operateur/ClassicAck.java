package operateur;

import carte.Cell;
import entite.Direction;
import entite.Entity;
import entite.GameException;
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
		Cell testEast = new Cell(x + 1, y);
		Cell testSouth = new Cell(x, y - 1);
		Cell testNorth = new Cell(x, y + 1);
		Cell testWest = new Cell(x - 1, y);
		if (!e.isCaracter()) {
			throw new GameException("Cette entité n'est pas un personnage");
		} else {
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

	@Override
	public boolean isObstacle() {
		// TODO Auto-generated method stub
		return false;
	}

}
