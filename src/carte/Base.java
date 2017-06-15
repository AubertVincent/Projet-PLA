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

	/**
	 * Created a new base, based on the player team
	 * 
	 * @param team
	 *            the team of the current player
	 */
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

	/**
	 * Used to get the base's X coordinate
	 * 
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * Used to get the base's Y coordinate
	 * 
	 * @return
	 */
	public int getY() {
		return y;
	}

	/**
	 * Used get the base's team
	 * 
	 * @return
	 */
	public Team getBaseTeam() {
		return baseTeam;
	}
}
