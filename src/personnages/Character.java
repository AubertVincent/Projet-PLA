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
import gui.GUICharacter;
import operateur.Action;
import pickable.PickAble;

public abstract class Character extends Entity {

	// protected Besace besace;

	protected Direction direction = Direction.SOUTH;
	protected int life;
	protected int vision;
	protected int damages;
	protected int range;
	protected int movePoints;
	protected int remainingAttacks;
	protected int recall;

	protected static List<Class<? extends Action>> possibleActionsList = new LinkedList<Class<? extends Action>>();
	protected Team team;
	protected Base base;
	private GUICharacter mySelfGUI;

	public Character(int x, int y, Map entityMap, Base base) throws Exception {

		super(x, y, entityMap);
		if (this instanceof Player) {

			this.direction = Direction.SOUTH;
			this.life = 10;
			this.vision = 5;
			this.damages = 3;
			this.range = 3;
			this.movePoints = 10;
			this.remainingAttacks = 5;
			this.recall = 3;

			this.team = base.getBaseTeam();
			this.base = base;
		} else if (this instanceof Robot) {
			this.direction = Direction.SOUTH;
			this.life = 5;
			this.vision = 1;
			this.damages = 2;
			this.range = 4;
			this.movePoints = 5;
			this.remainingAttacks = 3;
			this.recall = 3;
			this.team = base.getBaseTeam();
			this.base = base;
		} else {
			throw new Exception("Unimplemented subClass Character");
		}
	}

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
		return this.damages;
	}

	public void setAttack(int attack) {
		this.damages = attack;
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
		return this.remainingAttacks;
	}

	public void setAttackPoints(int aP) {
		this.remainingAttacks = aP;
	}

	public Besace getBesace() throws Exception {
		if (this instanceof Player) {
			return this.getBesace();
		} else {
			throw new Exception("Pas de besace pour un robot");
		}
	}

	@Override
	public void setX(int x) {
		this.getEntityMap().moveCharacter(this, x, this.getY());
		super.setX(x);
	}

	@Override
	public void setY(int y) {
		this.getEntityMap().moveCharacter(this, this.getX(), y);
		super.setY(y);
	}

	public void setGUICharacter(GUICharacter guiCharacter) {
		this.mySelfGUI = guiCharacter;
	}

	public void goTo(Direction dir, int lg) {
		for (int i = 0; i < lg; i++) {
			switch (dir) {
			case SOUTH:
				this.setY((this.getY() + 1));
				break;
			case NORTH:
				this.setY(this.getY() - 1);
				break;
			case WEST:
				this.setX(this.getX() - 1);
				break;
			case EAST:
				this.setX(this.getX() + 1);
				break;
			}

			this.pickUp();
			this.setMovePoints(this.getMovePoints() - 1);
		}
	}

	public void pickUp() {
		Besace PlayerBesace;
		try {
			PlayerBesace = this.getBesace();
			for (Entity e : this.getEntityMap().getPickAbleListOnCell(this.getX(), this.getY())) {
				PlayerBesace.add(((PickAble) e).getClass());
			}
		} catch (Exception e1) {
			e1.getMessage();
		}

	}

	/**
	 * Make an Entity attack an entity on the targeted cell
	 * 
	 * @param target
	 *            The cell targeted
	 * @throws GameException
	 */
	public void classicAtk(Cell target) {

		Character opponent = null;
		try {
			opponent = target.getOpponent(this.getTeam());
		} catch (NotDoableException e) {
			e.getMessage();
		}
		this.classicAtkTmp(target, opponent);
		this.setAttackPoints(this.getAttackPoints() - 1);

		if (opponent != null && opponent.getLife() <= 0) {
			try {
				throw new Exception("NYI");
			} catch (Exception e) {
				e.printStackTrace();
			}
			// FIXME
			// If the opponent's hero dies => End of game
			// opponent.dies();
		}
	}

	private void classicAtkTmp(Cell target, Character opponent) {
		int lifeA = this.getLife();
		int lifeE = opponent.getLife();
		int atkA = this.getAttack();
		int atkE = opponent.getAttack();

		lifeA = lifeA - atkE;
		lifeE = lifeE - atkA;

		this.setLife(lifeA);
		opponent.setLife(lifeE);
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
		this.pickUp();
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

}
