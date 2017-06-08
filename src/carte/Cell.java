package carte;

import java.util.ArrayList;
import java.util.List;

import entite.*;
import personnages.*;
import exceptions.*;

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
		this.isfree = false;

	}

	public void FreeCell() {
		isfree = true;
		listeEntites.clear();
	}

	public void FreeEntity() {
		// TODO ne pas tout nettoyer
	}

	public List<Entity> getListEntity() {
		return listeEntites;
	}

	public Character getOpponent(int player) throws NotDoableException {
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
		throw new NotDoableException("Il est vrai j'ai trop d'adversaire ... mais pas là");
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

	@SuppressWarnings("unused")
	private int getX() {
		return this.x;
	}

	@SuppressWarnings("unused")
	private int getY() {
		return this.y;
	}

	public static void main(String[] args) {
		Cell ma_Cell = new Cell(4, 5);
		System.out.println("is free ? : " + ma_Cell.isFree());
		ma_Cell.setEntity(new Player(5, 12, Direction.NORTH, 1, 1, 1, 1, 5, 1, 1));
		System.out.println("is free ? : " + ma_Cell.isFree());
		System.out.println(ma_Cell.getListEntity().toString());
		ma_Cell.FreeCell();
		System.out.println("is free ? : " + ma_Cell.isFree());
		Cell ma_cell2 = new Cell(7, 6, new Player(6, 7, Direction.NORTH, 1, 1, 1, 1, 5, 1, 1));
		System.out.println("cell 2 is free ? : " + ma_cell2.isFree());
		System.out.println(ma_cell2.getListEntity().toString());

	}

}
