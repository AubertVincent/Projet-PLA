package personnages;

import entite.Direction;
import entite.Entity;

public abstract class Caracter extends Entity {

	private Direction direction;

	protected int life;
	protected int vision;
	protected int attack;
	protected int range;
	protected int movePoints;
	protected int recall;

	public Caracter(int x, int y, Direction direction, int life, int vision, int attack, int range, int movePoints,
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

	public abstract boolean isPlayer();

	public abstract boolean isRobot();

	public boolean isCaracter() {
		return true;
	}

	public boolean isOperator() {
		return false;
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

	public void move() {
		// TODO
	}

	public void goTo(Direction dir, int lg) {

		direction = dir;

		switch (direction) {
		case NORTH:
			setY(getY() + lg);
			break;
		case SOUTH:
			setY(getY() - lg);
			;
			break;
		case EAST:
			setX(getX() + lg);
			break;
		case WEST:
			setX(getX() - lg);
			break;
		}
	}

	public void classicAtk() {
		// TODO
	}
}