package moteurDuJeu;

import java.util.ArrayList;
import java.util.List;

import carte.Base;
import carte.Map;
import entite.Direction;
import entite.Team;
import exceptions.NotDoableException;
import operateur.Behavior;
import operateur.ClassicAck;
import operateur.Explore;
import operateur.MoveDir;
import operateur.Priority;
import operateur.RandomBar;
import operateur.RandomMove;
import operateur.Recall;
import operateur.Succession;
import operateur.Tunnel;
import personnages.Besace;
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

	public Test() throws NotDoableException {
		my_map = new Map();
		listRobot = new ArrayList<Robot>();
		// x, y, entityMap, besace, direction, life, vision, attack, range,
		// movePoints, recall, team, attackPoints,
		// base
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
		// int xRobot1 = 33;
		// int yRobot1 = 11;
		int xRobot2 = 33;
		int yRobot2 = 17;
		// int xRobot3 = 6;
		// int yRobot3 = 5;
		// int xRobot4 = 10;
		// int yRobot4 = 10;

		_Sequence sequence = new Tree(random, rm1, rm1);
		// _Sequence sequence2 = new Tree(b, t, t);
		// _Sequence sequence2 = new Tree(b, ack, ack);
		// Robot robot = new Robot(xRobot1, yRobot1, my_map, new Besace(),
		// Direction.SOUTH, 10, 1, 1, 1, 2, 1, Team.BLEU,
		// 1, new Base(2, 4, Team.BLEU), sequence);
		Robot robot2 = new Robot(xRobot2, yRobot2, my_map, new Besace(), Direction.SOUTH, 1, 1, 1, 1, 1, 1, Team.ROUGE,
				1, new Base(21, 8, Team.ROUGE), sequence);
		// Robot robot3 = new Robot(xRobot3, yRobot3, my_map, new Besace(),
		// Direction.SOUTH, 10, 1, 1, 1, 2, 1, Team.BLEU,
		// 1, new Base(21, 8, Team.BLEU), sequence);
		// Robot robot4 = new Robot(xRobot4, yRobot4, my_map, new Besace(),
		// Direction.SOUTH, 10, 1, 1, 1, 2, 1, Team.BLEU,
		// 1, new Base(21, 8, Team.BLEU), sequence);
		// my_map.getCell(xRobot1, yRobot1).setEntity(robot);
		my_map.getCell(xRobot2, yRobot2).setEntity(robot2);
		// my_map.getCell(1, 0).setEntity(new Obstacle(1, 0, my_map));
		// my_map.getCell(0, 1).setEntity(new Obstacle(0, 1, my_map));
		// my_map.getCell(33, 5).setEntity(new Obstacle(5, 13, my_map));
		// my_map.getCell(33, 7).setEntity(new Obstacle(5, 13, my_map));
		listRobot.add(robot);// new Robot(2, 4, ma_map, new Besace(),
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
				sequence.execute(robot2);
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

	public static void main(String[] args) throws NotDoableException {
		try {
			new Test();
		} catch (NotDoableException e) {
			System.out.println("\"MAIN_ERROR\"");
		}
	}
}