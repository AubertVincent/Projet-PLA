package moteurDuJeu;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import Test_graphique.WindowGame;
import carte.Map;
import entite.GameException;
import personnages.Player;

public class Game {

	private Game game;

	public void main(String[] args) throws SlickException, GameException {

		game = new Game();
	}

	/**
	 * @param j The player who's gonna play his turn
	 * 
	 * @return New value of EndGame, True if the game is over
	 */
	private boolean JouerTour(Player j) {
		// TODO
		// 1. The player moves his hero
		// 2. The player decides to create a robot or not
		
		return false;
	}

	/**
	 * Method Allowing to play a game
	 * 
	 * @throws SlickException, GameException
	 */
	public Game() throws SlickException, GameException {
		// Création of the two players
		// TODO Choose the player initial position
		Player joueur1 = new Player();
		Player joueur2 = new Player();

		// Initialisation of the background map
		Map map = new Map();
		map.initMap();

		// Creation of a new graphique windows
		new AppGameContainer(new WindowGame(), 1024 + 64, 512 + 64, false).start();

		// Count the number of round
		int nbrRound = 0;
		// checked if the game is over
		boolean EndGame = false;

		// Loop while not EndGame
		while (!EndGame) {

			EndGame = JouerTour(joueur1);

			// Player 2 can play if the Game isn't over
			if (!EndGame) {
				EndGame = JouerTour(joueur2);
			}

			// TODO
			// Faire l'execution des automates des robots
			nbrRound++;
		}
	}
}
