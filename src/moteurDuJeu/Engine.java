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

	private int nbrOperatorInitOnMap = 10;

	private int nbrOperatorInGame = nbrOperatorInitOnMap;

	private Team winners = null;

	/**
	 * Create the model of the game, with the creation of the players (2 here)
	 * Initialize the map witch is the memory representation of the game
	 * 
	 * @throws SlickException
	 */
	public Engine(GUI userInterface) throws SlickException {
		myMap = new Map(userInterface);
		playerList = new ArrayList<Player>();
		try {
			playerList.add(new Player(new Base(Team.ROUGE), myMap, userInterface));
			playerList.add(new Player(new Base(Team.BLEU), myMap, userInterface));

			for (Player player : playerList) {
				nbrOperatorInGame += player.numberOfOwnedPickAble();
			}
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

	/**
	 * Used to get the playerList of the game
	 * 
	 * @return the playerList
	 */
	public List<Player> getPlayerList() {
		return playerList;
	}

	/**
	 * Used to get the current playPhase of the game
	 * 
	 * @return the current playPhase
	 */
	public PlayPhase getPlayPhase() {
		return this.playPhase;
	}

	/**
	 * Used to get the game's memory representation
	 * 
	 * @return the game's memory representation
	 */
	public Map getMap() {
		return this.myMap;
	}

	/**
	 * Used to get the initial number of Operator on the game
	 * 
	 * @return the initial number of operator
	 */
	public int getNbrOperatorInitOnMap() {
		return nbrOperatorInitOnMap;
	}

	/**
	 * Used to move a player, use the direction of the movement
	 * 
	 * @param player
	 *            the player to move
	 * @param dir
	 *            the direction where the player will be moved
	 */
	public void goTo(Character player, Direction dir) {

		if (player.getState().equals(State.Wait) && this.playPhase.equals(PlayPhase.playerMovement)) {
			player.goTo(dir, 1);
		} else {
			System.out.println("Pas de panique t'as pas fini de bouger");
		}
	}

	/**
	 * Used to move a player, use the direction of the movement and the length
	 * of the movement
	 * 
	 * @param player
	 *            the player to move
	 * @param dir
	 *            the direction where the player will be moved
	 * @param lg
	 *            the length of the movement
	 * 
	 */
	public void goTo(Character player, Direction dir, int lg) {
		if (player.getState().equals(State.Wait) && this.playPhase.equals(PlayPhase.playerMovement)) {
			player.goTo(dir, lg);
		} else {
			System.out.println("Pas de panique t'as pas fini de bouger");
		}
	}

	/**
	 * Used to do a classic attack to the target cell
	 * 
	 * @param character
	 *            the character who's gonna attack
	 * @param target
	 *            the attack's target
	 */
	public void classicAtk(Character character, Cell target) {

		if (character.getState().equals(State.Wait) && this.playPhase.equals(PlayPhase.automatonExecution)) {
			character.classicAtk(target);
		} else {
			System.out.println("Pas de panique t'as pas fini d'attaquer");
		}
	}

	/**
	 * Used to do a classic attack to the direction give
	 * 
	 * @param character
	 *            the character who's gonna attack
	 * @param dir
	 *            the target of the attack
	 */
	public void classicAtk(Character character, Direction dir) {
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

		} else {
			System.out.println("Pas de panique t'as pas fini d'attaquer");
		}

	}

	/**
	 * Used to create a new robot, initial it and put him in memory
	 * 
	 * @param userInterface
	 *            the GUI interface
	 * @param player
	 *            the player who create a new robot
	 * @param sequence
	 *            the robot's automaton
	 */
	private void createRobot(GUI userInterface, Player player, _Sequence sequence) {

		if (currentModifier.getState().equals(State.Wait) && this.playPhase.equals(PlayPhase.behaviorModification)) {
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
			Robot robot = new Robot(Xbase, Ybase, myMap, userInterface, sequence, player);
			robot.pickUp();
		}
	}

	/**
	 * used to set a new playPhase during the game
	 * 
	 * @param playPhase
	 *            the new playPhase of the game
	 */
	public void setPlayPhase(PlayPhase playPhase) {
		this.playPhase = playPhase;
	}

	/**
	 * Used to remove a player of the game's playerList
	 * 
	 * @param player
	 *            the player to remove
	 */
	public void remove(Player player) {
		this.playerList.remove(player);

	}

	/**
	 * Used to get the character on the cell which has been clicked
	 * 
	 * @param x
	 *            the X coordinates of the cell
	 * @param y
	 *            the Y coordinates of the cell
	 * @return the character clicked
	 */
	public GUICharacter getGUICharactereFromMouse(int x, int y) {

		for (Entity currentEntity : this.getMap().getCell(x, y).getEntityList()) {
			if (currentEntity instanceof Character) {
				return ((Character) currentEntity).getMyselfGUI();
			}
		}
		return null;
	}

	/**
	 * Used to decide if the player want to create a robot or modify its
	 * behavior
	 * 
	 * @param userInterface
	 *            the GUI interface
	 * @param player
	 *            the player who want to create a robot
	 */
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

	/**
	 * Used to decide if the player want to create a robot or modify its
	 * behavior
	 * 
	 * @param userInterface
	 *            the GUI interface
	 * @param robot
	 *            the robot whose its behavior gonna be modified
	 */
	public void behaviorModification(GUI userInterface, Robot robot) {
		if (robot.getState().equals(State.Wait) && this.playPhase.equals(PlayPhase.behaviorModification)) {
			this.isModifying = true;
			this.isCreating = false;
			this.currentModified = robot;
			// Put the robot's sequence in the besace before the change
			robot.getPlayer().getBesace().add(robot.getAutomaton());
			this.currentModifier = robot.getPlayer();
			userInterface.inputRequest();
		} else {
			System.out.println("Pas la phase de création de robot");
		}
	}

	/**
	 * Do either a robot creation or a robot modification
	 * 
	 * @param userInterface
	 *            the GUI interface
	 * @param sequence
	 *            the Automaton which is gonna be given to either the new robot
	 *            or the existing robot
	 */
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

	/**
	 * Used to set a new behavior to an already existing robot
	 * 
	 * @param currentModified
	 *            the robot who's gonna be modified
	 * @param sequence
	 *            the new robot's automaton
	 */
	private void modifyRobot(Robot currentModified, _Sequence sequence) {
		currentModifier.setState(State.RobotCreation);
		currentModifier.getMyselfGUI().setActionRequest(true);
		currentModified.setAutomaton(sequence);
		try {
			currentModified.getAutomatonInList().clear();
			currentModified.fillActionList();
		} catch (NotDoableException e) {
			e.getMessage();
		}
	}

	/**
	 * Used to get the current player who modify his robot behavior
	 * 
	 * @return the current player
	 */
	public Player getCurrentModifier() {
		return this.currentModifier;
	}

	/**
	 * Used to do a step in the robots automaton execution, called while at
	 * least one robot has an action to do
	 */
	public void step() {
		if (getPlayPhase().equals(PlayPhase.automatonExecution)) {
			int randomIndex;
			boolean allEmpty = true;
			// Temporary list to do the execution
			List<Robot> executedRobotList = new LinkedList<Robot>();
			// get each players
			// add each player robotList to the temporary list
			for (Player player : playerList) {
				executedRobotList.addAll(player.getRobotList());
			}
			while (executedRobotList.size() != 0) {
				// Get a robot robot in the temporary list
				randomIndex = (int) (Math.random() * executedRobotList.size());
				Robot currentRobot = executedRobotList.get(randomIndex);
				try {
					// If the current robot still have action to do
					if (!currentRobot.getAutomatonInList().isEmpty()
							&& currentRobot.getCurrentAction() < currentRobot.getAutomatonInList().size()) {
						allEmpty = false;
						currentRobot.getAutomatonInList().get(currentRobot.getCurrentAction()).execute(currentRobot);
					}
				} catch (NotDoableException e) {
					currentRobot.getAutomatonInList().clear();
					e.getMessage();
				}
				// In order to execute the next action at the next step
				currentRobot.setNextAction();
				executedRobotList.remove(currentRobot);
			}
			if (allEmpty) {
				this.setPlayPhase(PlayPhase.playerMovement);
				this.resetAllCharacter();

			}
		}
	}

	/**
	 * Used to reset all character attributes, call at each new round
	 */
	public void resetAllCharacter() {
		for (Player player : playerList) {
			player.resetAttributes();
			for (Robot robot : player.getRobotList()) {
				try {
					robot.getAutomatonInList().clear();
					robot.fillActionList();
					robot.setFirstAction();
					robot.resetAttributes();
				} catch (NotDoableException e) {
					e.getMessage();
				}
			}
		}
	}

	/**
	 * Used to check if the game is either over or not A game is finish if one
	 * of the player has collected all the game's operator
	 * 
	 * @return current state == End of game
	 */
	public boolean isEndOfGame() {
		boolean isAllPickedByOnePlayer = false;

		for (Player player : playerList) {
			// check if a platyer has all the operator
			isAllPickedByOnePlayer = isAllPickedByOnePlayer || (player.numberOfOwnedPickAble() >= nbrOperatorInGame);
		}

		if (isAllPickedByOnePlayer) {
			for (Player player : playerList) {
				if (player.numberOfOwnedPickAble() >= nbrOperatorInGame) {
					this.setPlayPhase(PlayPhase.endOfGame);
					// give the winning team
					this.winners = player.getTeam();
				}
			}
		}
		return isAllPickedByOnePlayer;
	}

	/**
	 * used to get the winning team
	 * 
	 * @return the winning team
	 */
	public Team getWinningTeam() {
		return winners;
	}

	/**
	 * Used to get if all the character of the map are in their own waiting
	 * state
	 * 
	 * @return true if all the characters are waiting
	 */
	public boolean everyoneWaiting() {
		boolean allWaiting = true;
		for (Player currentPlayer : this.getPlayerList()) {
			for (Robot currentRobot : currentPlayer.getRobotList()) {
				if (!currentRobot.getState().equals(State.Wait)) {
					allWaiting = false;
				}
			}
		}
		return allWaiting;
	}
}