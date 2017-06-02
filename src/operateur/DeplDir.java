package operateur;

import entite.Entite;
import entite.GameException;

public class DeplDir extends Mouvement {

	Direction dir;
	int lg;

	
	public DeplDir(Direction dir, int lg) {
		super();
		this.dir = dir;
		this.lg = lg;
	}

	@Override
	public boolean isDoable() {
		// TODO
		return false;			
	}
	


	public void execute(Entite e) throws GameException {

		if (!isDoable()) {
			throw new GameException("Cette action n'est pas réalisable");
		}

		e.allerVers(dir, lg);



	}

}
