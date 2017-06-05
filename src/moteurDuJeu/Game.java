package moteurDuJeu;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import Test_graphique.WindowGame;
import carte.Carte;
import entite.GameException;
import personnages.Player;

public class Game {

	private Game game;

	public void main(String[] args) throws SlickException, GameException {

		game = new Game();
	}

	/**
	 * @param j
	 *            Le joueur dont on va jouer le tour
	 * 
	 * @return Nouvelle valeur de NbrRound, -1 si fin de partie
	 */
	private boolean JouerTour(Player j) {
		// TODO
		// 1. Faire deplacer son héros au joueur
		// 2. Faire le choix de la création de robot
		return false;
	}

	/**
	 * Methode permettant de jouer une partie
	 * 
	 * @throws SlickException
	 */
	public Game() throws SlickException, GameException {
		// Création des deux joueurs
		// TODO choisir la position de base
		Player joueur1 = new Player();
		Player joueur2 = new Player();

		// Initialisation de la carte en background
		Carte map = new Carte();
		map.initCarte();

		// Création de la fenetre graphique
		new AppGameContainer(new WindowGame(), 1024 + 64, 512 + 64, false).start();

		// Compte le nombre de tour
		int nbrRound = 0;
		// Indique si la partie est terminé
		boolean FinPartie = false;

		// On tourne tant qu'on est pas en fin de partie
		while (!FinPartie) {

			FinPartie = JouerTour(joueur1);

			// Le Joueur 2 peut jouer son tour si la partie n'est pas fini
			if (!FinPartie) {
				FinPartie = JouerTour(joueur2);
			}

			// TODO
			// Faire l'execution des automates des robots
			nbrRound++;
		}
	}
}
