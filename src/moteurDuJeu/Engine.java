package moteurDuJeu;

import java.util.ArrayList;
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

	// TODO : Handle with the pickup of pickable when pass on a cell
	public void goTo(Character player, Direction dir) {

		if (player.getState().equals(State.Wait) && this.playPhase.equals(PlayPhase.playerMovement)) {
			player.goTo(dir, 1);
		} else {
			System.out.println("Pas de panique t'as pas fini de bouger");
		}
	}

	public void goTo(Character player, Direction dir, int lg) {
		if (player.getState().equals(State.Wait)) {
			player.goTo(dir, lg);
		} else {
			System.out.println("Pas de panique t'as pas fini de bouger");
		}
	}

	// TODO : Handle attackpoints and death is lifepoint is 0
	public void classicAtk(Character character, Cell target) {

		if (character.getState().equals(State.Wait)) {
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

			character.classicAtk(target);
			character.getMyselfGUI().setActionRequest(true);
		} else {
			System.out.println("Pas de panique t'as pas fini d'attaquer");
		}

	}

	private void createRobot(GUI userInterface, Player player, _Sequence sequence) {
		if (player.getState()
				.equals(State.Wait)/*
									 * && this.playPhase.equals(PlayPhase.
									 * behaviorModification)
									 */) {
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
		} else {
			System.out.println("Pas la phase de création de robot");
		}

	}

	public void setPlayPhase(PlayPhase playPhase) {
		this.playPhase = playPhase;
	}

	public void executeAutomaton(GUI userInterface) {
		// TODO : executer pour tout les robots des deux persoannges, ne pas
		// oublier le render a chaque mouvement afin d'éviter téléportation
		try {
			throw new Exception("NYI");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public GUICharacter getGUICharactereFromMouse(int x, int y) {

		for (Entity currentEntity : this.getMap().getCell(x, y).getListEntity()) {
			if (currentEntity instanceof Character) {
				return ((Character) currentEntity).getMyselfGUI();
			}
		}
		return null;
	}

	public void behaviorCreation(GUI userInterface, Player player) {
		this.isModifying = false;
		this.isCreating = true;
		this.currentModifier = player;
		userInterface.inputRequest();
	}

	public void behaviorModification(GUI userInterface, Robot robot) {
		this.isModifying = true;
		this.isCreating = false;
		this.currentModified = robot;
		this.currentModifier = robot.getPlayer();
		userInterface.inputRequest();
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
		currentModified.setAutomaton(sequence);
	}

	public Player getCurrentModifier() {
		return this.currentModifier;
	}
}