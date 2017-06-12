package moteurDuJeu;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import carte.Base;
import carte.Cell;
import carte.Coordinates;
import carte.Map;
import entite.Direction;
import entite.Team;
import exceptions.NotDoableException;
import gui.GUI;
import gui.GUICharacter;
import gui.GUIPlayer;
import gui.GUIRobot;
import personnages.Besace;
import personnages.Player;
import personnages.Robot;
import reader.Reader;
import sequence._Sequence;

public class Engine {

	private List<Player> listPlayer;

	public Map ma_map;

	private PlayPhase playPhase;

	/**
	 * Create an Engine Object An Engine has a map and a list of its players
	 * 
	 * @throws SlickException
	 */
	public Engine(GUI userInterface) throws SlickException {
		Coordinates coordP1 = new Coordinates(2, 4);
		Coordinates coordP2 = new Coordinates(31, 15);
		ma_map = new Map();
		GUIPlayer guiPlayerTmp;
		Player playerTmp;
		listPlayer = new ArrayList<Player>();
		try {
			guiPlayerTmp = new GUIPlayer(userInterface, coordP1, entite.Direction.SOUTH, 100, Team.ROUGE);
			playerTmp = new Player(coordP1, ma_map, new Besace(), Direction.SOUTH, 1, 1, 1, 1, 2, 1, 1, Team.ROUGE,
					new Base(coordP1, Team.ROUGE), guiPlayerTmp);
			listPlayer.add(playerTmp);
			guiPlayerTmp.setPlayer(playerTmp);
			userInterface.addGUICharactere(guiPlayerTmp);

			guiPlayerTmp = new GUIPlayer(userInterface, coordP2, entite.Direction.SOUTH, 100, Team.BLEU);
			playerTmp = new Player(coordP2, ma_map, new Besace(), Direction.SOUTH, 1, 1, 1, 1, 2, 1, 1, Team.BLEU,
					new Base(coordP2, Team.BLEU), guiPlayerTmp);

			listPlayer.add(playerTmp);
			guiPlayerTmp.setPlayer(playerTmp);
			userInterface.addGUICharactere(guiPlayerTmp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ma_map.init(userInterface, this);
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
			if (p.getTeam() == team) {
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
		return this.ma_map;
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
		Coordinates coord;
		Player player;
		// Case of Player1
		if (perso.getTeam() == Team.ROUGE) {
			player = getPlayer(Team.ROUGE);
			if (player.getMovePoints() > 0) {
				coord = new Coordinates(player.getCoord());
				switch (dir) {
				case SOUTH:
					coord.setY(coord.getY() + 1);
					if (map.isFree(coord)) {
						player.setCoord(new Coordinates(coord));
						// player.getCoord().setY(coord.getY());
						coord.setY(coord.getY() - 1);
						map.Free(coord);
						moveSucces = true;
					}
					break;
				case NORTH:
					coord.setY(coord.getY() - 1);
					if (map.isFree(coord)) {
						player.setCoord(new Coordinates(coord));
						// player.getCoord().setY(coord.getY());
						coord.setY(coord.getY() + 1);
						map.Free(coord);
						moveSucces = true;
					}
					break;
				case WEST:
					coord.setX(coord.getX() - 1);
					if (map.isFree(coord)) {
						player.setCoord(new Coordinates(coord));
						// player.getCoord().setX(coord.getX());
						coord.setX(coord.getX() + 1);
						map.Free(coord);
						moveSucces = true;
					}
					break;
				case EAST:
					coord.setX(coord.getX() + 1);
					if (map.isFree(coord)) {
						player.setCoord(new Coordinates(coord));
						// player.getCoord().setX(coord.getX());
						coord.setX(coord.getX() - 1);
						map.Free(coord);
						moveSucces = true;
					}
					break;
				}
			}
		}

		// Case of Player2
		else if (perso.getTeam() == Team.BLEU) {
			player = getPlayer(Team.BLEU);
			if (player.getMovePoints() > 0) {
				coord = new Coordinates(player.getCoord());
				switch (dir) {

				case SOUTH:
					coord.setY(coord.getY() + 1);
					if (map.isFree(coord)) {
						player.setCoord(new Coordinates(coord));
						// player.getCoord().setY(coord.getY());
						coord.setY(coord.getY() - 1);
						map.Free(coord);
						moveSucces = true;
					}
					break;

				case NORTH:
					coord.setY(coord.getY() - 1);
					if (map.isFree(coord)) {
						player.setCoord(new Coordinates(coord));
						// player.getCoord().setY(coord.getY());
						coord.setY(coord.getY() + 1);
						map.Free(coord);
						moveSucces = true;
					}
					break;

				case WEST:
					coord.setX(coord.getX() - 1);
					if (map.isFree(coord)) {
						player.setCoord(new Coordinates(coord));
						// player.getCoord().setX(coord.getX());
						coord.setX(coord.getX() + 1);
						map.Free(coord);
						moveSucces = true;
					}
					break;
				case EAST:
					coord.setX(coord.getX() + 1);
					if (map.isFree(coord)) {
						player.setCoord(new Coordinates(coord));
						// player.getCoord().setX(coord.getX());
						coord.setX(coord.getX() - 1);
						map.Free(coord);
						moveSucces = true;
					}
					break;
				}
			}
		}
		// perso hasn't team
		else {
			return moveSucces;
		}
		if (moveSucces) {
			map.setEntity(player.getCoord(), player);
			player.setMovePoints(player.getMovePoints() - 1);
			setPlayPhase(0);
			return moveSucces;
		}
		// If the player doesn't have MP
		else {
			return false;
		}
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
					target = map.getCell(player.getCoord().getX(), player.getCoord().getY() + 1);
					player.classicAtk(target);
					System.out.println(" Joueur 1 attaque la case : " + player.getCoord().getX() + ";"
							+ (player.getCoord().getY() + 1));
					break;

				case NORTH:
					target = map.getCell(player.getCoord().getX(), player.getCoord().getY() - 1);
					player.classicAtk(target);
					System.out.println(" Joueur 1 attaque la case : " + player.getCoord().getX() + ";"
							+ (player.getCoord().getY() - 1));
					break;

				case WEST:
					target = map.getCell(player.getCoord().getX() - 1, player.getCoord().getY());
					player.classicAtk(target);
					System.out.println(" Joueur 1 attaque la case : " + (player.getCoord().getX() - 1) + ";"
							+ player.getCoord().getY());
					break;

				case EAST:
					target = map.getCell(player.getCoord().getX() + 1, player.getCoord().getY());
					player.classicAtk(target);
					System.out.println(" Joueur 1 attaque la case : " + (player.getCoord().getX() + 1) + ";"
							+ player.getCoord().getY());
					break;
				}
				player.setAttackPoints(player.getAttackPoints() - 1);

			} else if (perso.getTeam().equals(Team.BLEU)) {
				player = getPlayer(Team.BLEU);
				switch (dir) {

				case SOUTH:
					target = map.getCell(player.getCoord().getX(), player.getCoord().getY() + 1);
					player.classicAtk(target);
					System.out.println(" Joueur 2 attaque la case : " + player.getCoord().getX() + ";"
							+ (player.getCoord().getY() + 1));
					break;

				case NORTH:
					target = map.getCell(player.getCoord().getX(), player.getCoord().getY() - 1);
					player.classicAtk(target);
					System.out.println(" Joueur 2 attaque la case : " + player.getCoord().getX() + ";"
							+ (player.getCoord().getY() - 1));
					break;

				case WEST:
					target = map.getCell(player.getCoord().getX() - 1, player.getCoord().getY());
					player.classicAtk(target);
					System.out.println(" Joueur 2 attaque la case : " + (player.getCoord().getX() - 1) + ";"
							+ player.getCoord().getY());
					break;

				case EAST:
					target = map.getCell(player.getCoord().getX() + 1, player.getCoord().getY());
					player.classicAtk(target);
					System.out.println(" Joueur 2 attaque la case : " + (player.getCoord().getX() + 1) + ";"
							+ player.getCoord().getY());
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

	public void createRobot(GUIPlayer GUIPlayer, GUI userInterface, Map map) {
		System.out.println("\n \n Je suis la _\n \n");
		// TODO : création d'un robot
		Coordinates coordBase;
		Player player;

		player = GUIPlayer.getPlayer();
		coordBase = player.getBase().getCoord();
		try {
			if (map.isFree(coordBase)) {
				Robot robot = new Robot(coordBase, ma_map, new Besace(), Direction.SOUTH, 1, 1, 1, 1, 1, 1,
						player.getTeam(), 1, player.getBase(), Reader.parse("(MC2E | (AC;(MC3N>MT8.3)))"), player,
						GUIPlayer);
				player.addRobot(coordBase, robot);
				map.setEntity(coordBase, robot);
				GUIPlayer.createRobot(robot, userInterface);
			} else {
				// TODO find the player's base nearest free cell

			}
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void behaviorModif(GUIRobot GUIRobot, GUI userInterface, Map map) {
		Player player = getPlayer(GUIRobot.getTeam());
		Robot robot = GUIRobot.getRobot();
		// TODO : gestion du parseur
		// pour reccuperer la nouveau sequence
		_Sequence newAutomaton = Reader.parse("  ");
		robot.setAutomaton(newAutomaton);
	}

	public void setPlayPhase(int key) {
		if (getPlayer(Team.ROUGE).getMovePoints() == 0 && getPlayer(Team.BLEU).getMovePoints() == 0
				&& this.playPhase == PlayPhase.playerMovement) {
			this.playPhase = PlayPhase.behaviorModification;
		} else if (key == Input.KEY_SPACE) {
			this.playPhase = PlayPhase.automatonExecution;
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
	// public void mousePressed(int button, int mouseXCell, int mouseYCell) {
	// // TODO A vous de jouer -> decider que faire lors d'un clic de souris
	//
	// // @Conseil :
	// Player player;
	// Robot robot;
	// if (playPhase == PlayPhase.behaviorModification) {
	// // Gestion clic
	// // Search the player on the Cell x,y
	// player = this.getPlayerFromXY(mouseXCell, mouseYCell);
	//
	// }
	// }

}