package carte;

import java.util.ArrayList;
import java.util.List;

import entite.Entity;

public class Cell {
	protected int x;
	protected int y;
	

	protected List<Entity> listeEntites;
	boolean isfree;

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		listeEntites = new ArrayList<Entity>();
		isfree = true;
	}

	public Cell(int x, int y, List<Entity> listeEnt) {

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

	
	public void setEntity(Entity ent){
		listeEntites.add(ent);
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
}
