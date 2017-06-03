package entite;

public enum Direction {

	NORD(0), OUEST(1), SUD(2), EST(3);
	
	private final int value ;
	
	private Direction(int val){
		value = val ;
	}
	
	public int toInt(){
		return value ;
	}
	
}
