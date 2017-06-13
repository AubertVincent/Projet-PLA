package carte;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import entite.Entity;
import entite.Team;
import exceptions.NotDoableException;
import personnages.Character;
import pickable.PickAble;

public class Cell {
	protected int x;
	protected int y;

	protected List<Entity> listeEntites;
	boolean isfree;
	boolean isExplored;

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		listeEntites = new ArrayList<Entity>();
		isfree = true;
		isExplored = false;
	}

	public Cell(int x, int y, Entity ent) {

		this.y = y;
		listeEntites = new ArrayList<Entity>();
		this.setEntity(ent);
		isfree = false;
		isExplored = false;
	}

	public boolean isEmpty() {
		return listeEntites.isEmpty();
	}

	public boolean isExplored() {
		return isExplored;
	}

	public void setExplored(boolean isExplored) {
		this.isExplored = isExplored;
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

	public List<Entity> getPickAbleList() {
		List<Entity> pickableList = new ArrayList<Entity>();
		for (Entity e : listeEntites) {
			if (e.isPickAble()) {
				pickableList.add(e);
			}
		}
		return pickableList;
	}

	public void FreePickable() {
		// TODO ne pas tout nettoyer
	}

	public List<Entity> getListEntity() {
		return listeEntites;
	}

	@SuppressWarnings("unchecked")
	public Class<PickAble> pickableEntity() {
		List<Entity> entityList = this.getListEntity();
		for (Iterator<Entity> entityIterator = entityList.iterator(); entityIterator.hasNext();) {
			Entity currentEntity = entityIterator.next();
			if (currentEntity.isPickAble()) {
				return ((Class<PickAble>) currentEntity.getClass());
			}
		}
		return null;
	}

	public void freePick(Class<PickAble> ramasse) {
		List<Entity> entityList = this.getListEntity();
		for (Iterator<Entity> entityIterator = entityList.iterator(); entityIterator.hasNext();) {
			Entity currentEntity = entityIterator.next();
			if (currentEntity.getClass() == ramasse) {
				entityList.remove(currentEntity);
			}
		}
	}

	public boolean isReachable() {
		List<Entity> entityList = this.getListEntity();
		for (Iterator<Entity> entityIterator = entityList.iterator(); entityIterator.hasNext();) {
			Entity currentEntity = entityIterator.next();
			if (currentEntity.isCharacter() || currentEntity.isObstacle()) {
				return false;
			}
		}
		return true;
	}

	public Character getOpponent(Team team) throws NotDoableException {
		int i = 0;
		Entity e;
		for (Iterator<Entity> it = this.listeEntites.iterator(); it.hasNext();) {
			// while (i < this.listeEntites.size()) {
			if (this.listeEntites.get(i).isCharacter()) {
				e = this.listeEntites.get(i);

				if (!((Character) e).getTeam().equals(team)) {
					return ((Character) e);
				}
			}
			i++;
		}
		throw new NotDoableException("Il est vrai j'ai trop d'adversaire ... mais pas là");
	}

	public boolean opponentHere(Team team) {
		for (Iterator<Entity> it = this.listeEntites.iterator(); it.hasNext();) {
			Entity currentEntity = it.next();
			if (currentEntity.isCharacter()) {
				if (((Character) currentEntity).getTeam() != team) {
					return true;
				}
			}
		}
		return false;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public boolean pickAbleHere() {
		List<Entity> entityList = this.getListEntity();
		for (Iterator<Entity> entityIterator = entityList.iterator(); entityIterator.hasNext();) {
			Entity currentEntity = entityIterator.next();
			if (currentEntity.isPickAble()) {
				return true;
			}
		}
		return false;
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
