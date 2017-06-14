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

	public List<PickAble> getPickAbleList() {
		List<PickAble> pickableList = new ArrayList<PickAble>();
		for (Entity e : listeEntites) {
			if (e.isPickAble()) {
				pickableList.add((PickAble) e);
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
		for (Iterator<Entity> it = this.listeEntites.iterator(); it.hasNext();) {
			Entity ent = it.next();
			if (ent.isCharacter()) {
				if (!((Character) ent).getTeam().equals(team)) {
					return ((Character) ent);
				}
			}
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

	public boolean robotHere() {
		List<Entity> entityList = this.getListEntity();
		for (Iterator<Entity> entityIterator = entityList.iterator(); entityIterator.hasNext();) {
			Entity currentEntity = entityIterator.next();
			if (currentEntity.isCharacter() && ((Character) currentEntity).isRobot()) {
				return true;
			}
		}
		return false;
	}

}
