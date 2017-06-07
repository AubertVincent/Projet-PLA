package carte;

import java.util.ArrayList;
import java.util.List;

import entite.Direction;
import entite.Entity;
import personnages.Player;

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

	public Cell(int x, int y, Entity ent) {

		this.y = y;
		listeEntites = new ArrayList<Entity>();
		this.setEntity(ent);
		isfree = false;
	}

	public boolean isEmpty() {
		return listeEntites.isEmpty();
	}

	public boolean isFree() {
		return this.isfree;
	}

	public void setEntity(Entity ent) {
		listeEntites.add(ent);
		this.isfree = false;
	}

	public void FreeCell() {
		isfree = true;
		listeEntites.clear();
	}
	public void FreeEntity(){
		// TODO ne pas tout nettoyer
	}

	public List<Entity> getListEntity() {
		return listeEntites;
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
		ma_Cell.setEntity(new Player(5, 12, Direction.NORTH, 1, 1, 1, 1, 5, 1));
		System.out.println("is free ? : " + ma_Cell.isFree());
		System.out.println(ma_Cell.getListEntity().toString());
		ma_Cell.FreeCell();
		System.out.println("is free ? : " + ma_Cell.isFree());
		Cell ma_cell2 = new Cell(7, 6, new Player(6, 7, Direction.NORTH, 1, 1, 1, 1, 5, 1));
		System.out.println("cell 2 is free ? : " + ma_cell2.isFree());
		System.out.println(ma_cell2.getListEntity().toString());
		
	}

}
