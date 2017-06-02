package operateur;

public abstract class Operateur extends entite.Entite {

	public int disponibilite;

	public boolean isPickable() {
		return true;
	}

	public boolean isPersonnage() {
		return false;
	}

	public boolean isOperateur() {
		return true;
	}

	public boolean isJoueur() {
		return false;
	}

	public boolean isRobot() {
		return false;
	}

	public static Operateur randomOp() {
		return (new DeplDir());
	}
}
