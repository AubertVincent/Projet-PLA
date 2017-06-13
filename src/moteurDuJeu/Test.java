package moteurDuJeu;

import java.util.ArrayList;
import java.util.List;

import carte.Base;
import carte.Map;
import carte.Obstacle;
import entite.Direction;
import entite.Team;
import exceptions.NotDoableException;
import gui.GUI;
import operateur.Behavior;
import operateur.ClassicAck;
import operateur.Explore;
import operateur.MoveDir;
import operateur.Priority;
import operateur.RandomBar;
import operateur.RandomMove;
import operateur.Recall;
import operateur.Succession;
import operateur.SuicideBomber;
import operateur.Tunnel;
import personnages.Player;
import personnages.Robot;
import sequence.Tree;
import sequence._Sequence;

public class Test {
	private List<Robot> listRobot;
	private Robot robot;

	public Map my_map;
	public Engine engine;
	/*
	 * public Robot(int x, int y, Map entityMap, Besace besace, Direction
	 * direction, int life, int vision, int attack, int range, int movePoints,
	 * int recall, Team team, int attackPoints, Base base, _Sequence
	 * myAutomaton, Player player, java.util.Map<Pair<Direction, Integer>,
	 * Pair<Robot, Integer>> targetsLife, GUICharacter GUIPlayer)
	 */

	public Test() throws Exception {
		GUI gui = new GUI();
		my_map = new Map(gui);
		listRobot = new ArrayList<Robot>();
		// x, y, entityMap, besace, direction, life, vision, attack, range,
		// movePoints, recall, team, attackPoints,
		// base
		//
		Behavior random = new RandomBar();
		Behavior succession = new Succession();
		Behavior priority = new Priority();
		Recall r = new Recall(0);
		MoveDir m1 = new MoveDir(Direction.WEST, 0);
		MoveDir m2 = new MoveDir(Direction.SOUTH, 0);
		RandomMove rm1 = new RandomMove();
		ClassicAck ack = new ClassicAck();
		Tunnel t = new Tunnel(5, 9);
		Explore explore = new Explore();
		SuicideBomber bomb = new SuicideBomber();
		int xRobot1 = 0;
		int yRobot1 = 0;
		int xRobot2 = 10;
		int yRobot2 = 11;
		int xRobot3 = 0;
		int yRobot3 = 1;
		// int xRobot4 = 10;
		// int yRobot4 = 10;

		_Sequence sequence = new Tree(random, bomb, bomb);
		_Sequence sequence2 = new Tree(random, m1, m1);
		// _Sequence sequence2 = new Tree(b, ack, ack);
		Player playerRouge = new Player(new Base(Team.ROUGE), my_map);
		Player playerBleu = new Player(new Base(Team.BLEU), my_map);
		Robot robot1 = new Robot(new Base(Team.ROUGE), my_map, sequence, playerBleu);
		Robot robot2 = new Robot(new Base(Team.ROUGE), my_map, sequence2, playerBleu);
		Robot robot3 = new Robot(new Base(Team.BLEU), my_map, sequence2, playerBleu);

		// 10, 9, my_map, new Besace(), Direction.SOUTH, 10, 1, 1, 1, 1, 1, 1,
		// Team.ROUGE,1, new Base(21, 8, Team.ROUGE)
		// Robot robot4 = new Robot(xRobot4, yRobot4, my_map, new Besace(),
		// Direction.SOUTH, 10, 1, 1, 1, 2, 1, Team.BLEU,
		// 1, new Base(21, 8, Team.BLEU), sequence);
		my_map.setEntity(robot1);
		my_map.setEntity(robot2);
		my_map.setEntity(robot3);
		robot1.teleport(xRobot1, yRobot1);
		robot2.teleport(xRobot2, yRobot2);
		robot3.teleport(xRobot3, yRobot3);
		my_map.getCell(10, 9).setEntity(playerRouge);
		my_map.getCell(17, 13).setEntity(playerBleu);
		playerRouge.teleport(9, 10);
		playerBleu.teleport(10, 9);
		// my_map.getCell(1, 0).setEntity(new Obstacle(1, 0, my_map));
		// my_map.getCell(0, 1).setEntity(new Obstacle(0, 1, my_map));
		// my_map.getCell(33, 5).setEntity(new Obstacle(5, 13, my_map));
		// my_map.getCell(33, 7).setEntity(new Obstacle(5, 13, my_map));
		my_map.setEntity(new Obstacle(11, 10, my_map));
		listRobot.add(robot1);// new Robot(2, 4, ma_map, new Besace(),
		// listRobot.add(robot2); // Direction.SOUTH, 1, 1, 1, 1, 10, 1, 1,
		// Team.ROUGE,

		// my_map.init(this);
		while (true) {
			// System.out.println("First Position Robot 1 : ");
			// int x = robot.getX();
			// int y = robot.getY();
			// // System.out.println("x:" + x + "; y:" + y);
			// System.out.println("First Position Robot 2 : ");
			// int x = robot2.getX();
			// int y = robot2.getY();
			// System.out.println("x:" + x + "; y:" + y);
			// int life = robot.getLife();
			// System.out.println("Avant life R1: " + life);
			// System.out.println("Avant life R3: " + robot3.getLife());
			// System.out.println("Avant life R4: " + robot4.getLife());
			try {
				sequence.execute(robot1);
				// sequence.execute(robot);
			} catch (NotDoableException e) {
				System.out.println("\"ERROR\"");
			}
			// life = robot.getLife();
			// System.out.println("Après life R1: " + life);
			// System.out.println("Après life R3: " + robot3.getLife());
			// System.out.println("Après life R4: " + robot4.getLife());
			// System.out.println("Second Position Robot 1 : ");
			// x = robot.getX();
			// y = robot.getY();
			// System.out.println("x:" + x + "; y:" + y);
			// System.out.println("Second Position Robot 2 : ");
			// x = robot2.getX();
			// y = robot2.getY();
			// System.out.println("x:" + x + "; y:" + y);
			// life = robot.getLife();
		}
	}

	public Robot getRobot(Team team) {
		// for (Robot r : listRobot) {
		// if (r.getTeam() == team) {
		// return r;
		// }
		// }
		// return null;
		return robot;
	}

	public static void main(String[] args) throws Exception {
		try {
			new Test();
		} catch (NotDoableException e) {
			System.out.println("\"MAIN_ERROR\"");
		}
	}
}