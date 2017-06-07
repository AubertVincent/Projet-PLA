package operateur;

import carte.*;
import entite.*;
import personnages.Character;

public class ClassicAck extends Attack {

	protected Character opponent;
	protected Character attacker;

	/**
	 * Set a new classicAck by leans of its attacker and its opponent
	 * 
	 * @param attacker
	 *            the initiator of the attack
	 * @param opponent
	 *            the target
	 */
	public ClassicAck(Character attacker, Character opponent) {
		super();
		this.opponent = opponent;
		this.attacker = attacker;
	}

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
			Direction d;
			Cell testEast = Map.getCell(x + 1, y);
			Cell testSouth = Map.getCell(x, y - 1);
			Cell testNorth = Map.getCell(x, y + 1);
			Cell testWest = Map.getCell(x - 1, y);
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
