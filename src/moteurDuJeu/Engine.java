package moteurDuJeu;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.SlickException;

import carte.Base;
import carte.Cell;
import carte.Map;
import entite.Direction;
import entite.Entity;
import entite.Team;
import exceptions.NotDoableException;
import gui.GUI;
import gui.GUICharacter;
import personnages.Character;
import personnages.Player;
import personnages.Robot;
import personnages.State;
import sequence._Sequence;

public class Engine {

	private List<Player> playerList;

	private Map myMap;

	private PlayPhase playPhase;

	private Player currentModifier;

	private Robot currentModified;

	private boolean isCreating;

	private boolean isModifying;

	/**
	 * Create an Engine Object An Engine has a map and a list of its players
	 * 
	 * @throws SlickException
	 */
	public Engine(GUI userInterface) throws SlickException {
		myMap = new Map(userInterface);
		playerList = new ArrayList<Player>();
		try {
			playerList.add(new Player(new Base(Team.ROUGE), myMap, userInterface));
			playerList.add(new Player(new Base(Team.BLEU), myMap, userInterface));

		} catch (Exception e) {
			e.printStackTrace();
		}
		myMap.init(userInterface, this);
		this.playPhase = PlayPhase.playerMovement;
	}

	/**
	 * 
	 * @param team
	 *            The team to search
	 * @return the player who match the team
	 */
	public Player getPlayer(Team team) {
		for (Player p : playerList) {
			if (p.getTeam().equals(team)) {
				return p;
			}
		}
		return null;
	}

	public List<Player> getPlayerList() {
		return playerList;
	}

	public PlayPhase getPlayPhase() {
		return this.playPhase;
	}

	public Map getMap() {
		return this.myMap;
	}

	public void goTo(Character player, Direction dir) {

		if (player.getState().equals(State.Wait) && this.playPhase.equals(PlayPhase.playerMovement)) {
			player.goTo(dir, 1);
		} else {
			System.out.println("Pas de panique t'as pas fini de bouger");
		}
		setNewPlayPhase();
	}

	public void goTo(Character player, Direction dir, int lg) {
		if (player.getState().equals(State.Wait) && this.playPhase.equals(PlayPhase.playerMovement)) {
			player.goTo(dir, lg);
		} else {
			System.out.println("Pas de panique t'as pas fini de bouger");
		}
		setNewPlayPhase();
	}

	public void classicAtk(Character character, Cell target) {

		if (character.getState().equals(State.Wait) && this.playPhase.equals(PlayPhase.playerMovement)) {
			character.classicAtk(target);
			character.getMyselfGUI().setActionRequest(true);
		} else {
			System.out.println("Pas de panique t'as pas fini d'attaquer");
		}
	}

	public void classicAtk(Character character, Direction dir) throws NotDoableException {
		if (character.getState().equals(State.Wait) && this.playPhase.equals(PlayPhase.playerMovement)) {
			Cell target = null;
			switch (dir) {
			case NORTH:
				target = getMap().getCell(character.getX(), character.getY() - 1);
				break;
			case SOUTH:
				target = getMap().getCell(character.getX(), character.getY() + 1);
				break;
			case EAST:
				target = getMap().getCell(character.getX() + 1, character.getY());
				break;
			case WEST:
				target = getMap().getCell(character.getX() - 1, character.getY());
				break;
			}
			character.setDirection(dir);
			character.classicAtk(target);
			character.getMyselfGUI().setActionRequest(true);
		} else {
			System.out.println("Pas de panique t'as pas fini d'attaquer");
		}

	}

	private void createRobot(GUI userInterface, Player player, _Sequence sequence) {

		player.setState(State.RobotCreation);
		player.getMyselfGUI().setActionRequest(true);
		int Xbase;
		int Ybase;
		Xbase = player.getBase().getX();
		Ybase = player.getBase().getY();
		if (!getMap().getCell(Xbase, Ybase).isReachable()) {
			Cell freeCell = getMap().nearestFreeCell(Xbase, Ybase);
			Xbase = freeCell.getX();
			Ybase = freeCell.getY();

		}
		// TODO recode this method oklm
		Robot robot = new Robot(Xbase, Ybase, myMap, userInterface, sequence, player);
		player.setState(State.Wait);
		robot.pickUp();
	}

	public void setPlayPhase(PlayPhase playPhase) {
		this.playPhase = playPhase;
	}

	public void setNewPlayPhase() {
		boolean movePointRemaining = true;
		for (Player p : this.playerList) {
			movePointRemaining = true;
			if (p.getMovePoints() <= 0) {
				movePointRemaining = false;
			}
		}
		if (movePointRemaining == false) {
			setPlayPhase(PlayPhase.behaviorModification);
			for (Player p : this.playerList) {
				p.setMovePoints(25);
			}
		}
	}

	public void executeAllRobot() {
		if (getPlayPhase().equals(PlayPhase.automatonExecution)) {
			int myRandom;
			List<Robot> executionRobotList = new LinkedList<Robot>();
			// get each players
			for (Player player : playerList) {
				executionRobotList.addAll(player.getRobotList());
			}
			// for (Iterator<Robot> iterator = executionRobotList.iterator();
			// iterator.hasNext();) {
			// for (int i =0 ; i < executionRobotList.size();i++){
			while (executionRobotList.size() != 0) {
				myRandom = (int) (Math.random() * executionRobotList.size());
				// Robot currentRobot = iterator.next();
				Robot currentRobot = executionRobotList.get(myRandom);
				try {
					currentRobot.execute();
				} catch (NotDoableException e) {
					// Do nothing if you can't execute this robot
				}
				executionRobotList.remove(currentRobot);
			}
			this.setPlayPhase(PlayPhase.playerMovement);
		}
	}

	public void remove(Player player) {
		this.playerList.remove(player);
		if (currentModifier.equals(player)) {
			currentModifier = null;
		}
	}

	public GUICharacter getGUICharactereFromMouse(int x, int y) {

		for (Entity currentEntity : this.getMap().getCell(x, y).getEntityList()) {
			if (currentEntity instanceof Character) {
				return ((Character) currentEntity).getMyselfGUI();
			}
		}
		return null;
	}

	public void behaviorCreation(GUI userInterface, Player player) {
		if (player.getState().equals(State.Wait) && this.playPhase.equals(PlayPhase.behaviorModification)) {
			this.isModifying = false;
			this.isCreating = true;
			this.currentModifier = player;
			userInterface.inputRequest();
		} else {
			System.out.println("Pas la phase de création de robot");
		}
	}

	public void behaviorModification(GUI userInterface, Robot robot) {
		if (robot.getState().equals(State.Wait) && this.playPhase.equals(PlayPhase.behaviorModification)) {
			this.isModifying = true;
			this.isCreating = false;
			this.currentModified = robot;
			this.currentModifier = robot.getPlayer();
			userInterface.inputRequest();
		} else {
			System.out.println("Pas la phase de création de robot");
		}
	}

	public void setRobotBehavior(GUI userInterface, _Sequence sequence) {
		if (isModifying && !isCreating) {
			this.modifyRobot(currentModified, sequence);
		} else if (!isModifying && isCreating) {
			this.createRobot(userInterface, this.currentModifier, sequence);
		}
		currentModifier.getBesace().remove(sequence);
		currentModified = null;
		currentModifier = null;
		this.isModifying = false;
		this.isCreating = false;
	}

	private void modifyRobot(Robot currentModified, _Sequence sequence) {
		currentModifier.setState(State.RobotCreation);
		currentModified.setAutomaton(sequence);
	}

	public Player getCurrentModifier() {
		return this.currentModifier;
	}
}