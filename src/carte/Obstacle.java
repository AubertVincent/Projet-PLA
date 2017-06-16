package carte;

import entite.Entity;

public class Obstacle extends Entity {

	/**
	 * Used to create a new Obstacle
	 * 
	 * @param x
	 *            the x coordinate of the Obstacle
	 * @param y
	 *            the y coordinate of the Obstacle
	 * @param entityMap
	 *            the map used to set the Obstacle
	 */
	public Obstacle(int x, int y, Map map) {
		super(x, y, map);
	}

	/**
	 * used to get if the obstacle is an obstacle
	 */
	@Override
	public boolean isObstacle() {
		return true;
	}

	/**
	 * Used to get if the obstacle is a character
	 */
	@Override
	public boolean isCharacter() {
		return false;
	}

	/**
	 * Used to get if the obstacle is a pickable
	 */
	@Override
	public boolean isPickAble() {
		return false;
	}

}
