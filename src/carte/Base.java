package carte;

import entite.Team;

public class Base {

	// Base coordinate
	private Coordinates coord;
	private Team baseTeam;
	private static final Coordinates coordBleu = new Coordinates(31, 15);
	private static final Coordinates coordRouge = new Coordinates(2, 4);

	public Base(Team team) {
		super();
		switch (team) {
		case BLEU:
			this.setCoord(coordBleu);
			break;
		case ROUGE:
			this.setCoord(coordRouge);
			break;
		}
		this.baseTeam = team;
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
