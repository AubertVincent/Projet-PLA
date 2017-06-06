package operateur;

public abstract class Behavior extends Operator {

	protected Action A;
	protected Action B;

	public Behavior(Action A, Action B) {
		super();
		this.A = A;
		this.B = B;
	}

}
