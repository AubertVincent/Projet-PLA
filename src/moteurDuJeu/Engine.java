package moteurDuJeu;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.SlickException;

import carte.Map;
import entite.Direction;
import entite.Team;
import gui.GUI;
import gui.GUICharacter;
import personnages.Player;

public class Engine {

	private List<Player> listPlayer;
	// private Player player1;
	// private Player player2;
	public Map ma_map;

	private int nbrRound;
	private boolean EndGame;

	/**
	 * Create an Engine Object, allow us to update all the entitys
	 * 
	 * @throws SlickException
	 */

	public Engine(GUI guy) throws SlickException {
		listPlayer = new ArrayList<Player>();
		listPlayer.add(new Player(2, 4, ma_map, Direction.SOUTH, 1, 1, 1, 1, 500, 1, Team.ROUGE));
		listPlayer.add(new Player(31, 15, ma_map, Direction.SOUTH, 1, 1, 1, 1, 500, 1, Team.BLEU));
		nbrRound = 0;
		EndGame = false;
		ma_map = new Map();
		ma_map.initMap(guy);
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
		// Case of Player1
		if (perso.getTeam() == Team.ROUGE) {
			Player player = getPlayer(Team.ROUGE);
			switch (dir) {

			case SOUTH:
				if (map.isFree(player.getX(), player.getY() + 1)) {

					player.setY(player.getY() + 1);
					moveSucces = true;
				}
				break;

			case NORTH:
				if (map.isFree(player.getX(), player.getY() - 1)) {
					System.out.println("je suis la");
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
			System.out.println("Move Succes : ? "+ moveSucces);
			if (moveSucces) {
				map.Add(player.getX(), player.getY(), player);
				player.setMovePoints(player.getMovePoints() - 1);
			}
			return moveSucces;
		}
		// Case of Player2
		else if (perso.getTeam() == Team.BLEU) {
			Player player = getPlayer(Team.BLEU);
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
			System.out.println("Move Succes : ? "+ moveSucces);
			if (moveSucces) {
				map.Add(player.getX(), player.getY(), player);
				player.setMovePoints(player.getMovePoints() - 1);
			}
			return moveSucces;
		}

		// If the player doesn't have MP
		else {
			return moveSucces;
		}


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