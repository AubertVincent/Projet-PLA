package personnages;

import entite.Direction;

public class Robot extends Caracter {

	public Robot(int x, int y, Direction direction, int life, int vision, int attack, int range, int movePoints,
			int recall) {
		super(x, y, direction, life, vision, attack, range, movePoints, recall);
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
