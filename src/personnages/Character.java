package personnages;

import entite.Direction;
import entite.Entity;
import pickable.*;

public abstract class Character extends Entity {

	protected Direction direction;

	protected int life;
	protected int vision;
	protected int attack;
	protected int range;
	protected int movePoints;
	protected int recall;

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
	public Character(int x, int y, Direction direction, int life, int vision, int attack, int range, int movePoints,
			int recall) {
		super(x, y);
		this.direction = direction;
		this.life = life;
		this.vision = vision;
		this.attack = attack;
		this.range = range;
		this.movePoints = movePoints;
		this.recall = recall;
	}

	protected abstract boolean isPlayer();

	public abstract boolean isRobot();

	public boolean isCharacter() {
		return true;
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
	 * Make an interaction between two fighters
	 * 
	 * @param attacker
	 *            the initiator of the attack
	 * @param opponent
	 *            the target
	 */
	public void classicAtk(Character attacker, Character opponent) {
		int lifeA = attacker.getLife();
		int lifeE = opponent.getLife();
		int atkA = attacker.getAttack();
		int atkE = opponent.getAttack();

		lifeA = java.lang.Math.max(lifeA - atkE, 0);
		lifeE = java.lang.Math.max(lifeE - atkA, 0);

		attacker.setLife(lifeA);
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
	public void teleport(Entity e, int x, int y) {
		e.setX(x);
		e.setY(y);
	}

	/**
	 * Pick up an entity
	 * 
	 * @param e
	 *            The entity which is picking up
	 */
	public void pickUp(Entity e) {
		PickAble.pick(e);
	}

}
