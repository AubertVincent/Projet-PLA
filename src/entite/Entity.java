package entite;

import carte.Map;


public abstract class Entity {


	protected int x;
	protected int y;
	protected final Map entityMap;


	public abstract boolean isObstacle();

	public abstract boolean isCharacter();


	public abstract boolean isPickAble();

	/**
	 * 
	 * Set a new Entity on the map by means of its coordinates
	 * 
	 * @param x
	 *            x coordinate on the map
	 * @param y
	 *            y coordinate on the map
	 * @param entityMap
	 *            The map on which the entity is located
	 */
	public Entity(int x, int y, Map entityMap) {
		super();
		this.x = x;
		this.y = y;
		this.entityMap = entityMap;
	}

	/**
	 * Set the x coordinate of an Entity
	 * 
	 * @param x
	 *            x coordinate on the map
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Set the y coordinate of an Entity
	 * 
	 * @param y
	 *            y coordinate on the map
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Get the x coordinate of an Entity
	 * 
	 * @return x coordinate on the map of the Entity
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Get the y coordinate of an Entity
	 * 
	 * @return y coordinate on the map of the Entity
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * The map on which the entity is located
	 * @return the map
	 */
	public Map getEntityMap() {
		return entityMap;
	}

}
