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

}
