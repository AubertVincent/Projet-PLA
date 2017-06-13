package carte;

import java.util.ArrayList;
import java.util.List;

import entite.Entity;
import entite.Team;
import exceptions.NotDoableException;
import personnages.Character;

public class Cell {
	protected int x;
	protected int y;

	protected List<Entity> entityList;
	boolean isfree;

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		entityList = new ArrayList<Entity>();
		isfree = true;
	}

	public Cell(int x, int y, Entity ent) {

		this.y = y;
		entityList = new ArrayList<Entity>();
		this.setEntity(ent);
		isfree = false;
	}

	public void remove(Entity entity) {
		entityList.remove(entity);
		isfree = entityList.isEmpty();
	}

	public boolean isEmpty() {
		return entityList.isEmpty();
	}

	public boolean isFree() {
		return this.isfree;
	}

	public void setEntity(Entity ent) {
		entityList.add(ent);
		this.isfree = false;
	}

	public void FreeCell() {
		isfree = true;
		entityList.clear();
	}

	public List<Entity> getPickAbleList() {
		List<Entity> pickableList = new ArrayList<Entity>();
		for (Entity e : entityList) {
			if (e.isPickAble()) {
				pickableList.add(e);
			}
		}
		return pickableList;
	}

	public void FreePickable() {
		// TODO ne pas tout nettoyer
	}

	public List<Entity> getEntityList() {
		return entityList;
	}

	public Character getOpponent(Team team) throws NotDoableException {
		int i = 0;
		Entity e;
		while (i < this.entityList.size()) {
			if (this.entityList.get(i).isCharacter()) {
				e = this.entityList.get(i);

				if (!((Character) e).getTeam().equals(team)) {
					return ((Character) e);
				}
			}
			i++;
		}
		throw new NotDoableException("Il est vrai j'ai trop d'adversaire ... mais pas là");
	}

	public boolean opponentHere(Team team) {
		int i = 0;
		Entity e;
		while (i < this.entityList.size()) {
			if (this.entityList.get(i).isCharacter()) {
				e = this.entityList.get(i);
				if (((Character) e).getTeam() != team) {
					return true;
				}
			}
			i++;
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

	// public static void main(String[] args) {
	// Cell ma_Cell = new Cell(4, 5);
	// System.out.println("is free ? : " + ma_Cell.isFree());
	// ma_Cell.setEntity(new Player(5, 12, Direction.NORTH, 1, 1, 1, 1, 5, 1));
	// System.out.println("is free ? : " + ma_Cell.isFree());
	// System.out.println(ma_Cell.getListEntity().toString());
	// ma_Cell.FreeCell();
	// System.out.println("is free ? : " + ma_Cell.isFree());
	// Cell ma_cell2 = new Cell(7, 6, new Player(6, 7, Direction.NORTH, 1, 1, 1,
	// 1, 5, 1));
	// System.out.println("cell 2 is free ? : " + ma_cell2.isFree());
	// System.out.println(ma_cell2.getListEntity().toString());
	//
	// }

}
