package carte;

import entite.Entity;

public class Obstacle extends Entity {
	
	public Obstacle(int x, int y) {
		super(x, y);
	}

	@Override
	public boolean isCaracter() {
		return false;
	}

	@Override
	public boolean isOperator() {
		return false;
	}

	@Override
	public boolean isObstacle() {
		return true;
	}


}
