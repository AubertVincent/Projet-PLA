package sequence;

public class Nil implements _Sequence {

	public static final Nil Nil = new Nil();

	private Nil() {
	}

	@Override
	public boolean isAction() {
		return false;
	}

	@Override
	public boolean isTree() {
		return false;
	}

}
