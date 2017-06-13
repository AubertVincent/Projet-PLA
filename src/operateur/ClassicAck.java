package operateur;

import carte.Cell;
import carte.Map;
import entite.Direction;
import entite.Team;
import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import pickable.PickClassicAck;

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
		if (r.getAttackPoints() <= 0) {
			return false;
		}
		int x = r.getX();
		int y = r.getY();
		boolean b;
		Team team = r.getTeam();
		Map myMap = r.getEntityMap();

		boolean isInCorner = ((x == 0 && y == 0) || (x == 0 && y == myMap.mapHeight() - 1)
				|| (x == myMap.mapWidth() - 1 && y == 0) || (x == myMap.mapWidth() - 1 && y == myMap.mapHeight() - 1));
		if (x == 0 && y == 0) {
			return myMap.getCell(x, y + 1).opponentHere(team) || myMap.getCell(x + 1, y).opponentHere(team);
		} else if (x == myMap.mapWidth() - 1 && y == 0) {

			return myMap.getCell(x, y + 1).opponentHere(team) || myMap.getCell(x - 1, y).opponentHere(team);

		} else if (x == myMap.mapWidth() - 1 && y == myMap.mapHeight() - 1) {
			return myMap.getCell(x, y - 1).opponentHere(team) || myMap.getCell(x - 1, y).opponentHere(team);

		} else if (x == 0 && y == myMap.mapHeight() - 1) {
			return myMap.getCell(x, y - 1).opponentHere(team) || myMap.getCell(x + 1, y).opponentHere(team);

		} else if (!isInCorner && x == 0) {
			return myMap.getCell(x, y - 1).opponentHere(team) || myMap.getCell(x, y + 1).opponentHere(team)
					|| myMap.getCell(x + 1, y).opponentHere(team);

		} else if (!isInCorner && y == 0) {
			return myMap.getCell(x + 1, y).opponentHere(team) || myMap.getCell(x, y + 1).opponentHere(team)
					|| myMap.getCell(x - 1, y).opponentHere(team);

		} else if (!isInCorner && y == myMap.mapHeight() - 1) {
			return myMap.getCell(x + 1, y).opponentHere(team) || myMap.getCell(x, y - 1).opponentHere(team)
					|| myMap.getCell(x - 1, y).opponentHere(team);

		} else if (!isInCorner && x == myMap.mapWidth() - 1) {
			return myMap.getCell(x, y - 1).opponentHere(team) || myMap.getCell(x, y + 1).opponentHere(team)
					|| myMap.getCell(x - 1, y).opponentHere(team);

		} else {
			Cell testEast = myMap.getCell(x + 1, y);
			Cell testSouth = myMap.getCell(x, y + 1);
			Cell testNorth = myMap.getCell(x, y - 1);
			Cell testWest = myMap.getCell(x - 1, y);
			b = !(!(testEast.opponentHere(team)) && !(testWest.opponentHere(team)) && !(testSouth.opponentHere(team))
					&& !(testNorth.opponentHere(team)));
			return b;
		}
	}

	@Override

	public void execute(Robot r) throws NotDoableException {
		// test
		// System.out.println("J'execute une attaque classique !");
		// end test
		if (!isDoable(r)) {
			throw new NotDoableException("Il n'y a personne à attaquer ou pas assez de point d'attaque");
		} else {

			int x = r.getX();
			int y = r.getY();
			Team team = r.getTeam();
			Map myMap = r.getEntityMap();

			Direction d;
			Cell testEast = myMap.getCell(x + 1, y);
			Cell testSouth = myMap.getCell(x, y + 1);
			Cell testNorth = myMap.getCell(x, y - 1);
			Cell testWest = myMap.getCell(x - 1, y);
			Cell target = null;
			boolean opponentNorth = testNorth.opponentHere(team);
			boolean opponentSouth = testSouth.opponentHere(team);
			boolean opponentEast = testEast.opponentHere(team);
			boolean opponentWest = testWest.opponentHere(team);
			if (opponentEast) {
				d = Direction.EAST;
				r.setDirection(d);
				target = testEast;
			} else if (opponentNorth) {
				d = Direction.NORTH;
				r.setDirection(d);
				target = testNorth;
			} else if (opponentWest) {
				d = Direction.WEST;
				r.setDirection(d);
				target = testWest;
			} else if (opponentSouth) {
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

	@Override
	public Class<? extends PickAble> getPickable() {
		return PickClassicAck.class;
	}

	@Override
	public String toString() {
		return "AC";
	}

}
