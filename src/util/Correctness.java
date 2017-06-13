package util;

public enum Correctness {
	CORRECT, INCORRECT;

	private Correctness() {
	}

	@Override
	public String toString() {
		return this.name();
	}
}
