package carte;

import entite.Team;

public class Base {

	// Base coordinate
	private Coordinates coord;
	private Team baseTeam;

	public Base(Coordinates coord, Team baseTeam) {
		super();
		this.coord = coord;
		this.baseTeam = baseTeam;
	}

	public Coordinates getCoord() {
		return this.coord;
	}

	public Team getBaseTeam() {
		return baseTeam;
	}

	public void setBaseTeam(Team baseTeam) {
		this.baseTeam = baseTeam;
	}

	public void setCoord(Coordinates coord) {
		this.coord = coord;
	}

}
