package personnages;

import java.util.LinkedList;
import java.util.List;

import entite.Direction;
import operateur.*;

public class Player extends Character {

	protected static List<Action> possibleActionsList = new LinkedList<Action>();
	
	public Player(int x, int y, Direction direction, int life, int vision, int attack, int range, int movePoints,
			int recall) {
		super(x, y, direction, life, vision, attack, range, movePoints, recall);
		possibleActionsList.add(new ClassicAck());
		possibleActionsList.add(new MoveDir());
		possibleActionsList.add(new Tunnel());
		possibleActionsList.add(new Recall());
	}
	

	

	
	public static List<Action> getPossibleActionsList() {
		return possibleActionsList;
	}
	

	@Override
	public boolean isPlayer() {
		return true;
	}

	@Override
	public boolean isRobot() {
		return false;
	}

	// TODO Keyboard reaction

}
