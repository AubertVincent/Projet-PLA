package personnages;

import java.util.Iterator;
import java.util.List;

import carte.Base;
import carte.Coordinates;
import carte.Map;
import entite.Direction;
import entite.Entity;
import exceptions.NotDoableException;
import gui.GUI;
import gui.GUIRobot;
import operateur.Action;
import operateur.ClassicAck;
import operateur.MoveDir;
import sequence._Sequence;
import util.Pair;

public class Robot extends Character {

	protected _Sequence myAutomaton;
	private java.util.Map<Pair<Direction, Integer>, Pair<Robot, Integer>> targetsLife;
	private GUIRobot mySelfGUI;

	private Player player;

	public Robot(Base base, Map entityMap, GUI userInterface, _Sequence myAutomaton, Player player) throws Exception {
		super(base.getX(), base.getY(), entityMap, base);
		this.myAutomaton = myAutomaton;
		this.mySelfGUI = new GUIRobot(userInterface, base.getX(), base.getY(), Direction.SOUTH, 100, base.getBaseTeam(),
				this, player.getMyselfGUI());
		this.player = player;
		this.player.addRobot(new Coordinates(base.getX(), base.getY()), this);
		this.player.getMyselfGUI().addGUIRobot(this.mySelfGUI);
		super.setGUICharacter(this.mySelfGUI);
	}

	static {
		possibleActionsList.add(ClassicAck.class);
		possibleActionsList.add(MoveDir.class);
		// possibleActionsList.add(Tunnel.class);
		// possibleActionsList.add(Recall.class);
	}

	public static List<Class<? extends Action>> getPossibleActionsList() {
		return possibleActionsList;
	}

	@Override
	public boolean isPlayer() {
		return false;
	}

	@Override
	public boolean isRobot() {
		return true;
	}

	@Override
	public boolean isObstacle() {
		return false;
	}

	public _Sequence getAutomaton() {
		return this.myAutomaton;
	}

	public void setAutomaton(_Sequence automaton) {
		this.myAutomaton = automaton;
	}

	public GUIRobot getMyselfGUI() {
		return this.mySelfGUI;
	}

	public Player getPlayer() {
		return this.player;
	}

	/**
	 * Suicide a Robot and kill the Robots next to it
	 * 
	 * @param e
	 *            The Robot which is suiciding
	 */

	/**
	 * Suicide a robot and kill the robots around it
	 */
	public void suicideBomber() {
		int x = this.getX();
		int y = this.getY();
		List<Entity> northEntityList = this.entityMap.getListEntity(x, y - 1);
		List<Entity> southEntityList = this.entityMap.getListEntity(x, y + 1);
		List<Entity> westEntityList = this.entityMap.getListEntity(x - 1, y);
		List<Entity> eastEntityList = this.entityMap.getListEntity(x + 1, y);

		// Cell testN = this.entityMap.getCell(x, y-1);

		int i = 0;
		for (Iterator<Entity> entityIterator = northEntityList.iterator(); entityIterator.hasNext();) {
			Entity eCourant = entityIterator.next();
			if (eCourant instanceof Robot) {
				this.targetsLife.put(new Pair<Direction, Integer>(Direction.NORTH, i),
						new Pair<Robot, Integer>(((Robot) eCourant), ((Robot) eCourant).getLife()));
				((Robot) eCourant).setLife(0);
				i++;
			}
		}

		i = 0;
		for (Iterator<Entity> entityIterator = southEntityList.iterator(); entityIterator.hasNext();) {
			Entity eCourant = entityIterator.next();
			if (eCourant instanceof Robot) {
				this.targetsLife.put(new Pair<Direction, Integer>(Direction.SOUTH, i),
						new Pair<Robot, Integer>(((Robot) eCourant), ((Robot) eCourant).getLife()));
				((Robot) eCourant).setLife(0);
				i++;
			}
		}

		i = 0;
		for (Iterator<Entity> entityIterator = westEntityList.iterator(); entityIterator.hasNext();) {
			Entity eCourant = entityIterator.next();
			if (eCourant instanceof Robot) {
				this.targetsLife.put(new Pair<Direction, Integer>(Direction.WEST, i),
						new Pair<Robot, Integer>(((Robot) eCourant), ((Robot) eCourant).getLife()));
				((Robot) eCourant).setLife(0);
				i++;
			}
		}

		i = 0;
		for (Iterator<Entity> entityIterator = eastEntityList.iterator(); entityIterator.hasNext();) {
			Entity eCourant = entityIterator.next();
			if (eCourant instanceof Robot) {
				this.targetsLife.put(new Pair<Direction, Integer>(Direction.EAST, i),
						new Pair<Robot, Integer>(((Robot) eCourant), ((Robot) eCourant).getLife()));
				((Robot) eCourant).setLife(0);
				i++;
			}
		}
	}

	public void cancelSuicideBomber() {
		int x = this.getX();
		int y = this.getY();
		List<Entity> northEntityList = this.entityMap.getListEntity(x, y - 1);
		List<Entity> southEntityList = this.entityMap.getListEntity(x, y + 1);
		List<Entity> westEntityList = this.entityMap.getListEntity(x - 1, y);
		List<Entity> eastEntityList = this.entityMap.getListEntity(x + 1, y);

		int i = 0;
		for (Iterator<Entity> entityIterator = northEntityList.iterator(); entityIterator.hasNext();) {
			Entity eCourant = entityIterator.next();
			if (eCourant instanceof Robot) {
				Pair<Direction, Integer> key = new Pair<Direction, Integer>(Direction.NORTH, i);
				Pair<Robot, Integer> robotLife = this.targetsLife.get(key);
				((Robot) eCourant).setLife(robotLife.getSecond());
				i++;
			}
		}

		i = 0;
		for (Iterator<Entity> entityIterator = southEntityList.iterator(); entityIterator.hasNext();) {
			Entity eCourant = entityIterator.next();
			if (eCourant instanceof Robot) {
				Pair<Direction, Integer> key = new Pair<Direction, Integer>(Direction.SOUTH, i);
				Pair<Robot, Integer> robotLife = this.targetsLife.get(key);
				((Robot) eCourant).setLife(robotLife.getSecond());
				i++;
			}
		}

		i = 0;
		for (Iterator<Entity> entityIterator = westEntityList.iterator(); entityIterator.hasNext();) {
			Entity eCourant = entityIterator.next();
			if (eCourant instanceof Robot) {
				Pair<Direction, Integer> key = new Pair<Direction, Integer>(Direction.WEST, i);
				Pair<Robot, Integer> robotLife = this.targetsLife.get(key);
				((Robot) eCourant).setLife(robotLife.getSecond());
				i++;
			}
		}

		i = 0;
		for (Iterator<Entity> entityIterator = eastEntityList.iterator(); entityIterator.hasNext();) {
			Entity eCourant = entityIterator.next();
			if (eCourant instanceof Robot) {
				Pair<Direction, Integer> key = new Pair<Direction, Integer>(Direction.EAST, i);
				Pair<Robot, Integer> robotLife = this.targetsLife.get(key);
				((Robot) eCourant).setLife(robotLife.getSecond());
				i++;
			}
		}
	}

	public void execute() throws NotDoableException {
		myAutomaton.execute(this);
	}
}
