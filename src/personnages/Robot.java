package personnages;

import java.util.LinkedList;
import java.util.List;

import entite.Direction;
import operateur.*;

public class Robot extends Character {

	protected static List<Class <? extends Action>> possibleActionsList = new LinkedList<Class <? extends Action>>();

	public Robot(int x, int y, Direction direction, int life, int vision, int attack, int range, int movePoints,
			int recall) {
		super(x, y, direction, life, vision, attack, range, movePoints, recall);
		possibleActionsList.add(ClassicAck.class);
		possibleActionsList.add(MoveDir.class);
		possibleActionsList.add(Tunnel.class);
		possibleActionsList.add(Recall.class);
		possibleActionsList.add(SuicideBomber.class);
	}

	public static List<Class <? extends Action>> getPossibleActionsList() {
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
