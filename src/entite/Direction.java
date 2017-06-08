package entite;

public enum Direction {

	NORTH(0), WEST(1), SOUTH(2), EAST(3);

	private final int value;

	private Direction(int val) {
		value = val;
	}

	public int toInt() {
		return value;
	}

//	@Override
//	public String toString() {
//		switch (value) {
//		case 0:
//			return "NORTH";
//		case 1:
//			return "WEST";
//		case 2:
//			return "SOUTH";
//		case 3:
//			return "EAST";
//		default:
//			throw new IllegalArgumentException();
//		}
//	}
}
