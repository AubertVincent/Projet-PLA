package personnages;

import entite.Direction;

public class Robot extends Character {

	public Robot(int x, int y, Direction direction, int life, int vision, int attack, int range, int movePoints,
			int recall, int aP) {
		super(x, y, direction, life, vision, attack, range, movePoints, recall, aP);
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


	@Override
	public boolean isObstacle() {
		// TODO Auto-generated method stub
		return false;
	}
}
