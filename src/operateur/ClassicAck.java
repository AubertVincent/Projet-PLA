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
		int player = ((Character) e).getPlayer();
		Cell testEast = new Cell(x + 1, y);
		Cell testSouth = new Cell(x, y - 1);
		Cell testNorth = new Cell(x, y + 1);
		Cell testWest = new Cell(x - 1, y);

		if (!(testEast.opponentHere(player)) && !(testWest.opponentHere(player)) && !(testSouth.opponentHere(player))
				&& !(testNorth.opponentHere(player))) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	protected void execute(Entity e) throws GameException {
		
		if (!e.isCharacter()) { //should be impossible
			throw new GameException("Cette entité n'est pas un personnage");
		}
		if (!isDoable(e)) {
			throw new GameException("Il n'y a personne à attacker");
		} else {

			int x = e.getX();
			int y = e.getY();
			int player = ((Character) e).getPlayer();
			Map myMap = e.getEntityMap();
			Direction d;
			Cell testEast = myMap.getCell(x + 1, y);
			Cell testSouth = myMap.getCell(x, y - 1);
			Cell testNorth = myMap.getCell(x, y + 1);
			Cell testWest = myMap.getCell(x - 1, y);
			Cell target = null;
			if (testEast.opponentHere(player)) {
				d = Direction.EAST;
				((Character) e).setDirection(d);
				target = testEast;
			} else if (testNorth.opponentHere(player)) {
				d = Direction.NORTH;
				((Character) e).setDirection(d);
				target = testNorth;
			} else if (testWest.opponentHere(player)) {
				d = Direction.WEST;
				((Character) e).setDirection(d);
				target = testWest;
			} else if (testSouth.opponentHere(player)) {
				d = Direction.SOUTH;
				((Character) e).setDirection(d);
				target = testSouth;
			}
			((Character) e).classicAtk(target);

		}
	}

}
