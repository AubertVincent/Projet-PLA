package operateur;

public abstract class Behavior extends Operator {

	Action A;
	Action B;

	public Behavior(int x, int y, Action A, Action B) {
		super(x, y);
		this.A = A;
		this.B = B;
	}

}
