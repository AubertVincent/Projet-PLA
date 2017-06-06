package moteurDuJeu;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import Test_graphique.WindowGame;
import carte.Map;
import entite.Direction;
import entite.GameException;
import personnages.Player;
import personnages.Robot;
import java.util.List;

public class Game {

	private Game game;
	private Player player1;
	private Player player2;
	private Map map;

	public void main(String[] args) throws SlickException, GameException {

		game = new Game();
	}

	/**
	 * @param j
	 *            The player who's gonna play his turn
	 * 
	 * @return New value of EndGame, True if the game is over
	 */

	private boolean RoundPlayer(Player j) {

		// TODO
		// 1. The player moves his hero
		while (j.getMovePoints() > 0) {
			j.WalkOn();
		}

		// 2. The player decides to create a robot or not

		if (player1.getLife() == 0 || player2.getLife() == 0) {
			return true;
		} else {
			return false;
		}
	}

	private boolean RoundRobot(Player player1, Player player2) {
		List<Robot> listR1 = player1.getListRobot();
		List<Robot> listR2 = player2.getListRobot();
		int size1 = listR1.size();
		int size2 = listR2.size();
		int i = 0;
		while (i < size1 || i < size2) {
			if (i < size1) {
				(listR1.get(i)).execute();
			}
			if (i < size2) {
				(listR2.get(i)).execute();
			}
			i++;
		}
		if (player1.getLife() == 0 || player2.getLife() == 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Method Allowing to play a game
	 * 
	 * @throws SlickException,
	 *             GameException
	 */
	public Game() throws SlickException, GameException {
		// Création of the two players
		// TODO Choose the player initial position

		Player player1 = new Player(10, 10, Direction.NORTH, 1, 1, 1, 1, 1, 1);
		Player player2 = new Player(100, 100, Direction.NORTH, 1, 1, 1, 1, 1, 1);

		// Initialisation of the background map
		map = new Map();
		map.initMap();

		// Creation of a new graphique windows
		new AppGameContainer(new WindowGame(), 1024 + 64, 512 + 64, false).start();

		// Count the number of round
		int nbrRound = 0;
		// checked if the game is over
		boolean EndGame = false;

		// Loop while not EndGame
		while (!EndGame) {

			EndGame = RoundPlayer(player1);

			// Player 2 can play if the Game isn't over
			if (!EndGame) {
				EndGame = RoundPlayer(player2);

				if (!EndGame) {
					EndGame = RoundRobot(player1, player2);
				}

			}

			nbrRound++;
		}
	}
}
