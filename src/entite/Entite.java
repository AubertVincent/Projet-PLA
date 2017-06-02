package entite;

import entite.Entite.Direction;

public abstract class Entite {

	public abstract boolean isPersonnage();

	public abstract boolean isOperateur();

	public abstract boolean isJoueur();

	public abstract boolean isRobot();

	public abstract boolean isPickable();

	public enum Direction {
		NORD, SUD, EST, OUEST;
	}

	private int x;
	private int y;
	private Direction d;

	public Entite(int x, int y, Direction d) {
		super();
		this.x = x;
		this.y = y;
		this.d = d;
	}

	public void Position(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	public void setD(Direction d) {
		this.d = d;
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

	public Direction getD() {
		return this.d;
	}

	public void allerVers(Direction dir, int lg) {

		setD(dir);

		switch (dir) {
		case NORD:
			y = y + lg;
			setY(y);
			break;
		case SUD:
			y = y - lg;
			setY(y);
			break;
		case EST:
			x = x + lg;
			setX(x);
			break;
		case OUEST:
			x = x - lg;
			setX(x);
			break;
		}
	}

}
