package operateur;

import sequence._Sequence;
import exceptions.*;

public abstract class Behavior {

	public abstract void execute(_Sequence left, _Sequence right) throws NotDoableException;

}