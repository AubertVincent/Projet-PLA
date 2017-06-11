package moteurDuJeu;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.SlickException;

import carte.Base;
import carte.Coordinates;
import carte.Map;
import entite.Direction;
import entite.Team;
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
	 * Create an Engine Object, allow us to update all the entitys
	 * 
	 * @throws SlickException
	 */

	public Engine(GUI userInterface) throws SlickException {
		Coordinates coordP1 = new Coordinates(2, 4);
		Coordinates coordP2 = new Coordinates(31, 15);
		ma_map = new Map();
		ma_map.init(userInterface);
		listPlayer = new ArrayList<Player>();
		listPlayer.add(new Player(coordP1, ma_map, new Besace(), Direction.SOUTH, 1, 1, 1, 1, 500, 1, 1, Team.ROUGE,
				new Base(coordP1, Team.ROUGE)));
		listPlayer.add(new Player(coordP2, ma_map, new Besace(), Direction.SOUTH, 1, 1, 1, 1, 500, 1, 1, Team.BLEU,
				new Base(coordP2, Team.BLEU)));

	}

	private Player getPlayer(Team team) {
		for (Player p : listPlayer) {
			if (p.getTeam() == team) {
				return p;
			}
		}
		return null;
	}

	public boolean doMove(Direction dir, GUICharacter perso, Map map) {

		boolean moveSucces = false;
		Coordinates coord;
		Player player;
		// Case of Player1
		if (perso.getTeam() == Team.ROUGE) {
			player = getPlayer(Team.ROUGE);
			coord = new Coordinates(player.getCoord());
			switch (dir) {

			case SOUTH:
				coord.setY(coord.getY() + 1);
				if (map.isFree(coord)) {
					player.getCoord().setY(coord.getY());
					coord.setY(coord.getY() - 1);
					map.Free(coord);
					moveSucces = true;
				}
				break;

			case NORTH:
				coord.setY(coord.getY() - 1);
				if (map.isFree(coord)) {
					player.getCoord().setY(coord.getY());
					coord.setY(coord.getY() + 1);
					map.Free(coord);
					moveSucces = true;
				}
				break;

			case WEST:
				coord.setX(coord.getX() - 1);
				if (map.isFree(coord)) {
					player.getCoord().setX(coord.getX());
					coord.setX(coord.getX() + 1);
					map.Free(coord);
					moveSucces = true;
				}
				break;

			case EAST:
				coord.setX(coord.getX() + 1);
				if (map.isFree(coord)) {
					player.getCoord().setX(coord.getX());
					coord.setX(coord.getX() - 1);
					map.Free(coord);
					moveSucces = true;
				}

				break;

			}

		}
		// Case of Player2
		else if (perso.getTeam() == Team.BLEU) {
			player = getPlayer(Team.BLEU);
			coord = new Coordinates(player.getCoord());
			switch (dir) {

			case SOUTH:
				coord.setY(coord.getY() + 1);
				if (map.isFree(coord)) {
					player.getCoord().setY(coord.getY());
					coord.setY(coord.getY() - 1);
					map.Free(coord);
					moveSucces = true;
				}
				break;

			case NORTH:
				coord.setY(coord.getY() - 1);
				if (map.isFree(coord)) {
					player.getCoord().setY(coord.getY());
					coord.setY(coord.getY() + 1);
					map.Free(coord);
					moveSucces = true;
				}
				break;

			case WEST:
				coord.setX(coord.getX() - 1);
				if (map.isFree(coord)) {
					player.getCoord().setX(coord.getX());
					coord.setX(coord.getX() + 1);
					map.Free(coord);
					moveSucces = true;
				}
				break;

			case EAST:
				coord.setX(coord.getX() + 1);
				if (map.isFree(coord)) {
					player.getCoord().setX(coord.getX());
					coord.setX(coord.getX() - 1);
					map.Free(coord);
					moveSucces = true;
				}

				break;

			}

		}

		// If the player doesn't have MP
		else {
			return moveSucces;
		}

		if (moveSucces) {
			map.setEntity(player.getCoord(), player);
			player.setMovePoints(player.getMovePoints() - 1);
		}
		return moveSucces;
	}

	// private void doAttack(Direction dir, GUICharacter perso, Map map) {
	// Cell target;
	// if (player1.getAttackPoints() > 0 && perso.getTeam() == 1) {
	//
	// switch (dir) {
	//
	// case SOUTH:
	// target = map.getCell(player1.getX(), player1.getY() + 1);
	// player1.classicAtk(target);
	// System.out.println(" Joueur 1 attaque la case : " + player1.getX() + ";"
	// + (player1.getY() + 1));
	// break;
	//
	// case NORTH:
	// target = map.getCell(player1.getX(), player1.getY() - 1);
	// player1.classicAtk(target);
	// System.out.println(" Joueur 1 attaque la case : " + player1.getX() + ";"
	// + (player1.getY() - 1));
	// break;
	//
	// case WEST:
	// target = map.getCell(player1.getX() - 1, player1.getY());
	// player1.classicAtk(target);
	// System.out.println(" Joueur 1 attaque la case : " + (player1.getX() - 1)
	// + ";" + player1.getY());
	// break;
	//
	// case EAST:
	// target = map.getCell(player1.getX() + 1, player1.getY());
	// player1.classicAtk(target);
	// System.out.println(" Joueur 1 attaque la case : " + (player1.getX() + 1)
	// + ";" + player1.getY());
	// break;
	//
	// }
	// player1.setAttackPoints(player1.getAttackPoints() - 1);
	//
	// } else if (player2.getAttackPoints() > 0 && perso.getTeam() == 2) {
	//
	// switch (dir) {
	//
	// case SOUTH:
	// target = map.getCell(player2.getX(), player2.getY() + 1);
	// player2.classicAtk(target);
	// System.out.println(" Joueur 2 attaque la case : " + player2.getX() + ";"
	// + (player2.getY() + 1));
	// break;
	//
	// case NORTH:
	// target = map.getCell(player2.getX(), player2.getY() - 1);
	// player2.classicAtk(target);
	// System.out.println(" Joueur 2 attaque la case : " + player2.getX() + ";"
	// + (player2.getY() - 1));
	// break;
	//
	// case WEST:
	// target = map.getCell(player2.getX() - 1, player2.getY());
	// player1.classicAtk(target);
	// System.out.println(" Joueur 2 attaque la case : " + (player2.getX() - 1)
	// + ";" + player2.getY());
	// break;
	//
	// case EAST:
	// target = map.getCell(player2.getX() + 1, player2.getY());
	// player1.classicAtk(target);
	// System.out.println(" Joueur 2 attaque la case : " + (player2.getX() + 1)
	// + ";" + player2.getY());
	// break;
	//
	// }
	// player2.setAttackPoints(player2.getAttackPoints() - 1);
	//
	// } else {
	// System.out.println("Plus de point d'attaque !\n");
	// this.EndGame = false;
	// }
	//
	// this.EndGame = true;
	// }

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