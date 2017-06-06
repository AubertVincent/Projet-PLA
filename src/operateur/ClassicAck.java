package operateur;

import carte.Cellule;
import entite.*;
import personnages.Character;

public class ClassicAck extends Attack {

	@Override
	protected boolean isDoable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void execute(Entity e) throws GameException {

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
			((Character) e).classicAtk(attacker, opponent);

		}
	}



}
