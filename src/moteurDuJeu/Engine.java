package moteurDuJeu;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import carte.Map;
import entite.Direction;
import gui.GUI;
import gui.GUICharacter;
import personnages.Player;

public class Engine {

	private static Player player1 = new Player(3, 5, Direction.SOUTH, 1, 1, 1, 1, 5, 1);
	private static Player player2 = new Player(31, 16, Direction.SOUTH, 1, 1, 1, 1, 5, 1);
	//private static Map map;
	private static int nbrRound;
	private static boolean EndGame;

	/**
	 * Create an Engine Object, allow us to update all the entitys
	 * 
	 * @throws SlickException
	 */
	public Engine() throws SlickException {
		player1 = new Player(5, 12, Direction.NORTH, 1, 1, 1, 1, 5, 1);
		player2 = new Player(30, 15, Direction.NORTH, 1, 1, 1, 1, 5, 1);
		//map = new Map();
		// Map.initMap();
		// perso1 = new GUICharacter(2, 4, entite.Direction.SOUTH,
		// "res/SpriteSheetAnim.png");
		// perso2 = new GUICharacter(14, 28,
		// entite.Direction.SOUTH,"res/SpriteSheetAnim.png");
		nbrRound = 0;
		EndGame = false;
	}

	private static boolean doMove(Direction dir, GUICharacter perso, int joueur) {

		// Mise a jour de la position du joueur 1
		if (player1.getMovePoints() > 0 && joueur == 1) {

			switch (dir) {

			case SOUTH:
				if (Map.isFree(player1.getX() + 1, player1.getY())) {
					// System.out.println("case libre ? : " +
					// Map.isFree(player1.getX() + 1, player1.getY()));
					player1.setX(player1.getX() + 1);
					Map.Free(player1.getX() - 1, player1.getY());
					Map.Add(player1.getX(), player1.getY(), player1);
					perso.goToDirection(Direction.SOUTH);
					player1.setMovePoints(player1.getMovePoints() - 1);
				}
				break;

			case NORTH:
				if (Map.isFree(player1.getX() - 1, player1.getY())) {
					// System.out.println("case libre ? : " +
					// Map.isFree(player1.getX() - 1, player1.getY()));
					player1.setX(player1.getX() - 1);
					Map.Free(player1.getX() + 1, player1.getY());
					Map.Add(player1.getX(), player1.getY(), player1);
					perso.goToDirection(Direction.NORTH);
					player1.setMovePoints(player1.getMovePoints() - 1);
				}
				break;

			case WEST:
				if (Map.isFree(player1.getX(), player1.getY() - 1)) {
					// System.out.println("case libre ? : " +
					// Map.isFree(player1.getX(), player1.getY() - 1));
					player1.setY(player1.getY() - 1);
					Map.Free(player1.getX(), player1.getY() + 1);
					Map.Add(player1.getX(), player1.getY(), player1);
					perso.goToDirection(Direction.WEST);
					player1.setMovePoints(player1.getMovePoints() - 1);
				}
				break;

			case EAST:
				if (Map.isFree(player1.getX(), player1.getY() + 1)) {
					// System.out.println("case libre ? : " +
					// Map.isFree(player1.getX(), player1.getY() + 1));
					player1.setY(player1.getY() + 1);
					Map.Free(player1.getX(), player1.getY() - 1);
					Map.Add(player1.getX(), player1.getY(), player1);
					perso.goToDirection(Direction.EAST);
					player1.setMovePoints(player1.getMovePoints() - 1);
				}

				break;

			}
			System.out.println("coordonnee de la case : " + player1.getX() + ";" + player1.getY());
		} else if (player2.getMovePoints() > 0 && joueur == 2) {

			switch (dir) {

			case SOUTH:
				if (Map.isFree(player2.getX() + 1, player2.getY())) {
					// System.out.println("case libre ? : " +
					// Map.isFree(player2.getX() + 1, player2.getY()));
					player2.setX(player2.getX() + 1);
					Map.Free(player2.getX() - 1, player1.getY());
					Map.Add(player2.getX(), player2.getY(), player2);
					perso.goToDirection(Direction.SOUTH);
					player2.setMovePoints(player2.getMovePoints() - 1);
				}
				break;

			case NORTH:

				if (Map.isFree(player2.getX() - 1, player2.getY())) {
					System.out.println("case libre ? : " + Map.isFree(player2.getX() - 1, player2.getY()));
					player2.setX(player2.getX() - 1);
					Map.Free(player2.getX() + 1, player1.getY());
					Map.Add(player2.getX(), player2.getY(), player2);
					perso.goToDirection(Direction.NORTH);
					player2.setMovePoints(player2.getMovePoints() - 1);
				}
				break;

			case WEST:
				if (Map.isFree(player2.getX(), player2.getY() - 1)) {
					// System.out.println("case libre ? : " +
					// Map.isFree(player2.getX(), player2.getY() - 1));
					player2.setY(player2.getY() - 1);
					Map.Free(player2.getX(), player2.getY() + 1);
					Map.Add(player2.getX(), player2.getY(), player2);
					perso.goToDirection(Direction.WEST);
					player2.setMovePoints(player2.getMovePoints() - 1);
				}
				break;

			case EAST:

				if (Map.isFree(player2.getX(), player2.getY() + 1)) {
					// System.out.println("case libre ? : " +
					// Map.isFree(player1.getX(), player1.getY() + 1));
					player2.setY(player2.getY() + 1);
					Map.Free(player2.getX(), player2.getY() - 1);
					Map.Add(player2.getX(), player2.getY(), player2);
					perso.goToDirection(Direction.EAST);
					player2.setMovePoints(player2.getMovePoints() - 1);
				}
				break;
			}
			System.out.println("coordonnee de la case : " + player2.getX() + ";" + player2.getY());
		} else {
			System.out.println("Plus de point de déplacement \n");
			return false;
		}

		return true;
	}

	public static boolean update(Direction dir, GUICharacter perso, int joueur) {

		// Permit to do the move for 5 MP
		boolean smth_append = doMove(dir, perso, joueur);

		// Set to 5 the MP if all has been consume
		if (smth_append == false) {
			nbrRound++;
			switch (joueur) {
			case 1:
				player1.setMovePoints(5);
				break;
			case 2:
				player2.setMovePoints(5);
				break;
			}
		}

		// TODO Creation of robot
		return smth_append;
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