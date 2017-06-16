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

	protected List<Entity> entityList;
	boolean isfree;
	private boolean isExplored;

	/**
	 * Created a new empty cell with x and y coordinates
	 * 
	 * @param x
	 *            the cell's x coordinated
	 * @param y
	 *            the cell's x coordinated
	 */
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		entityList = new ArrayList<Entity>();
		isfree = true;
		isExplored = false;
	}

	/**
	 * Created a new cell with x and y coordinates and an entity on it
	 * 
	 * @param x
	 *            the cell's x coordinated
	 * @param y
	 *            the cell's x coordinated
	 * @param ent
	 *            the Entity to set on the cell
	 */
	public Cell(int x, int y, Entity ent) {
		this.y = y;
		entityList = new ArrayList<Entity>();
		this.setEntity(ent);
		isfree = false;
		isExplored = false;
	}

	/**
	 * Used to remove the entity given
	 * 
	 * @param entity
	 *            the Entity to remove from the cell
	 */
	public void remove(Entity entity) {
		entityList.remove(entity);
		isfree = entityList.isEmpty();
	}

	/**
	 * Used the check if the cell is either empty or not
	 * 
	 * @return true if the current cell is empty
	 */
	public boolean isEmpty() {
		return entityList.isEmpty();
	}

	/**
	 * Used the check if the cell is either explored or not
	 * 
	 * @return true if the current cell is explored
	 */
	public boolean isExplored() {
		return isExplored;
	}

	/**
	 * used to set the current cell to either explored or not
	 * 
	 * @param isExplored
	 *            true => the cell is explored, false => the cell is unExplored
	 */
	public void setExplored(boolean isExplored) {
		this.isExplored = isExplored;
	}

	/**
	 * Used to check if the cell is either free or not
	 * 
	 * @return true if the cell is free
	 */
	public boolean isFree() {
		return this.isfree;
	}

	/**
	 * Set a new entity on the current cell
	 * 
	 * @param ent
	 *            the new entity to set on the cell
	 */
	public void setEntity(Entity ent) {
		entityList.add(ent);
		this.isfree = false;
	}

	/**
	 * Free the current cell
	 */
	public void FreeCell() {
		isfree = true;
		entityList.clear();
	}

	/**
	 * Used to get the pickable entity on the current cell
	 * 
	 * @return the cell's Pickable list
	 */
	public List<PickAble> getPickAbleList() {
		List<PickAble> pickableList = new ArrayList<PickAble>();
		for (Entity e : entityList) {
			if (e.isPickAble()) {
				pickableList.add((PickAble) e);
			}
		}
		return pickableList;
	}

	/**
	 * Used to get all the entity of the cell
	 * 
	 * @return the entity list of the current cell
	 */
	public List<Entity> getEntityList() {
		return entityList;
	}

	/**
	 * Used to list all the pickable of the cell
	 * 
	 * @return the pickable list of the current cell
	 */
	@SuppressWarnings("unchecked")
	public Class<PickAble> pickableEntity() {
		List<Entity> entityList = this.getEntityList();
		for (Iterator<Entity> entityIterator = entityList.iterator(); entityIterator.hasNext();) {
			Entity currentEntity = entityIterator.next();
			if (currentEntity.isPickAble()) {
				return ((Class<PickAble>) currentEntity.getClass());
			}
		}
		return null;
	}

	/**
	 * Used to remove the pickable given from the entity list of the cell
	 * 
	 * @param ramasse
	 *            the pickable which should be remove
	 */
	public void freePick(Class<PickAble> ramasse) {
		List<Entity> entityList = this.getEntityList();
		for (Iterator<Entity> entityIterator = entityList.iterator(); entityIterator.hasNext();) {
			Entity currentEntity = entityIterator.next();
			if (currentEntity.getClass() == ramasse) {
				entityList.remove(currentEntity);
			}
		}
	}

	/**
	 * Check if a cell can be reach
	 * 
	 * @return If the cell has no character nor obstacle, return true
	 */
	public boolean isReachable() {
		List<Entity> entityList = this.getEntityList();
		for (Iterator<Entity> entityIterator = entityList.iterator(); entityIterator.hasNext();) {
			Entity currentEntity = entityIterator.next();
			if (currentEntity.isCharacter() || currentEntity.isObstacle()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Get the opponent in the cell of the character who call the method
	 * 
	 * @param team
	 *            The team of the character who want to get his opponent
	 * @return The first opponent in the entity list of the cell
	 * @throws NotDoableException
	 */
	public Character getOpponent(Team team) throws NotDoableException {
		for (Iterator<Entity> it = this.entityList.iterator(); it.hasNext();) {
			Entity ent = it.next();
			if (ent.isCharacter()) {
				if (!((Character) ent).getTeam().equals(team)) {
					return ((Character) ent);
				}
			}
		}
		throw new NotDoableException("Il est vrai j'ai trop d'adversaire ... mais pas là");
	}

	/**
	 * Check if there is an opponent on the cell
	 * 
	 * @param team
	 *            the team of the character who call the function
	 * @return if there is an opponent here, return true
	 */
	public boolean opponentHere(Team team) {
		for (Iterator<Entity> it = this.entityList.iterator(); it.hasNext();) {
			Entity currentEntity = it.next();
			if (currentEntity.isCharacter()) {
				if (((Character) currentEntity).getTeam() != team) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Used to get the current X of the cell
	 * 
	 * @return the current cell X coordinate
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Used to get the current Y of the cell
	 * 
	 * @return the current cell Y coordinate
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Check if there is a pickable on the cell
	 * 
	 * @return if there is a pickable on the cell, return true
	 */
	public boolean pickAbleHere() {
		List<Entity> entityList = this.getEntityList();
		for (Iterator<Entity> entityIterator = entityList.iterator(); entityIterator.hasNext();) {
			Entity currentEntity = entityIterator.next();
			if (currentEntity.isPickAble()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if there is a robot on the cell
	 * 
	 * @return if there is a robot on the cell, return true
	 */
	public boolean robotHere() {
		List<Entity> entityList = this.getEntityList();
		for (Iterator<Entity> entityIterator = entityList.iterator(); entityIterator.hasNext();) {
			Entity currentEntity = entityIterator.next();
			if (currentEntity.isCharacter() && ((Character) currentEntity).isRobot()) {
				return true;
			}
		}
		return false;
	}

}
