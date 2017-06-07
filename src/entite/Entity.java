package entite;

public abstract class Entity {

	private int x;
	private int y;

	public abstract boolean isCharacter();

	/**
	 * Set a new Entity on the map by means of its coordinates
	 * @param x x coordinate on the map
	 * @param y y coordinate on the map
	 */
	public Entity(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Set the x coordinate of an Entity
	 * @param x x coordinate on the map
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Set the y coordinate of an Entity
	 * @param y y coordinate on the map
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Get the x coordinate of an Entity
	 * @return x coordinate on the map of the Entity
	 */
	public int getX() {
		return this.x;
	}

	
	/**
	 * Get the y coordinate of an Entity
	 * @return y coordinate on the map of the Entity
	 */
	public int getY() {
		return this.y;
	}

}
