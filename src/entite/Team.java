package entite;

public enum Team {
	ROUGE(0), BLEU(1);

	private final int value;

	private Team(int value) {
		this.value = value;
	}

	public int toInt() {
		return value;
	}
}
