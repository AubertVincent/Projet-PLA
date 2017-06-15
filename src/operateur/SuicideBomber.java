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

	// ↓ Miscellaneous methods ↓

	@Override
	public boolean isDoable(Robot r) {

		// We have to check if there is at least one opponent around this robot
		// (North, South, Est, West)
		int x = r.getX();
		int y = r.getY();

		Map myMap = r.getMap();
		if (x + 1 < r.getMap().mapWidth() && myMap.getCell(x + 1, y).robotHere()
				&& myMap.getCell(x + 1, y).opponentHere(r.getTeam())) {
			return true;
		}
		if (x - 1 >= 0 && myMap.getCell(x - 1, y).robotHere() && myMap.getCell(x - 1, y).opponentHere(r.getTeam())) {
			return true;
		}
		if (y - 1 >= 0 && myMap.getCell(x, y - 1).robotHere() && myMap.getCell(x, y - 1).opponentHere(r.getTeam())) {
			return true;
		}
		if (y + 1 < r.getMap().mapHeight() && myMap.getCell(x, y + 1).robotHere()
				&& myMap.getCell(x, y + 1).opponentHere(r.getTeam())) {

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
			throw new NotDoableException("There is anybody to kill");
		} else {
			// The list in which the targets will be put
			List<Cell> targets = new ArrayList<Cell>();
			int x = r.getX();
			int y = r.getY();
			Map myMap = r.getMap();
			// if there is a robot on the cell, the cell is put in the list
			if (x + 1 < r.getMap().mapWidth() && myMap.getCell(x + 1, y).robotHere()) {
				targets.add(myMap.getCell(x + 1, y));
			}
			if (x - 1 >= 0 && myMap.getCell(x - 1, y).robotHere()) {
				targets.add(myMap.getCell(x - 1, y));
			}
			if (y - 1 >= 0 && myMap.getCell(x, y - 1).robotHere()) {
				targets.add(myMap.getCell(x, y - 1));
			}
			if (y + 1 < r.getMap().mapHeight() && myMap.getCell(x, y + 1).robotHere()) {

				targets.add(myMap.getCell(x, y + 1));
			}
			r.suicideBomber(targets);
			// implemented in order to cancel
			// this.lastLife = r.getLife();
		}
	}

	@Override
	public Class<? extends PickAble> getPickable() {
		return PickSuicideBomber.class;
	}

	@Override
	public String toString() {
		return " AS ";
	}

	// End(Miscellaneous methods)

}
