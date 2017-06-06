package operateur;

public abstract class Behavior extends Operator {

	protected Action A;
	protected Action B;

	/**
	 * Set a new Behavior by means of its 2 actions
	 * @param A First action
	 * @param B Second action
	 */
	public Behavior(Action A, Action B) {
		super();
		this.A = A;
		this.B = B;
	}
	
	
	
}
