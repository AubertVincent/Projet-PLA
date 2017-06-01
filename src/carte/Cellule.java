package carte;
import entite.Entite;

public class Cellule {
	protected int x;
	protected int y;
	protected Entite entite;
	
	public Cellule(int x, int y){
		this.x = x;
		this.y = y;
		this.entite = new Entite();
	}
	
	public Cellule(int x, int y, Entite ent){
		this.x = x;
		this.y = y;
		this.entite = ent;
	}
	
	//@Override 
	public boolean isEmpty(){
		return entite.isEmpty();
	}
	
	public void setEntite(Entite ent){
		this.entite = ent;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
}
