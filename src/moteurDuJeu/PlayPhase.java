package moteurDuJeu;

public enum PlayPhase {

	playerMovement(0), behaviorModification(1), automatonExecution(2), endOfGame(3);

	private final int value;

	private PlayPhase(int val) {
		value = val;
	}

	public int toInt() {
		return value;
	}

	public static String toString(PlayPhase phase) {
		switch (phase) {
		case playerMovement:
			return "Phase de mouvement des joueurs";
		case behaviorModification:
			return "Phase de Modification et création";
		case automatonExecution:
			return "Phase de mouvement des unités";
		case endOfGame:
			return "Fin du Jeu";
		}
		return null;
	}

}
