package moteurDuJeu;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import Test_graphique.WindowGame;
import carte.Carte;
import personnages.Joueur;

public class Game {

	private Game game;

	public void main(String[] args) throws SlickException {

		game = new Game();
	}

	/**
	 * @param j Le joueur dont on va jouer le tour
	 * 
	 * @return Nouvelle valeur de NbrRound, -1 si fin de partie
	 */
	private boolean JouerTour(Joueur j) {
		// TODO
		return false;
	}

	/**
	 * Methode permettant de jouer 	une partie
	 * @throws SlickException
	 */
	public Game() throws SlickException {
		// Création des deux joueurs
		Joueur joueur1 = new Joueur();
		Joueur joueur2 = new Joueur();

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
			
			//TODO
			nbrRound++;
		}
	}
}
