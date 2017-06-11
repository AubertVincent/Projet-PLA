package util;

public enum Correct {
	CORRECT, INCORRECT;

	private Correct() {
	}

	@Override
	public String toString() {
		return this.name();
	}
}
