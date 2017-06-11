package operateur;

import carte.Cell;
import carte.Map;
import exceptions.NotDoableException;
import personnages.Robot;
import pickable.PickAble;
import pickable.PickSuicideBomber;

public class SuicideBomber extends Attack {

	private int lastLife; // Life of the robot before it suicides

	@Override
	public boolean isDoable(Robot r) {

		// We have to check if there is at least one opponent around this robot
		// (North, South, Est, West)
		int x = r.getX();
		int y = r.getY();
		Map m = r.getEntityMap();
		Cell cellule = m.getCell(x + 1, y);
		if (cellule.opponentHere(r.getTeam())) {
			return true;
		}
		cellule = m.getCell(x - 1, y);
		if (cellule.opponentHere(r.getTeam())) {
			return true;
		}
		cellule = m.getCell(x, y - 1);
		if (cellule.opponentHere(r.getTeam())) {
			return true;
		}
		cellule = m.getCell(x, y - 1);
		if (cellule.opponentHere(r.getTeam())) {
			return true;
		}
		return false;
	}

	@Override
	public void execute(Robot r) throws NotDoableException {
		if (!isDoable(r)) {
			throw new NotDoableException("Il n'y a personne à tuer");
		} else {
			r.suicideBomber();
			this.lastLife = r.getLife();
			r.setLife(0);
		}
	}

	@Override
	public void cancel(Robot r) throws NotDoableException {
		if (!isDoable(r)) {
			throw new NotDoableException("Il n'y a personne à ressusciter");
		} else {
			r.setLife(this.lastLife);
			r.cancelSuicideBomber();
		}
	}

	@Override
	public Class<? extends PickAble> getPickable() {
		return PickSuicideBomber.class;
	}

	@Override
	public String toString() {
		return "AS";
	}


}
