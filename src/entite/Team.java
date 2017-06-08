package entite;

public enum Team {
	ROUGE(0), BLEU(1);

	private final int value;

	private Team(int val) {
		value = val;
	}

	public int toInt() {
		return value;
	}
}
