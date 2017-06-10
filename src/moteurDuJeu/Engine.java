package moteurDuJeu;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.SlickException;

import carte.Base;
import carte.Cell;
import carte.Map;
import entite.Direction;
import entite.Team;
import exceptions.NotDoableException;
import gui.GUI;
import gui.GUICharacter;
import personnages.Besace;
import personnages.Player;

public class Engine {

	private List<Player> listPlayer;
	// private Player player1;
	// private Player player2;
	public Map ma_map;

	private PlayPhase playPhase;

	/**
	 * Create an Engine Object An Engine has a map and a list of its players
	 * 
	 * @throws SlickException
	 */
	public Engine(GUI userInterface) throws SlickException {
		ma_map = new Map();

		listPlayer = new ArrayList<Player>();
		listPlayer.add(new Player(2, 4, ma_map, new Besace(), Direction.SOUTH, 1, 1, 1, 1, 10, 1, 1, Team.ROUGE,
				new Base(2, 4, Team.ROUGE), userInterface.getGUICharacterFromTeam(Team.ROUGE)));
		listPlayer.add(new Player(31, 15, ma_map, new Besace(), Direction.SOUTH, 1, 1, 1, 1, 10, 1, 1, Team.BLEU,
				new Base(31, 15, Team.BLEU), userInterface.getGUICharacterFromTeam(Team.BLEU)));
		userInterface.getGUICharacterFromTeam(Team.ROUGE).setPlayer(this.getPlayer(Team.ROUGE));
		userInterface.getGUICharacterFromTeam(Team.BLEU).setPlayer(this.getPlayer(Team.BLEU));
		ma_map.init(userInterface, this);
	}

	/**
	 * 
	 * @param team
	 *            The team to search
	 * @return the player who match the team
	 */
	public Player getPlayer(Team team) {
		for (Player p : listPlayer) {
			if (p.getTeam() == team) {
				return p;
			}
		}
		return null;
	}

	public List<Player> getPlayerList() {
		return listPlayer;
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
	// TODO : Handle no more movePoint
	public boolean doMove(Direction dir, GUICharacter perso, Map map) {

		boolean moveSucces = false;
		Player player;
		// Case of Player1
		if (perso.getTeam().equals(Team.ROUGE)) {
			player = getPlayer(Team.ROUGE);
			if (player.getMovePoints() > 0) {
				switch (dir) {

				case SOUTH:
					if (map.isFree(player.getX(), player.getY() + 1)) {

						player.setY(player.getY() + 1);
						map.Free(player.getX(), player.getY() - 1);
						moveSucces = true;
					}
					break;

				case NORTH:
					if (map.isFree(player.getX(), player.getY() - 1)) {
						player.setY(player.getY() - 1);
						map.Free(player.getX(), player.getY() + 1);
						moveSucces = true;
					}
					break;

				case WEST:
					if (map.isFree(player.getX() - 1, player.getY())) {
						player.setX(player.getX() - 1);
						map.Free(player.getX() + 1, player.getY());
						moveSucces = true;
					}
					break;

				case EAST:
					if (map.isFree(player.getX() + 1, player.getY())) {
						player.setX(player.getX() + 1);
						map.Free(player.getX() - 1, player.getY());
						moveSucces = true;
					}

					break;

				}
			}

		}
		// Case of Player2
		else if (perso.getTeam().equals(Team.BLEU)) {
			player = getPlayer(Team.BLEU);
			if (player.getMovePoints() > 0) {
				switch (dir) {

				case SOUTH:
					if (map.isFree(player.getX(), player.getY() + 1)) {
						player.setY(player.getY() + 1);
						map.Free(player.getX(), player.getY() - 1);
						moveSucces = true;
					}
					break;

				case NORTH:
					if (map.isFree(player.getX(), player.getY() - 1)) {
						player.setY(player.getY() - 1);
						map.Free(player.getX(), player.getY() + 1);
						moveSucces = true;
					}
					break;

				case WEST:
					if (map.isFree(player.getX() - 1, player.getY())) {
						player.setX(player.getX() - 1);
						map.Free(player.getX() + 1, player.getY());
						moveSucces = true;
					}
					break;

				case EAST:

					if (map.isFree(player.getX() + 1, player.getY())) {
						player.setX(player.getX() + 1);
						map.Free(player.getX() - 1, player.getY());
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

		if (moveSucces) {
			map.setEntity(player.getX(), player.getY(), player);
			player.setMovePoints(player.getMovePoints() - 1);
		}
		return moveSucces;
	}

	// TODO : Handle attackpoints and death is lifepoint is 0
	public void doAttack(Direction dir, GUICharacter perso, Map map) throws NotDoableException {
		Cell target;
		Player player;
		try {
			if (perso.getTeam().equals(Team.ROUGE)) {
				player = getPlayer(Team.ROUGE);
				switch (dir) {

				case SOUTH:
					target = map.getCell(player.getX(), player.getY() + 1);
					player.classicAtk(target);
					System.out.println(" Joueur 1 attaque la case : " + player.getX() + ";" + (player.getY() + 1));
					break;

				case NORTH:
					target = map.getCell(player.getX(), player.getY() - 1);
					player.classicAtk(target);
					System.out.println(" Joueur 1 attaque la case : " + player.getX() + ";" + (player.getY() - 1));
					break;

				case WEST:
					target = map.getCell(player.getX() - 1, player.getY());
					player.classicAtk(target);
					System.out.println(" Joueur 1 attaque la case : " + (player.getX() - 1) + ";" + player.getY());
					break;

				case EAST:
					target = map.getCell(player.getX() + 1, player.getY());
					player.classicAtk(target);
					System.out.println(" Joueur 1 attaque la case : " + (player.getX() + 1) + ";" + player.getY());
					break;

				}
				player.setAttackPoints(player.getAttackPoints() - 1);

			} else if (perso.getTeam().equals(Team.BLEU)) {
				player = getPlayer(Team.BLEU);
				switch (dir) {

				case SOUTH:
					target = map.getCell(player.getX(), player.getY() + 1);
					player.classicAtk(target);
					System.out.println(" Joueur 2 attaque la case : " + player.getX() + ";" + (player.getY() + 1));
					break;

				case NORTH:
					target = map.getCell(player.getX(), player.getY() - 1);
					player.classicAtk(target);
					System.out.println(" Joueur 2 attaque la case : " + player.getX() + ";" + (player.getY() - 1));
					break;

				case WEST:
					target = map.getCell(player.getX() - 1, player.getY());
					player.classicAtk(target);
					System.out.println(" Joueur 2 attaque la case : " + (player.getX() - 1) + ";" + player.getY());
					break;

				case EAST:
					target = map.getCell(player.getX() + 1, player.getY());
					player.classicAtk(target);
					System.out.println(" Joueur 2 attaque la case : " + (player.getX() + 1) + ";" + player.getY());
					break;

				}
				player.setAttackPoints(player.getAttackPoints() - 1);

			} else {
				System.out.println("Plus de point d'attaque !\n");
			}

			System.out.println("Point de vie du joueur 2 apres attaque : " + getPlayer(Team.BLEU).getLife());

		} catch (NotDoableException e) {
			throw new NotDoableException("Echec de l'attaque");
		}
	}

	// public void update(Direction dir, GUICharacter perso, int joueur, Map
	// map, GUI guy) {
	//
	// // Allow to do the move for 5 MP
	// doMove(dir, perso, map);
	//
	// // Set to 5 the MP if all has been consume
	// if (this.EndGame == true) {
	// nbrRound++;
	// switch (joueur) {
	// case 1:
	// player1.setMovePoints(5);
	// break;
	// case 2:
	// player2.setMovePoints(5);
	// break;
	// }
	// }
	//
	// // TODO Creation of robot
	// }

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
	public void mousePressed(int button, int mouseXCell, int mouseYCell) {
		// TODO A vous de jouer -> decider que faire lors d'un clic de souris

		// @Conseil :
		if (playPhase == PlayPhase.behaviorModification) {
			// Gestion clic
		}
	}

	// private boolean RoundRobot(Player player1, Player player2) {
	// for (Robot r : player2.getListRobot()) {
	// r.execute();
	// }
	// for (Robot r : player1.getListRobot()) {
	// r.execute();
	// }
	// if (player1.getLife() == 0 || player2.getLife() == 0) {
	// return true;
	// } else {
	// return false;
	// }
	//
	// }

}