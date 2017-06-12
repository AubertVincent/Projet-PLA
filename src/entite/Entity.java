package entite;

import carte.Coordinates;
import carte.Map;

public abstract class Entity {

	protected Coordinates coord;
	private final Map entityMap;

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
	public Entity(Coordinates coord, Map entityMap) {
		super();
		this.coord = coord;
		this.entityMap = entityMap;
	}

	/**
	 * Set the coordinates of an Entity
	 * 
	 * @param coord
	 *            coordinates on the map
	 */
	public void setCoord(Coordinates coord) {
		this.coord = coord;
	}

	/**
	 * Get the x coordinate of an Entity
	 * 
	 * @return x coordinate on the map of the Entity
	 */
	public Coordinates getCoord() {
		return this.coord;
	}

	/**
	 * The map on which the entity is located
	 * 
	 * @return the map
	 */
	public Map getEntityMap() {
		return entityMap;
	}

}
