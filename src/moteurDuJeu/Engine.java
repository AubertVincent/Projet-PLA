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

	private int nbrOperatorInitOnMap = 1;
	private int nbrOperatorInGame = nbrOperatorInitOnMap;

	private Team winners = null;

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

	public List<Player> getPlayerList() {
		return playerList;
	}

	public PlayPhase getPlayPhase() {
		return this.playPhase;
	}

	public Map getMap() {
		return this.myMap;
	}

	public int getNbrOperatorInitOnMap() {
		return nbrOperatorInitOnMap;
	}

	public void goTo(Character player, Direction dir) {

		if (player.getState().equals(State.Wait) && this.playPhase.equals(PlayPhase.playerMovement)) {
			player.goTo(dir, 1);
		} else {
			System.out.println("Pas de panique t'as pas fini de bouger");
		}
	}

	public void goTo(Character player, Direction dir, int lg) {
		if (player.getState().equals(State.Wait) && this.playPhase.equals(PlayPhase.playerMovement)) {
			player.goTo(dir, lg);
		} else {
			System.out.println("Pas de panique t'as pas fini de bouger");
		}
	}

	public void classicAtk(Character character, Cell target) {

		if (character.getState().equals(State.Wait) && this.playPhase.equals(PlayPhase.automatonExecution)) {
			character.classicAtk(target);
		} else {
			System.out.println("Pas de panique t'as pas fini d'attaquer");
		}
	}

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

	public void setPlayPhase(PlayPhase playPhase) {
		this.playPhase = playPhase;
	}

	public void remove(Player player) {
		this.playerList.remove(player);

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
			// Put the robot's sequence in the besace before the change
			robot.getPlayer().getBesace().add(robot.getAutomaton());
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
		try {
			currentModified.getAutomatonInList().clear();
			currentModified.fillActionList();
		} catch (NotDoableException e) {
			e.getMessage();
		}
	}

	public Player getCurrentModifier() {
		return this.currentModifier;
	}

	public void step() {
		if (getPlayPhase().equals(PlayPhase.automatonExecution)) {
			int randomIndex;
			boolean allEmpty = true;
			List<Robot> executedRobotList = new LinkedList<Robot>();
			// get each players
			for (Player player : playerList) {
				executedRobotList.addAll(player.getRobotList());
			}
			while (executedRobotList.size() != 0) {

				randomIndex = (int) (Math.random() * executedRobotList.size());
				Robot currentRobot = executedRobotList.get(randomIndex);
				try {
					if (!currentRobot.getAutomatonInList().isEmpty()
							&& currentRobot.getCurrentAction() < currentRobot.getAutomatonInList().size()) {
						allEmpty = false;
						currentRobot.getAutomatonInList().get(currentRobot.getCurrentAction()).execute(currentRobot);
					}
				} catch (NotDoableException e) {
					currentRobot.getAutomatonInList().clear();
					e.getMessage();
				}
				currentRobot.setNextAction();
				executedRobotList.remove(currentRobot);
			}
			if (allEmpty) {
				this.setPlayPhase(PlayPhase.playerMovement);
				this.resetAllCharacter();

			}
		}
	}

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

	public boolean isEndOfGame() {
		boolean isAllPickedByOnePlayer = false;

		for (Player player : playerList) {
			isAllPickedByOnePlayer = isAllPickedByOnePlayer || player.numberOfOwnedPickAble() == nbrOperatorInGame;
		}

		if (isAllPickedByOnePlayer) {
			for (Player player : playerList) {
				if (player.numberOfOwnedPickAble() == nbrOperatorInGame) {
					this.setPlayPhase(PlayPhase.endOfGame);
					this.winners = player.getTeam();
				}
			}
		}
		return isAllPickedByOnePlayer;
	}

	public Team getWinningTeam() {
		return winners;
	}

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