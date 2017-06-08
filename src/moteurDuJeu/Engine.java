package moteurDuJeu;

import org.newdawn.slick.SlickException;

import carte.Cell;
import carte.Map;
import entite.Direction;
import gui.GUI;
import gui.GUICharacter;
import personnages.Player;;

public class Engine {

	private Player player1;
	private Player player2;
	public Map ma_map;

	private int nbrRound;
	private boolean EndGame;

	/**
	 * Create an Engine Object, allow us to update all the entitys
	 * 
	 * @throws SlickException
	 */
	
	public Engine(GUI guy) throws SlickException {
<<<<<<< HEAD
		player1 = new Player(2, 4, Direction.SOUTH, 1, 1, 1, 1, 500, 1, 1);
		player2 = new Player(31, 15, Direction.SOUTH, 1, 1, 1, 1, 500, 1, 1);
=======
		player1 = new Player(2, 4, ma_map, Direction.SOUTH, 1, 1, 1, 1, 500, 1, 1);
		player2 = new Player(31, 15, ma_map, Direction.SOUTH, 1, 1, 1, 1, 500, 1, 2);
>>>>>>> GUI
		nbrRound = 0;
		EndGame = false;
		ma_map = new Map();
		ma_map.initMap(guy);
	}

	public void doMove(Direction dir, GUICharacter perso, Map map) {

		// Mise a jour de la position du joueur 1
		if (player1.getMovePoints() > 0 && perso.getTeam() == 1) {

			switch (dir) {

			case SOUTH:
				// System.out.println("case " + (player1.getX()+1) + ";" +
				// player1.getY() + " libre ? : " + map.isFree(player1.getX() +
				// 1, player1.getY()));
				if (map.isFree(player1.getX(), player1.getY() + 1)) {

					player1.setY(player1.getY() + 1);
					map.Free(player1.getX(), player1.getY() - 1);
					// System.out.println(" J'ai libere la case : " +
					// (player1.getX() - 1) + player1.getY());
					map.Add(player1.getX(), player1.getY(), player1);

					player1.setMovePoints(player1.getMovePoints() - 1);
				}
				break;

			case NORTH:
				if (map.isFree(player1.getX(), player1.getY() - 1)) {
					System.out.println("case libre ? : " + map.isFree(player1.getX() - 1, player1.getY()));
					player1.setY(player1.getY() - 1);
					map.Free(player1.getX(), player1.getY() + 1);
					map.Add(player1.getX(), player1.getY(), player1);

					player1.setMovePoints(player1.getMovePoints() - 1);
				}
				break;

			case WEST:
				if (map.isFree(player1.getX() - 1, player1.getY())) {
					System.out.println("case libre ? : " + map.isFree(player1.getX(), player1.getY() - 1));
					player1.setX(player1.getX() - 1);
					map.Free(player1.getX() + 1, player1.getY());
					map.Add(player1.getX(), player1.getY(), player1);

					player1.setMovePoints(player1.getMovePoints() - 1);
				}
				break;

			case EAST:
				if (map.isFree(player1.getX() + 1, player1.getY())) {
					System.out.println("case libre ? : " + map.isFree(player1.getX(), player1.getY() + 1));
					player1.setX(player1.getX() + 1);
					map.Free(player1.getX() - 1, player1.getY());
					map.Add(player1.getX(), player1.getY(), player1);

					player1.setMovePoints(player1.getMovePoints() - 1);
				}

				break;

			}
<<<<<<< HEAD
			System.out.println(" Joueur 1 : coordonnee de la case : " + player1.getX() + ";" + player1.getY());
=======
			// System.out.println("coordonnee de la case : " + player1.getX() +
			// ";" + player1.getY());
>>>>>>> GUI
		} else if (player2.getMovePoints() > 0 && perso.getTeam() == 2) {

			switch (dir) {

			case SOUTH:
					System.out.println("case libre ? : " + map.isFree(player2.getX() + 1, player2.getY()));
					if (map.isFree(player2.getX(), player2.getY() + 1)) {
					player2.setY(player2.getY() + 1);
					map.Free(player2.getX(), player2.getY() - 1);
					map.Add(player2.getX(), player2.getY(), player2);

					player2.setMovePoints(player2.getMovePoints() - 1);
				}
				break;

			case NORTH:

				if (map.isFree(player2.getX(), player2.getY() - 1)) {
					System.out.println("case libre ? : " + map.isFree(player2.getX() - 1, player2.getY()));
					player2.setY(player2.getY() - 1);
					map.Free(player2.getX(), player2.getY() + 1);
					map.Add(player2.getX(), player2.getY(), player2);
					player2.setMovePoints(player2.getMovePoints() - 1);
				}
				break;

			case WEST:
				if (map.isFree(player2.getX() - 1, player2.getY())) {
					System.out.println("case libre ? : " + map.isFree(player2.getX(), player2.getY() - 1));
					player2.setX(player2.getX() - 1);
					map.Free(player2.getX() + 1, player2.getY());
					map.Add(player2.getX(), player2.getY(), player2);

					player2.setMovePoints(player2.getMovePoints() - 1);
				}
				break;

			case EAST:

				if (map.isFree(player2.getX() + 1, player2.getY())) {
					System.out.println("case libre ? : " + map.isFree(player2.getX(), player2.getY() + 1));
					player2.setX(player2.getX() + 1);
					map.Free(player2.getX() - 1, player2.getY());
					map.Add(player2.getX(), player2.getY(), player2);

					player2.setMovePoints(player2.getMovePoints() - 1);
				}
				break;
			}
			System.out.println("Joueur 2 : coordonnee de la case : " + player2.getX() + ";" + player2.getY());
		} else {
			System.out.println("Plus de point de déplacement \n");
			this.EndGame = false;
		}

		this.EndGame = true;
	}

	public void update(Direction dir, GUICharacter perso, Map map) {

		// Allow to do the move for 5 MP
		doMove(dir, perso, map);

		// Set to 5 the MP if all has been consume
		if (this.EndGame == true) {
			nbrRound++;
			switch (perso.getTeam()) {
			case 1:
				player1.setMovePoints(5);
				break;
			case 2:
				player2.setMovePoints(5);
				break;
			}
		}
	}

	private void doAttack(Direction dir, GUICharacter perso, Map map) {
		Cell target;
		if (player1.getAttackPoints() > 0 && perso.getTeam() == 1) {

			switch (dir) {

			case SOUTH:
				target = map.getCell(player1.getX(), player1.getY() + 1);
				player1.classicAtk(target);
				System.out.println(" Joueur 1 attaque la case : " + player1.getX() + ";" + (player1.getY() + 1));
				break;

			case NORTH:
				target = map.getCell(player1.getX(), player1.getY() - 1);
				player1.classicAtk(target);
				System.out.println(" Joueur 1 attaque la case : " + player1.getX() + ";" + (player1.getY() - 1));
				break;

			case WEST:
				target = map.getCell(player1.getX() - 1, player1.getY());
				player1.classicAtk(target);
				System.out.println(" Joueur 1 attaque la case : " + (player1.getX() - 1) + ";" + player1.getY());
				break;

			case EAST:
				target = map.getCell(player1.getX() + 1, player1.getY());
				player1.classicAtk(target);
				System.out.println(" Joueur 1 attaque la case : " + (player1.getX() + 1) + ";" + player1.getY());
				break;

			}

		} else if (player2.getAttackPoints() > 0 && perso.getTeam() == 2) {

			switch (dir) {

			case SOUTH:
				target = map.getCell(player2.getX(), player2.getY() + 1);
				player2.classicAtk(target);
				System.out.println(" Joueur 2 attaque la case : " + player2.getX() + ";" + (player2.getY() + 1));
				break;

			case NORTH:
				target = map.getCell(player2.getX(), player2.getY() - 1);
				player2.classicAtk(target);
				System.out.println(" Joueur 2 attaque la case : " + player2.getX() + ";" + (player2.getY() - 1));
				break;

			case WEST:
				target = map.getCell(player2.getX() - 1, player2.getY());
				player1.classicAtk(target);
				System.out.println(" Joueur 2 attaque la case : " + (player2.getX() - 1) + ";" + player2.getY());
				break;

			case EAST:
				target = map.getCell(player2.getX() + 1, player2.getY());
				player1.classicAtk(target);
				System.out.println(" Joueur 2 attaque la case : " + (player2.getX() + 1) + ";" + player2.getY());
				break;

			}

		} else {
			System.out.println("Plus de point d'attaque !\n");
			this.EndGame = false;
		}

		this.EndGame = true;
	}

}