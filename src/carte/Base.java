package carte;

import entite.Team;

public class Base {

	// Base coordinate
	private int x;
	private int y;
	private Team baseTeam;
	private static final int xBleu = 30;
	private static final int yBleu = 8;
	private static final int xRouge = 2;
	private static final int yRouge = 4;

	public Base(Team team) {
		super();
		switch (team) {
		case BLEU:
			this.x = xBleu;
			this.y = yBleu;
			break;
		case ROUGE:
			this.x = xRouge;
			this.y = yRouge;
			break;
		}
		this.baseTeam = team;
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
