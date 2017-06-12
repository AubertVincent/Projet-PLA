package carte;

import entite.Entity;

public class Obstacle extends Entity {

	public Obstacle(Coordinates coordinates, Map map) {
		super(coordinates, map);
	}

	@Override
	public boolean isObstacle() {
		return true;
	}

	@Override
	public boolean isCharacter() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPickAble() {
		// TODO Auto-generated method stub
		return false;
	}

}
