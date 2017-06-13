package operateur;

import java.util.ArrayList;
import java.util.List;

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
		Map myMap = r.getEntityMap();

		if (x < r.getEntityMap().mapWidth() && myMap.getCell(x + 1, y).opponentHere(r.getTeam())) {
			return true;
		}
		if (x > 0 && myMap.getCell(x - 1, y).opponentHere(r.getTeam())) {
			return true;
		}
		if (y > 0 && myMap.getCell(x, y - 1).opponentHere(r.getTeam())) {
			return true;
		}
		if (y < r.getEntityMap().mapHeight() && myMap.getCell(x, y + 1).opponentHere(r.getTeam())) {
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
			List<Cell> targets = new ArrayList<Cell>();
			int x = r.getX();
			int y = r.getY();
			Map myMap = r.getEntityMap();

			if (x < r.getEntityMap().mapWidth() && myMap.getCell(x + 1, y).opponentHere(r.getTeam())) {
				targets.add(myMap.getCell(x + 1, y));
			}
			if (x > 0 && myMap.getCell(x - 1, y).opponentHere(r.getTeam())) {
				targets.add(myMap.getCell(x - 1, y));
			}
			if (y > 0 && myMap.getCell(x, y - 1).opponentHere(r.getTeam())) {
				targets.add(myMap.getCell(x, y - 1));
			}
			if (y < r.getEntityMap().mapHeight() && myMap.getCell(x, y + 1).opponentHere(r.getTeam())) {
				targets.add(myMap.getCell(x, y + 1));
			}

			r.suicideBomber(targets);
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
