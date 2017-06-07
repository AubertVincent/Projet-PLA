package entite;

public enum Direction {

	NORTH(0), WEST(1), SOUTH(2), EAST(3);
	
	private final int value ;
	
	private Direction(int val){
		value = val ;
	}
	
	public int toInt(){
		return value ;
	}
	
}
