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
import gui.GUIPlayer;
import gui.GUIRobot;
import personnages.Besace;
import personnages.Player;
import personnages.Robot;
import pickable.PickAble;
import reader.Reader;
import sequence._Sequence;

public class Engine {

	private List<Player> listPlayer;

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
		listPlayer = new ArrayList<Player>();
		try {

			listPlayer.add(new Player(new Base(Team.ROUGE), myMap, userInterface));
			listPlayer.add(new Player(new Base(Team.BLEU), myMap, userInterface));

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
		for (Player p : listPlayer) {
			if (p.getTeam().equals(team)) {
				return p;
			}
		}
		return null;
	}

	public List<Player> getPlayerList() {
		return listPlayer;
	}

	public PlayPhase getPlayPhase() {
		return this.playPhase;
	}

	public Map getMap() {
		return this.myMap;
	}

	/**
	 * 
	 * @param dir
	 *            The direction of the mouvement
	 * @param perso
	 *            The GUICharacter whos gonna do the mouvement
	 * @param map
	 *            The map with all the entity referenced
	 * @return True is the mouvement is possible, false else
	 */
	// TODO : Handle with the pickup of pickable when pass on a cell
	public boolean doMove(GUIPlayer perso, Direction dir, Map map) {
		boolean moveSucces = false;
		Player player = perso.getPlayer();
		if (this.playPhase.equals(PlayPhase.playerMovement)) {

			// Case of Player1
			if (player.getMovePoints() > 0) {
				switch (dir) {

				case SOUTH:
					if (map.isFree(player.getX(), player.getY() + 1)
							|| map.isPickAble(player.getX(), player.getY() + 1)) {

						player.setY(player.getY() + 1);
						// map.Free(player.getX(), player.getY() - 1);
						moveSucces = true;
					}
					break;

				case NORTH:
					if (map.isFree(player.getX(), player.getY() - 1)
							|| map.isPickAble(player.getX(), player.getY() - 1)) {
						player.setY(player.getY() - 1);
						// map.Free(player.getX(), player.getY() + 1);
						moveSucces = true;
					}
					break;

				case WEST:
					if (map.isFree(player.getX() - 1, player.getY())
							|| map.isPickAble(player.getX() - 1, player.getY())) {
						player.setX(player.getX() - 1);
						// map.Free(player.getX() + 1, player.getY());
						moveSucces = true;
					}
					break;

				case EAST:
					if (map.isFree(player.getX() + 1, player.getY())
							|| map.isPickAble(player.getX() + 1, player.getY())) {
						player.setX(player.getX() + 1);
						// map.Free(player.getX() - 1, player.getY());
						moveSucces = true;
					}

					break;
				}
			}
		}
		// If the player doesn't have MP
		else {
			return moveSucces;
		}

		// Gestion of the besace
		if (moveSucces) {

			Besace PlayerBesace = player.getBesace();

			for (Entity e : map.getPickAbleList(player.getX(), player.getY())) {
				PlayerBesace.add(((PickAble) e).getClass());
			}

			// map.setEntity(player.getX(), player.getY(), player);
			player.setMovePoints(player.getMovePoints() - 1);
			setPlayPhase(0);
		}
		return moveSucces;

	}

	// TODO : Handle attackpoints and death is lifepoint is 0
	public void doAttack(GUIPlayer perso, Direction dir, Map map) throws NotDoableException {
		Cell target;
		Player player = perso.getPlayer();
		personnages.Character opponent = null;
		try {
			if (player.getAttackPoints() > 0) {

				switch (dir) {

				case SOUTH:
					target = map.getCell(player.getX(), player.getY() + 1);
					opponent = target.getOpponent(player.getTeam());
					player.classicAtk(target);
					System.out.println(" Joueur 1 attaque la case : " + player.getX() + ";" + (player.getY() + 1));
					break;

				case NORTH:
					target = map.getCell(player.getX(), player.getY() - 1);
					opponent = target.getOpponent(player.getTeam());
					player.classicAtk(target);
					System.out.println(" Joueur 1 attaque la case : " + player.getX() + ";" + (player.getY() - 1));
					break;

				case WEST:
					target = map.getCell(player.getX() - 1, player.getY());
					opponent = target.getOpponent(player.getTeam());
					player.classicAtk(target);
					System.out.println(" Joueur 1 attaque la case : " + (player.getX() - 1) + ";" + player.getY());
					break;

				case EAST:
					target = map.getCell(player.getX() + 1, player.getY());
					opponent = target.getOpponent(player.getTeam());
					player.classicAtk(target);
					System.out.println(" Joueur 1 attaque la case : " + (player.getX() + 1) + ";" + player.getY());
					break;

				}
				player.setAttackPoints(player.getAttackPoints() - 1);

			} else {
				System.out.println("Plus de point d'attaque !\n");
			}

			if (opponent != null && opponent.getLife() <= 0) {
				// If the opponent hero die => End of game
				if (opponent.getClass().equals(Player.class)) {

				}
				// The opponent is a robot, remove him from the game
				else {
					Player opponentPlayer = getPlayer(opponent.getTeam());
					opponentPlayer.removeRobot((Robot) opponent);

					myMap.Free(opponent.getX(), opponent.getY());

				}
			}
			System.out.println("Point de vie du joueur 2 apres attaque : " + getPlayer(Team.BLEU).getLife());

		} catch (NotDoableException e) {
			throw new NotDoableException("Echec de l'attaque");
		}
	}

	public void createRobot(GUIPlayer GUIPlayer, GUI userInterface, Map map) {
		// TODO : création d'un robot
		int Xbase;
		int Ybase;
		Player player;

		player = GUIPlayer.getPlayer();
		Xbase = player.getBase().getX();
		Ybase = player.getBase().getY();
		try {
			if (map.isFree(Xbase, Ybase)) {

				Robot robot = new Robot(player.getBase(), myMap, userInterface,
						Reader.parse("(MC2E | (AC;(MC3N>MT8.3)))"), player);
				map.setEntity(Xbase, Ybase, robot);
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

	public void behaviorModif(GUIRobot GUIRobot, GUI userInterface, Map map) {
		Player player = getPlayer(GUIRobot.getTeam());
		Robot robot = GUIRobot.getRobot();
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
			listPlayer.get(0).setMovePoints(10);
		}
	}

	public void executeAutomaton(GUI userInterface) {
		// TODO : executer pour tout les robots des deux persoannges, ne pas
		// oublier le render a chaque mouvement afin d'éviter téléportation
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