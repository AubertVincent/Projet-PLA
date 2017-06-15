package carte;

import java.util.ArrayList;
import java.util.List;

import entite.Entity;
import entite.Team;
import gui.GUI;
import moteurDuJeu.Engine;
import personnages.Character;
import pickable.PickAble;

public class Map {

	// the Map attributes
	private int width = 34;
	private int height = 18;

	public final Cell[][] map = new Cell[width][height];

	private List<PickAble> pickableList;

	private GUI userInterface;

	/**
	 * USed to created a new map, this is the game's memory representation
	 * 
	 * @param userInterface
	 *            the GUI interface
	 */
	public Map(GUI userInterface) {
		this.pickableList = new ArrayList<PickAble>();
		this.userInterface = userInterface;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				map[i][j] = new Cell(i, j);
			}
		}
	}

	/**
	 * Used to initialize the map
	 * 
	 * @param userInterface
	 *            the GUI interface
	 * @param engine
	 *            the game model
	 */
	public void init(GUI userInterface, Engine engine) {
		// Set each game's player at their base
		map[engine.getPlayer(Team.ROUGE).getX()][engine.getPlayer(Team.ROUGE).getY()]
				.setEntity(engine.getPlayer(Team.ROUGE));
		map[engine.getPlayer(Team.BLEU).getX()][engine.getPlayer(Team.BLEU).getY()]
				.setEntity(engine.getPlayer(Team.BLEU));
		// Set the obstacles on the map thanks to the GUI interface
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (userInterface.isObstacle(i, j)) {
					this.setEntity(new Obstacle(i, j, this));
				}
			}
		}
		// Once obstacles are set,
		// Set random operator at random places
		int randomX = (int) (Math.random() * (width));
		int randomY = (int) (Math.random() * (height));
		PickAble newPickAble;
		for (int i = 0; i < engine.getNbrOperatorInitOnMap(); i++) {
			while (!map[randomX][randomY].isFree()) {
				randomX = (int) (Math.random() * (width));
				randomY = (int) (Math.random() * (height));
			}
			newPickAble = PickAble.randomPickable(randomX, randomY, this);
			this.setEntity(newPickAble);
			this.addPickAble(newPickAble);
			randomX = (int) (Math.random() * (width));
			randomY = (int) (Math.random() * (height));
		}
	}

	/**
	 * Used to get the map height
	 * 
	 * @return the map height
	 */
	public int mapHeight() {
		return this.height;
	}

	/**
	 * Used to get the map width
	 * 
	 * @return the map width
	 */
	public int mapWidth() {
		return this.width;
	}

	/**
	 * Used to get a specif cell of the map
	 * 
	 * @param x
	 *            the x of the specific cell
	 * @param y
	 *            the y of the specific cell
	 * @return the specifi Cell
	 */
	public Cell getCell(int x, int y) {
		return map[x][y];
	}

	/**
	 * Used to move a character from its current position to the
	 * position(newX,newY)
	 * 
	 * @param character
	 *            the character to move
	 * @param newX
	 *            the new charater's x position
	 * @param newY
	 *            the new charater's y position
	 */
	public void moveCharacter(Character character, int newX, int newY) {
		// Free the character's current cell
		this.getCell(character.getX(), character.getY()).FreeCell();
		// put the character on its new cell
		this.getCell(newX, newY).setEntity(character);
	}

	/**
	 * Used to get the list of all the entity set on the map
	 * 
	 * @return the list of all the entity set on the map
	 */
	public List<PickAble> getPickAbleList() {
		return this.pickableList;
	}

	/**
	 * Used to add a new pickable to the list of all the entity set on the map
	 * 
	 * @param pickable
	 *            the pickable to add at the list of all the entity
	 */
	public void addPickAble(PickAble pickable) {
		pickableList.add(pickable);
	}

	/**
	 * Set a new entity on the map at the entity position
	 * 
	 * @param entity
	 *            the entity to set
	 */
	public void setEntity(Entity entity) {
		this.getCell(entity.getX(), entity.getY()).setEntity(entity);
	}

	/**
	 * Used to get the nearest free cell of the (x,y) position the method check
	 * all the cell around the given position, start with distance = 1, then
	 * increments the distance until its find a free cell
	 * 
	 * @param x
	 *            the x position
	 * @param y
	 *            the x position
	 * @return the nearest free cell
	 */
	public Cell nearestFreeCell(int x, int y) {
		// get the (x,y) cell
		Cell freeCell = getCell(x, y);
		int distance = 1;
		while (!freeCell.isReachable()) {
			// check all the cell at at range = distance
			for (int i = distance; i >= -distance && !freeCell.isReachable(); i--) {
				if (i > 0) {
					for (int j = -Math.abs(distance - i); j <= Math.abs(distance - i) && !freeCell.isReachable(); j++) {
						if ((x + i >= 0 && x + i < mapWidth()) && (y + j >= 0 && y + j < mapHeight())) {
							if (getCell(x + i, y + j).isReachable()) {
								freeCell = getCell(x + i, y + j);
							}
						}
					}
				} else {
					for (int j = distance + i; j >= -(distance + i) && !freeCell.isReachable(); j--) {
						if ((x + i >= 0 && x + i < mapWidth()) && (y + j >= 0 && y + j < mapHeight())) {
							if (getCell(x + i, y + j).isReachable()) {
								freeCell = getCell(x + i, y + j);
							}
						}
					}
				}
			}
			// Do it for a bigger distance
			distance++;
		}
		return freeCell;
	}

	/**
	 * Used to get the current GUI interface
	 * 
	 * @return the current GUI interface
	 */
	public GUI getGUI() {
		return this.userInterface;
	}

	/**
	 * Used to get the Cell of the character's position pickable list
	 * 
	 * @param character
	 *            the cell position
	 * @return the pickable list of the character cell
	 */
	public List<PickAble> getPickAbleList(Character character) {
		return this.getCell(character.getX(), character.getY()).getPickAbleList();
	}

	/**
	 * Used to remove the give entity from the list of all the entity set on the
	 * map
	 * 
	 * @param entity
	 *            the entity to remove from the list of all the entity
	 */
	public void removePickAble(Entity e) {
		this.pickableList.remove(e);
	}

	/**
	 * used to remove the entity form its cell
	 * 
	 * @param entity
	 *            to remove
	 */
	public void remove(Entity entity) {
		entity.getCell().remove(entity);
	}

	/**
	 * Used to initialize the exploration map
	 * 
	 * @param userInterface
	 *            the GUi interface
	 */
	public void initExploration(GUI userInterface) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (userInterface.isObstacle(i, j)) {
					this.setEntity(new Obstacle(i, j, this));
					this.getCell(i, j).setExplored(true);
				}
			}
		}

	}

}
