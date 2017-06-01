package carte;
import java.util.ArrayList;
import java.util.List;

import entite.Entite;

public class Cellule {
	protected int x;
	protected int y;
	protected List<Entite> listeEntites ;
	
	public Cellule(int x, int y){
		this.x = x;
		this.y = y;
		this.listeEntites = new ArrayList<Entite>();
	}
	
	public Cellule(int x, int y, List<Entite> listeEnt){
		this.x = x;
		this.y = y;
		this.listeEntites = listeEnt;
	}
	
	//@Override 
	public boolean isEmpty(){
		return listeEntites.isEmpty();
	}
	
	public void setEntite(Entite ent){
		listeEntites.add(ent);
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
}
