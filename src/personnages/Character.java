package personnages;

import java.util.LinkedList;
import java.util.List;

import carte.Base;
import carte.Cell;
import carte.Map;
import entite.Direction;
import entite.Entity;
import entite.Team;
import exceptions.GameException;
import exceptions.NotDoableException;
import operateur.Action;
import pickable.PickAble;

public abstract class Character extends Entity {

	protected Besace besace;

	protected Direction direction = Direction.SOUTH;
	protected int life = 10;
	protected int vision = 5;
	protected int attack = 3;
	protected int range = 3;
	protected int movePoints = 10;
	protected int attackPoints = 5;
	protected int recall = 3;

	protected static List<Class<? extends Action>> possibleActionsList = new LinkedList<Class<? extends Action>>();
	protected Team team;
	protected Base base;

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
	 * @throws Exception
	 */
	public Character(int x, int y, Map entityMap, Base base) throws Exception {

		super(x, y, entityMap);
		if (this instanceof Player) {

			this.direction = Direction.SOUTH;
			this.life = 10;
			this.vision = 5;
			this.attack = 3;
			this.range = 3;
			this.movePoints = 10;
			this.attackPoints = 5;
			this.recall = 3;

			this.team = base.getBaseTeam();
			this.base = base;
		} else if (this instanceof Robot) {
			this.direction = Direction.SOUTH;
			this.life = 1;
			this.vision = 3;
			this.attack = 2;
			this.range = 4;
			this.movePoints = 5;
			this.attackPoints = 3;
			this.recall = 3;
			this.team = base.getBaseTeam();
			this.base = base;
		} else {

			throw new Exception("Unimplemented subClass Character");
		}
	}

	// public Character(int x, int y, Map entityMap, Direction direction, int
	// life, int vision, int attack, int range,
	// int movePoints, int recall, int attackPoints, Team team, Base base)
	// throws Exception {
	//
	// super(x, y, entityMap);
	// if (this instanceof Robot) {
	//
	// this.direction = direction;
	// this.life = life;
	// this.vision = vision;
	// this.attack = attack;
	// this.range = range;
	// this.movePoints = movePoints;
	// this.recall = recall;
	// this.team = team;
	// this.attackPoints = attackPoints;
	// this.base = base;
	// } else {
	// throw new Exception("Unimplemented subClass Character");
	// }
	// }

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
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

	// public GUICharacter getGUICharacter() {
	// return this.GUICharactere;
	// }
	//
	// public void setGUICharacter(GUICharacter guiCharacter) {
	// this.GUICharactere = guiCharacter;
	// }

	// public void resetBesace() {
	// // FIXME adapt besace : implement clear for this class
	// this.besace.clear();
	// }

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

	// TODO pas Terminer (=> a laisser dans le code)
	// public void kill(Robot rob) {
	// int x = rob.getX();
	// int y = rob.getY();
	// ArrayList<PickAble> listePickable;
	// for (Class<? extends Action> r : rob.getPossibleActionsList()){
	// listePickable.add(actionToPickAble(r));
	// }
	// }

	public void kill(Player joueur) {

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
	// public void pickUp() throws NotDoableException {
	// List<Class<PickAble>> listPicked = null;
	// Picked picked = new Picked(listPicked);
	// Map myMap = this.getEntityMap();
	// try {
	// while (true) {
	// Class<PickAble> classPicked = myMap.pickableEntity(this.getX(),
	// this.getY());
	// myMap.freePick(classPicked, this.getX(), this.getY());
	// if (this.isRobot()) {
	// ((Robot) this).getPlayer().getBesace().add(classPicked);
	// } else if (this.isPlayer()) {
	// ((Player) this).getBesace().add(classPicked);
	// }
	// picked.add(classPicked);
	// // FIXME
	// besace.add(picked.getClass());
	// }
	//
	// } catch (NotDoableException e) {
	//
	// }
	// }

	public void placePickAble(int x, int y, Class<PickAble> picked, Map map) {
		try {
			map.getCell(x, y).setEntity(picked.newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	// public void cancelPickUp() throws NotDoableException {
	// int x = this.getX();
	// int y = this.getY();
	// // TODO : implement size for the class Besace
	// Picked picked = besace.get(besace.size() - 1);
	// Map myMap = this.getEntityMap();
	// while (picked.size() > 0) {
	// Class<PickAble> classPicked = picked.get(0);
	// if (this.isRobot()) {
	// ((Robot) this).getPlayer().getBesace().remove(classPicked);
	// } else if (this.isPlayer()) {
	// ((Player) this).getBesace().remove(classPicked);
	// }
	// this.placePickAble(x, y, classPicked, myMap);
	// picked.remove(0);
	// }
	//
	// }

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
