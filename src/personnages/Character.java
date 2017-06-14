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
import pickable.PickAble;

public abstract class Character extends Entity {

	protected Direction direction = Direction.SOUTH;
	protected int life;
	protected int vision;
	protected int damages;
	protected int range;
	protected int movePoints;
	protected int remainingAttacks;
	protected int recall;

	protected static List<Class<?>> possibleActionsList = new LinkedList<Class<?>>();
	protected Team team;
	protected Base base;

	private State state;

	public Character(int x, int y, Map entityMap, Base base) {

		super(x, y, entityMap);
		if (this instanceof Player) {

			this.direction = Direction.SOUTH;
			this.life = 10;
			this.vision = 5;
			this.damages = 3;
			this.range = 3;
			this.movePoints = 2;
			this.remainingAttacks = 5;
			this.recall = 3;

			this.team = base.getBaseTeam();
			this.base = base;
			this.state = State.Wait;
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
			this.state = State.Wait;
		} else {
			try {
				throw new Exception("Unimplemented subClass Character");
			} catch (Exception e) {
				e.printStackTrace();
			}
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
		this.getMap().moveCharacter(this, x, this.getY());
		super.setX(x);
	}

	@Override
	public void setY(int y) {
		this.getMap().moveCharacter(this, this.getX(), y);
		super.setY(y);
	}

	public void goTo(Direction dir, int lg) {
		this.setState(State.ClassiqueMove);
		boolean moveSucces = false;
		if (this instanceof Robot) {
			for (int i = 0; i < lg; i++) {
				switch (dir) {
				case SOUTH:
					this.setY((this.getY() + 1));
					moveSucces = true;
					break;
				case NORTH:
					this.setY(this.getY() - 1);
					moveSucces = true;
					break;
				case WEST:
					this.setX(this.getX() - 1);
					moveSucces = true;
					break;
				case EAST:
					this.setX(this.getX() + 1);
					moveSucces = true;
					break;
				}
			}
		} else {
			if (this.getMovePoints() > 0) {

				switch (dir) {
				case SOUTH:
					if (getMap().getCell(getX(), getY() + 1).isReachable()) {
						this.setY((this.getY() + 1));
						moveSucces = true;
					}
					break;
				case NORTH:
					if (getMap().getCell(getX(), getY() - 1).isReachable()) {
						this.setY((this.getY() - 1));
						moveSucces = true;
					}
					break;
				case WEST:
					if (getMap().getCell(getX() - 1, getY()).isReachable()) {
						this.setX(this.getX() - 1);
						moveSucces = true;
					}
					break;
				case EAST:
					if (getMap().getCell(getX() + 1, getY()).isReachable()) {
						this.setX(this.getX() + 1);
						moveSucces = true;
					}
					break;
				}

			} else {
				System.out.println("Plus de point de mouvement");
			}

		}
		if (moveSucces) {
			this.setDirection(dir);
			this.pickUp();
			this.setMovePoints(this.getMovePoints() - 1);
		}
	}

	public void pickUp() {
		Besace besaceOfCurrentPlayer;
		try {
			if (this instanceof Player) {
				besaceOfCurrentPlayer = this.getBesace();
			} else {
				besaceOfCurrentPlayer = this.getPlayer().getBesace();
			}
			List<PickAble> pickableList = this.getPickAbleList();
			for (Entity e : pickableList) {
				besaceOfCurrentPlayer.add(((PickAble) e).getClass());
				this.getMap().removePickAble(e);
			}
			this.getPickAbleList().clear();

		} catch (Exception e1) {
			e1.getMessage();
		}

	}

	private List<PickAble> getPickAbleList() {

		return this.getMap().getPickAbleList(this);

	}

	/**
	 * Make an Entity attack an entity on the targeted cell
	 * 
	 * @param target
	 *            The cell targeted
	 * @throws GameException
	 */
	public void classicAtk(Cell target) {
		this.setState(State.ClassicAttack);
		Character opponent = null;
		try {
			if (this.getAttackPoints() > 0) {
				this.getMyselfGUI().setActionRequest(true);
				opponent = target.getOpponent(this.getTeam());
				this.classicAtkTmp(target, opponent);
				this.setAttackPoints(this.getAttackPoints() - 1);

				if (opponent != null && opponent.getLife() <= 0) {
					this.kill(opponent);
				}
			}
		}

		catch (NotDoableException e) {
			e.getMessage();
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public abstract void die();

	public void kill(Character character) {

		character.dropPickables();
		character.setState(State.Dying);
	}

	protected abstract void dropPickables();

	public abstract GUICharacter getMyselfGUI();

	public abstract Player getPlayer();
}
