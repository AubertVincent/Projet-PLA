package entite;

public abstract class Entity {

	public abstract boolean isCharacter();

	public abstract boolean isOperator();

	private int x;
	private int y;

	public Entity(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

}
