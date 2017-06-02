package carte;

import java.util.ArrayList;
import java.util.List;

import entite.Entite;

public class Cellule {
	protected int x;
	protected int y;
	protected List<Entite> listeEntites;
	boolean isfree;

	public Cellule(int x, int y) {
		this.x = x;
		this.y = y;
		listeEntites = new ArrayList<Entite>();
		isfree = true;
	}

	public Cellule(int x, int y, List<Entite> listeEnt) {
		this.x = x;
		this.y = y;
		listeEntites = listeEnt;
		isfree = false;
	}

	public boolean isEmpty() {
		return listeEntites.isEmpty();
	}

	public boolean isFree() {
		return isfree;
	}

	public void setEntite(Entite ent) {
		listeEntites.add(ent);
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
}
