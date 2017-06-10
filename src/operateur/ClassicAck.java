package operateur;

import carte.Cell;
import carte.Map;
import entite.Direction;
import entite.Team;
import exceptions.NotDoableException;
import personnages.Robot;

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
	protected boolean isDoable(Robot r) {

		int x = r.getX();
		int y = r.getY();
		Team team = r.getTeam();

		Cell testEast = new Cell(x + 1, y);
		Cell testSouth = new Cell(x, y - 1);
		Cell testNorth = new Cell(x, y + 1);
		Cell testWest = new Cell(x - 1, y);

		return !(testEast.opponentHere(team)) && !(testWest.opponentHere(team)) && !(testSouth.opponentHere(team))
				&& !(testNorth.opponentHere(team));
	}

	@Override

	public void execute(Robot r) throws NotDoableException {
		if (!isDoable(r)) {
			throw new NotDoableException("Il n'y a personne à attaquer");
		} else {

			int x = r.getX();
			int y = r.getY();
			Team team = r.getTeam();
			Map myMap = r.getEntityMap();

			Direction d;
			Cell testEast = myMap.getCell(x + 1, y);
			Cell testSouth = myMap.getCell(x, y - 1);
			Cell testNorth = myMap.getCell(x, y + 1);
			Cell testWest = myMap.getCell(x - 1, y);
			Cell target = null;
			if (testEast.opponentHere(team)) {
				d = Direction.EAST;
				r.setDirection(d);
				target = testEast;
			} else if (testNorth.opponentHere(team)) {
				d = Direction.NORTH;
				r.setDirection(d);
				target = testNorth;
			} else if (testWest.opponentHere(team)) {
				d = Direction.WEST;
				r.setDirection(d);
				target = testWest;
			} else if (testSouth.opponentHere(team)) {
				d = Direction.SOUTH;
				r.setDirection(d);
				target = testSouth;
			}
			r.classicAtk(target);

		}
	}

	@Override
	public void cancel(Robot r) throws NotDoableException {
		if (!isDoable(r)) {
			throw new NotDoableException("Il n'y a personne à attaquer");
		} else {

			int x = r.getX();
			int y = r.getY();
			Team team = r.getTeam();
			Map myMap = r.getEntityMap();

			Direction d;
			Cell testEast = myMap.getCell(x + 1, y);
			Cell testSouth = myMap.getCell(x, y - 1);
			Cell testNorth = myMap.getCell(x, y + 1);
			Cell testWest = myMap.getCell(x - 1, y);
			Cell target = null;
			if (testEast.opponentHere(team)) {
				d = Direction.EAST;
				r.setDirection(d);
				target = testEast;
			} else if (testNorth.opponentHere(team)) {
				d = Direction.NORTH;
				r.setDirection(d);
				target = testNorth;
			} else if (testWest.opponentHere(team)) {
				d = Direction.WEST;
				r.setDirection(d);
				target = testWest;
			} else if (testSouth.opponentHere(team)) {
				d = Direction.SOUTH;
				r.setDirection(d);
				target = testSouth;
			}
			r.cancelClassicAtk(target);

		}

	}
}
