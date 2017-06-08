package operateur;

import sequence._Sequence;
import exceptions.*;
import personnages.Robot;

public abstract class Behavior {

	public abstract void execute(Robot r,_Sequence left, _Sequence right) throws NotDoableException;

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	
	
}