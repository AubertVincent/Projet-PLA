package moteurDuJeu;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Input;
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
import reader.Reader;
import sequence._Sequence;

public class Engine {

	private List<Player> playerList;

	private Map myMap;

	private PlayPhase playPhase;

	private Player currentModifier;

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

		if (player.getState().equals(State.Wait)) {
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
		if (character.getState().equals(State.Wait)) {
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

	public void createRobot(GUI userInterface, Player player) {
		if (player.getState().equals(State.RobotCreation)) {
			int Xbase;
			int Ybase;
			Xbase = player.getBase().getX();
			Ybase = player.getBase().getY();
			if (!getMap().getCell(Xbase, Ybase).isReachable()) {
				Cell freeCell = getMap().nearestFreeCell(Xbase, Ybase);
				Xbase = freeCell.getX();
				Ybase = freeCell.getY();

			}
			new Robot(Xbase, Ybase, myMap, userInterface, Reader.parse("(MC2E | (AC;(MC3N>MT8.3)))"), player);
		} else {
			System.out.println("Pas la phase de création de robot");
		}

	}

	public Player getCurrentModifier() {
		return this.currentModifier;
	}

	public void setCurrentModifier(Player currentPlayer) {
		this.currentModifier = currentPlayer;
	}

	public void behaviorModif(GUI userInterface, Robot robot) {
		Player player = robot.getPlayer();
		// TODO : gestion du parseur
		// pour reccuperer la nouveau sequence
		_Sequence newAutomaton = Reader.parse("(MC2E)");
		robot.setAutomaton(newAutomaton);
	}

	public void setPlayPhase(int key) {
		if (getPlayer(Team.ROUGE).getMovePoints() == 0 && getPlayer(Team.BLEU).getMovePoints() == 0
				&& this.playPhase == PlayPhase.playerMovement) {
			this.playPhase = PlayPhase.behaviorModification;
		} else if (key == Input.KEY_SPACE) {
			// this.playPhase = PlayPhase.automatonExecution;
			this.playPhase = PlayPhase.playerMovement;
			playerList.get(0).setMovePoints(10);
		}
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
}