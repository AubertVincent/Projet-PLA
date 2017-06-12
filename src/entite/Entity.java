package entite;

import carte.Coordinates;
import carte.Map;

public abstract class Entity {

	protected Coordinates coordinates;
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
	public Entity(Coordinates coordinates, Map entityMap) {
		super();
		this.coordinates = coordinates;
		this.entityMap = entityMap;
	}

	/**
	 * Set the coordinates of an Entity
	 * 
	 * @param coordinates
	 *            coordinates on the map
	 */
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * Get the x coordinate of an Entity
	 * 
	 * @return x coordinate on the map of the Entity
	 */
	public Coordinates getCoordinates() {
		return this.coordinates;
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
