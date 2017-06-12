package moteurDuJeu;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import carte.Base;
import carte.Cell;
import carte.Map;
import entite.Direction;
import entite.Team;
import exceptions.NotDoableException;
import gui.GUI;
import personnages.Character;
import personnages.Player;
import personnages.Robot;
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
		myMap = new Map();
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
		player.goTo(dir, 1);
	}

	public void goTo(Character player, Direction dir, int lg) {
		player.goTo(dir, lg);
	}

	// TODO : Handle attackpoints and death is lifepoint is 0
	public void classicAtk(Character character, Cell target) {
		character.classicAtk(target);
	}

	public void classicAtk(Character character, Direction dir) throws NotDoableException {
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

	}

	public void createRobot(Player player, GUI userInterface) {
		// TODO : création d'un robot
		int Xbase;
		int Ybase;
		Xbase = player.getBase().getX();
		Ybase = player.getBase().getY();
		try {
			if (getMap().isFree(Xbase, Ybase)) {

				Robot robot = new Robot(player.getBase(), myMap, userInterface,
						Reader.parse("(MC2E | (AC;(MC3N>MT8.3)))"), player);
				getMap().setEntity(Xbase, Ybase, robot);
			} else {
				// TODO find the player's base nearest free cell

			}
		} catch (SlickException e) {
			e.getMessage();
		} catch (Exception e) {

			e.getMessage();
		}
	}

	public Player getCurrentModifier() {
		return this.currentModifier;
	}

	public void setCurrentModifier(Player currentPlayer) {
		this.currentModifier = currentPlayer;
	}

	public void behaviorModif(Robot robot, GUI userInterface) {
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

	/**
	 * Is called right when mouse is pressed
	 * 
	 * @param button
	 *            The index of the button (Input.'index')
	 * @param mouseXCell
	 *            X coordinate of the clicked tile
	 * @param mouseYCell
	 *            Y coordinate of the clicked tile
	 */

}