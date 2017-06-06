package operateur;

import carte.*;
import entite.*;
import personnages.Character;

public class ClassicAck extends Attack {

	public ClassicAck(Character opponent, Character attacker) {
		super(opponent, attacker);
	}

	public ClassicAck() {
		super();
	}

	@Override
	protected boolean isDoable(Entity e) { // Care, here an obstacle is
											// attackable
		int x = e.getX();
		int y = e.getY();
		Cell testEast = new Cell(x + 1, y);
		Cell testSouth = new Cell(x, y - 1);
		Cell testNorth = new Cell(x, y + 1);
		Cell testWest = new Cell(x - 1, y);
		if (testEast.isFree() && testSouth.isFree() && testNorth.isFree() && testWest.isFree()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	protected void execute(Entity e) throws GameException {

		if (!isDoable(e)) {
			throw new GameException("Il n'y a personne à attacker");
		}
		if (!e.isCharacter()) {
			throw new GameException("Cette entité n'est pas un personnage");
		} else {
			int x = e.getX();
			int y = e.getY();
			Direction d;
			Cell testEast = new Cell(x + 1, y);
			Cell testSouth = new Cell(x, y - 1);
			Cell testNorth = new Cell(x, y + 1);
			Cell testWest = new Cell(x - 1, y);
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
			}
			((Character) e).classicAtk(attacker, opponent);

		}
	}

}
