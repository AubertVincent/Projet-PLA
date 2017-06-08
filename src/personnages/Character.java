package personnages;

import java.util.List;

import carte.Cell;
import carte.Map;
import entite.Direction;
import entite.Entity;
import entite.Team;
import exceptions.GameException;
import exceptions.NotDoableException;
import pickable.PickAble;
import pickable.Picked;

public abstract class Character extends Entity {

	public List<Picked> myOwnBesace;

	protected Direction direction;
	protected int life;
	protected int vision;
	protected int attack;
	protected int range;
	protected int movePoints;
	protected int recall;
	protected Team team;
	protected int attackPoints;

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
			int movePoints, int recall, int aP, Team team) {
		super(x, y, entityMap);
		this.direction = direction;
		this.life = life;
		this.vision = vision;
		this.attack = attack;
		this.range = range;
		this.movePoints = movePoints;
		this.recall = recall;
		this.attackPoints = aP;
		this.team = team;
	}

	public abstract boolean isPlayer();

	public abstract boolean isRobot();

	public boolean isCharacter() {
		return true;
	}

	public boolean isOperator() {
		return false;
	}

	public boolean isObstacle() {
		return false;
	}

	@Override
	public boolean isPickAble() {
		return false;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
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

	public int getAttackPoints() {
		return this.attackPoints;
	}

	public void setAttackPoints(int aP) {
		this.attackPoints = aP;
	}

	public void goTo(Direction dir, int lg) {

		direction = dir;

		switch (direction) {
		case NORTH:
			setY(getY() + lg);
			break;
		case SOUTH:
			setY(getY() - lg);
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
	public void classicAtk(Cell target) throws NotDoableException {
		try {
			Character opponent = target.getOpponent(this.team);
			int lifeA = this.getLife();
			int lifeE = opponent.getLife();
			int atkA = this.getAttack();
			int atkE = opponent.getAttack();

			lifeA = lifeA - atkE;
			lifeE = lifeE - atkA;

			this.setLife(lifeA);
			opponent.setLife(lifeE);

		} catch (NotDoableException e) {
			throw new NotDoableException("Personne à attaquer");
		}
	}

	public void cancelClassicAtk(Cell target) throws NotDoableException {
		try {
			Character opponent = target.getOpponent(this.team);
			int lifeA = this.getLife();
			int lifeE = opponent.getLife();
			int atkA = this.getAttack();
			int atkE = opponent.getAttack();

			lifeA = lifeA + atkE;
			lifeE = lifeE + atkA;

			this.setLife(lifeA);
			opponent.setLife(lifeE);

		} catch (NotDoableException e) {
			throw new NotDoableException("Personne à attaquer");
		}
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
	public void pickUp() throws NotDoableException {
		List<Class<PickAble>> listPicked = null;
		Picked picked = new Picked(listPicked);
		int i = 0;
		Map myMap = this.getEntityMap();
		try {
			while (true) {
				Class<PickAble> classPicked = myMap.pickableEntity(this.getX(), this.getY());
				myMap.freePick(classPicked, this.getX(), this.getY());
				if (this.isRobot()) {
					i = ((Robot) this).getPlayer().besace.get(classPicked.getClass());
					((Robot) this).getPlayer().besace.put(classPicked, i++);
				} else if (this.isPlayer()) {
					i = ((Player) this).besace.get(classPicked.getClass());
					((Player) this).besace.put(classPicked, i++);
				}
				picked.add(classPicked);
				myOwnBesace.add(picked);
			}

		} catch (NotDoableException e) {

		}
	}

	public void placePickAble(int x, int y, Class<PickAble> picked, Map map) {
		try {
			map.getCell(x, y).setEntity(picked.newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void cancelPickUp() throws NotDoableException {
		int x = this.getX();
		int y = this.getY();
		int i = 0;
		Picked picked = this.myOwnBesace.get(this.myOwnBesace.size() - 1);
		Map myMap = this.getEntityMap();
		while (picked.size() > 0) {
			Class<PickAble> classPicked = picked.get(0);
			if (this.isRobot()) {
				i = ((Robot) this).getPlayer().besace.get(classPicked.getClass());
				((Robot) this).getPlayer().besace.put(classPicked, i--);
			} else if (this.isPlayer()) {
				i = ((Player) this).besace.get(classPicked.getClass());
				((Player) this).besace.put(classPicked, i--);
				this.placePickAble(x, y, classPicked, myMap);
				picked.remove(0);
			}

		}
	}

	//
	// public int getXBase() {
	// if (this.getTeam() == Team.ROUGE) {
	// return 2;
	// } else {
	// return 31;
	// }
	// }
	//
	// public int getYBase() {
	// if (this.getTeam() == Team.ROUGE) {
	// return 4;
	// } else {
	// return 15;
	// }
	// }

}
