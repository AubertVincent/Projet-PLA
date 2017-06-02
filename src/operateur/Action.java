package operateur;
import entite.Entite;
import entite.GameException;

public abstract class Action extends Operateur {
	
	
	public abstract void execute(Entite e) throws GameException;
	public abstract boolean isDoable();
}
