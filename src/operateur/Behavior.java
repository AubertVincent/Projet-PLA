package operateur;

import exceptions.NotDoableException;
import personnages.Robot;
import sequence._Sequence;

public abstract class Behavior {

	public abstract void execute(Robot r, _Sequence left, _Sequence right) throws NotDoableException;

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

}