package operateur;

import carte.Cell;
import carte.Map;
import exceptions.NotDoableException;
import personnages.Robot;

public class SuicideBomber extends Attack {

	@Override
	public boolean isDoable(Robot r) {
		// int x = r.getX();
		// int y = r.getY();
		//
		// List<Entity> testNorth = r.entityMap.getListEntity(x, y - 1);
		// List<Entity> testSouth = r.entityMap.getListEntity(x, y + 1);
		// List<Entity> testWest = r.entityMap.getListEntity(x - 1, y);
		// List<Entity> testEast = r.entityMap.getListEntity(x + 1, y);
		// Cell testN = r.entityMap.getCell(x, y-1);
		// testN.opponentHere(r.getPlayer());

		int compteur;
		int x = r.getX();
		int y = r.getY();
		Map m = r.getEntityMap();
		Cell cellule = m.getCell(x + 1, y);
		// We have to check if there is at least one opponent around this robot
		// (North, South, Est, West)
		if (cellule.getOpponent(r.getTeam()).getTeam()==r.getTeam()) {
			return true;
		}
		cellule = m.getCell(x - 1, y);
		if (cellule.getOpponent(r.getTeam())) {
			return true;
		}
		cellule = m.getCell(x, y - 1);
		if (cellule.getOpponent(r.getTeam())) {
			return true;
		}
		cellule = m.getCell(x, y - 1);
		if (cellule.getOpponent(r.getTeam())) {
			return true;
		}
		return false;
	}

	@Override
	public void execute(Robot r) throws NotDoableException {
		if (!isDoable(r)) {
			throw new NotDoableException("Cette entité n'est pas un robot");
		} else {
			r.suicideBomber();
			r.setLife(0);
		}

	}

	@Override
	public void cancel(Robot r) throws NotDoableException {
		// TODO Auto-generated method stub

	}

}
