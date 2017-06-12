package carte;

import entite.Team;

public class Base {

	// Base coordinate
	private Coordinates coordinates;
	private Team baseTeam;
	private static final Coordinates coordinatesBleu = new Coordinates(31, 15);
	private static final Coordinates coordinatesRouge = new Coordinates(2, 4);

	public Base(Team team) {
		super();
		switch (team) {
		case BLEU:
			this.setCoordinates(coordinatesBleu);
			break;
		case ROUGE:
			this.setCoordinates(coordinatesRouge);
			break;
		}
		this.baseTeam = team;
	}

	public Coordinates getCoordinates() {
		return this.coordinates;
	}

	public Team getBaseTeam() {
		return baseTeam;
	}

	public void setBaseTeam(Team baseTeam) {
		this.baseTeam = baseTeam;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

}
