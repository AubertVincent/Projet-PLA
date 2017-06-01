package operateur;

public abstract class Operateur extends entite.Entite {
	
	public int disponibilite;
	
	public boolean isPickable() {
		return true;
	}
	public static Operateur randomOp(){
		return (new DeplDir());
	}
}
