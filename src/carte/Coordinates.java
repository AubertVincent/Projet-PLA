package carte;

public class Coordinates {

	public boolean equals(Coordinates coord) {
		return coord.getX() == x && coord.getY() == y;
	}

	private int x;
	private int y;

	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Coordinates(Coordinates coord) {
		this.x = coord.getX();
		this.y = coord.getY();
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

	public Coordinates getCoordinates() {
		return this;
	}

	public void setCoordinates(int x, int y) {
		setX(x);
		setY(y);
	}
}
