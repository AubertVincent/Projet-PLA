package personnages;

import java.util.LinkedList;
import java.util.List;

import entite.Direction;
import entite.Entity;
import operateur.Action;

public abstract class Character extends Entity {

	protected Direction direction;

	protected int life;
	protected int vision;
	protected int attack;
	protected int range;
	protected int movePoints;
	protected int recall;

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

	protected abstract boolean isRobot();

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

	public void teleport(Character c, int x, int y) {
		c.setX(x);
		c.setY(y);
	}
}
