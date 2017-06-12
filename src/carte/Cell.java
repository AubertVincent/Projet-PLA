package carte;

import java.util.ArrayList;
import java.util.List;

import entite.Entity;
import entite.Team;
import exceptions.NotDoableException;
import personnages.Character;

public class Cell {
	protected Coordinates coordinates;

	protected List<Entity> listeEntites;
	boolean isFree;

	public Cell(Coordinates coordinates) {
		this.coordinates = coordinates;
		listeEntites = new ArrayList<Entity>();
		isFree = true;
	}

	public Cell(Coordinates coordinates, Entity entity) {
		this.coordinates = coordinates;
		listeEntites = new ArrayList<Entity>();
		this.setEntity(entity);
		isFree = false;
	}

	public Cell(int x, int y) {
		coordinates = new Coordinates(x, y);
		listeEntites = new ArrayList<Entity>();
		isFree = true;
	}

	public Cell(int x, int y, Entity entity) {
		coordinates = new Coordinates(x, y);
		listeEntites = new ArrayList<Entity>();
		this.setEntity(entity);
		isFree = false;
	}

	public boolean isEmpty() {
		return listeEntites.isEmpty();
	}

	public boolean isFree() {
		return this.isFree;
	}

	public void setEntity(Entity entity) {
		listeEntites.add(entity);
		this.isFree = false;
	}

	public void FreeCell() {
		isFree = true;
		listeEntites.clear();
	}

	public List<Entity> getPickAbleList() {
		List<Entity> pickableList = new ArrayList<Entity>();
		for (Entity e : listeEntites) {
			if (e.isPickAble()) {
				pickableList.add(e);
			}
		}
		return pickableList;
	}

	public void FreeEntity() {
		// TODO ne pas tout nettoyer
	}

	public List<Entity> getListEntity() {
		return listeEntites;
	}

	public Character getOpponent(Team team) throws NotDoableException {
		int i = 0;
		Entity entity;
		while (i < this.listeEntites.size()) {
			if (this.listeEntites.get(i).isCharacter()) {
				entity = this.listeEntites.get(i);

				if (!((Character) entity).getTeam().equals(team)) {
					return ((Character) entity);
				}
			}
			i++;
		}
		throw new NotDoableException("Il est vrai j'ai trop d'adversaire ... mais pas là");
	}

	public boolean opponentHere(Team team) {
		int i = 0;
		Entity entity;
		while (i < this.listeEntites.size()) {
			if (this.listeEntites.get(i).isCharacter()) {
				entity = this.listeEntites.get(i);
				if (((Character) entity).getTeam() != team) {
					return true;
				}
			}
			i++;
		}
		return false;
	}

	public Coordinates getCoordinates() {
		return coordinates;
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
