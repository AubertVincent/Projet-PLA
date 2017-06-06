package personnages;

import java.util.LinkedList;
import java.util.List;

import entite.Direction;
import operateur.*;

public class Robot extends Character {

	protected static List<Action> possibleActionsList = new LinkedList<Action>();

	public Robot(int x, int y, Direction direction, int life, int vision, int attack, int range, int movePoints,
			int recall) {
		super(x, y, direction, life, vision, attack, range, movePoints, recall);
		possibleActionsList.add(new ClassicAck());
		possibleActionsList.add(new MoveDir());
		possibleActionsList.add(new Tunnel());
		possibleActionsList.add(new Recall());
		possibleActionsList.add(new SuicideBomber());
	}

	public static List<Action> getPossibleActionsList() {
		return possibleActionsList;
	}

	@Override
	public boolean isPlayer() {
		return false;
	}

	@Override
	public boolean isRobot() {
		return true;
	}

	public void suicideBomber() {
		// TODO
	}
}
