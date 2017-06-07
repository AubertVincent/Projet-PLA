package carte;

import java.util.ArrayList;
import java.util.List;

import entite.*;
import personnages.Character;

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

	public void setEntity(Entity ent) {
		listeEntites.add(ent);
	}

	public void FreeCell() {
		isfree = true;
		listeEntites.clear();
	}

	public List<Entity> getListEntity() {
		return listeEntites;
	}

	public Character getOpponent(int player) throws GameException {
		int i = 0;
		Entity e;
		while (i < this.listeEntites.size() - 1) {
			if (this.listeEntites.get(i).isCharacter()) {
				e = this.listeEntites.get(i);
				if (((Character) e).getPlayer() == player) {
					return ((Character) e);
				}
			}
		}
		throw new GameException("Il est vrai j'ai trop d'adversaire ... mais pas là");
	}

	public boolean opponentHere(int player) {
		int i = 0;
		Entity e;
		while (i < this.listeEntites.size() - 1) {
			if (this.listeEntites.get(i).isCharacter()) {
				e = this.listeEntites.get(i);
				if (((Character) e).getPlayer() == player) {
					return true;
				}
			}
		}
		return false;
	}
}