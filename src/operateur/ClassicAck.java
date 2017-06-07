package operateur;

import carte.*;
import entite.*;
import personnages.Character;

public class ClassicAck extends Attack {

	/**
	 * Set a new classicAck by leans of its attacker and its opponent
	 * 
	 * @param attacker
	 *            the initiator of the attack
	 * @param opponent
	 *            the target
	 */

	public ClassicAck() {
		super();
	}

	/**
	 * A classicAck is doable if there is an opponent entity
	 */
	@Override
	protected boolean isDoable(Entity e) { // Care, here an obstacle is
											// attackable
		int x = e.getX();
		int y = e.getY();
		Cell testEast = new Cell(x + 1, y);
		Cell testSouth = new Cell(x, y - 1);
		Cell testNorth = new Cell(x, y + 1);
		Cell testWest = new Cell(x - 1, y);
		// TODO not isFree but !isOpponentEntity
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
			Map myMap = e.getEntityMap();
			Direction d;
			Cell testEast = myMap.getCell(x + 1, y);
			Cell testSouth = myMap.getCell(x, y - 1);
			Cell testNorth = myMap.getCell(x, y + 1);
			Cell testWest = myMap.getCell(x - 1, y);
			Cell target = null;
			if (!(testEast.isFree())) {
				d = Direction.EAST;
				((Character) e).setDirection(d);
				target = testEast;
			} else if (!(testNorth.isFree())) {
				d = Direction.NORTH;
				((Character) e).setDirection(d);
				target = testNorth;
			} else if (!(testWest.isFree())) {
				d = Direction.WEST;
				((Character) e).setDirection(d);
				target = testWest;
			} else if (!(testSouth.isFree())) {
				d = Direction.SOUTH;
				((Character) e).setDirection(d);
				target = testSouth;
			}
			((Character) e).classicAtk(target);

		}
	}

}
