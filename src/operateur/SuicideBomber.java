package operateur;

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

		if (x < r.getEntityMap().mapWidth() && m.getCell(x + 1, y).opponentHere(r.getTeam())) {
			return true;
		}
		if (x > 0 && m.getCell(x - 1, y).opponentHere(r.getTeam())) {
			return true;
		}
		if (y > 0 && m.getCell(x, y - 1).opponentHere(r.getTeam())) {
			return true;
		}
		if (y < r.getEntityMap().mapHeight() && m.getCell(x, y + 1).opponentHere(r.getTeam())) {
			return true;
		}
		return false;
	}

	@Override
	public void execute(Robot r) throws NotDoableException {
		// test
		// System.out.println("J'execute suicide Bomber !");
		// end test
		if (!isDoable(r)) {
			throw new NotDoableException("Il n'y a personne à tuer");
		} else {
			r.suicideBomber();
			this.lastLife = r.getLife();
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
