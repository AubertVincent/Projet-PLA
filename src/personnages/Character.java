package personnages;

import carte.Cell;
import carte.Map;
import entite.Direction;
import entite.Entity;
import entite.GameException;
import pickable.*;

public abstract class Character extends Entity {

	protected Direction direction;

	protected int life;
	protected int vision;
	protected int attack;
	protected int range;
	protected int movePoints;
	protected int recall;
	protected int player;

	/**
	 * Set a new character
	 * 
	 * @param x
	 *            x coordinate on the map
	 * @param y
	 *            y coordinate on the map
	 * @param direction
	 *            Where the character is oriented
	 * @param life
	 *            Character's life
	 * @param vision
	 *            Character's vision range
	 * @param attack
	 *            Character's attack
	 * @param range
	 *            Character's range
	 * @param movePoints
	 *            Character's move points
	 * @param recall
	 *            Character's recall's time
	 */
	public Character(int x, int y, Map entityMap, Direction direction, int life, int vision, int attack, int range,
			int movePoints, int recall, int player) {
		super(x, y, entityMap);
		this.direction = direction;
		this.life = life;
		this.vision = vision;
		this.attack = attack;
		this.range = range;
		this.movePoints = movePoints;
		this.recall = recall;
		this.player = player;
	}

	protected abstract boolean isPlayer();

	public abstract boolean isRobot();

	public boolean isCharacter() {
		return true;
	}

	public boolean isPickAble() {
		return false;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public Direction getDirection() {
		return this.direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getLife() {
		return this.life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getVision() {
		return this.vision;
	}

	public void setVision(int vision) {
		this.vision = vision;
	}

	public int getAttack() {
		return this.attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getRange() {
		return this.range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public int getMovePoints() {
		return this.movePoints;
	}

	public void setMovePoints(int movePoints) {
		this.movePoints = movePoints;
	}

	public int getRecall() {
		return this.recall;
	}

	public void setRecall(int recall) {
		this.recall = recall;
	}

	/**
	 * Move a character in the direction given of the length given
	 * 
	 * @param dir
	 *            Direction of the move
	 * @param lg
	 *            Length of the move
	 */
	public void goTo(Direction dir, int lg) { // lg?

		direction = dir;

		switch (direction) {
		case NORTH:
			setY(getY() - lg);
			break;
		case SOUTH:
			setY(getY() + lg);
			break;
		case EAST:
			setX(getX() + lg);
			break;
		case WEST:
			setX(getX() - lg);
			break;
		}
	}

	/**
	 * Make an Entity attack an entity on the cell targeted
	 * 
	 * @param target
	 *            The cell targeted
	 * @throws GameException
	 */
	public void classicAtk(Cell target) throws GameException {
		Character opponent = target.getOpponent(this.player);
		int lifeA = this.getLife();
		int lifeE = opponent.getLife();
		int atkA = this.getAttack();
		int atkE = opponent.getAttack();

		lifeA = java.lang.Math.max(lifeA - atkE, 0);
		lifeE = java.lang.Math.max(lifeE - atkA, 0);

		this.setLife(lifeA);
		opponent.setLife(lifeE);
	}

	/**
	 * Teleport an entity to the coordinates given
	 * 
	 * @param e
	 *            the entity
	 * @param x
	 *            x coordinate on the map
	 * @param y
	 *            y coordinate on the map
	 */
	public void teleport(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	/**
	 * Pick an entity ('picked' here) on the cell
	 * 
	 * @throws GameException
	 */
	public void pickUp() throws GameException {
		Map myMap = this.getEntityMap();
		Class<PickAble> picked = myMap.pickableEntity(this.getX(), this.getY());
		myMap.freePick(picked, this.getX(), this.getY());
		if (this.isRobot()) {
			int i = ((Robot) this).isToPlayer().besace.get(picked.getClass());
			((Robot) this).isToPlayer().besace.put(picked, i++);
		} else if (this.isPlayer()) {
			int i = ((Player) this).besace.get(picked.getClass());
			((Player) this).besace.put(picked, i++);
		}
	}
}
