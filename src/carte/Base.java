package carte;

import entite.Team;

public class Base {

	// Base coordinate
	private int x;
	private int y;
	private Team baseTeam;

	public Base(int x, int y, Team baseTeam) {
		super();
		this.x = x;
		this.y = y;
		this.baseTeam = baseTeam;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Team getBaseTeam() {
		return baseTeam;
	}

	public void setBaseTeam(Team baseTeam) {
		this.baseTeam = baseTeam;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

}
