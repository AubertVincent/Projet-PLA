package moteurDuJeu;

import java.util.ArrayList;
import java.util.List;

import carte.Base;
import carte.Map;
import carte.Obstacle;
import entite.Direction;
import entite.Team;
import exceptions.NotDoableException;
import operateur.Behavior;
import operateur.ClassicAck;
import operateur.MoveDir;
import operateur.RandomBar;
import operateur.Recall;
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
		Behavior b = new RandomBar();
		Recall r = new Recall(0);
		MoveDir m1 = new MoveDir(Direction.NORTH, 1);
		MoveDir m2 = new MoveDir(Direction.NORTH, 1);
		ClassicAck ack = new ClassicAck();
		int xRobot1 = 5;
		int xRobot2 = 5;
		int yRobot1 = 9;
		int yRobot2 = 11;

		_Sequence sequence = new Tree(b, m2, m2);
		_Sequence sequence2 = new Tree(b, m1, m1);
		// _Sequence sequence2 = new Tree(b, ack, ack);
		Robot robot = new Robot(xRobot1, yRobot1, my_map, new Besace(), Direction.SOUTH, 10, 1, 1, 1, 100, 1,
				Team.ROUGE, 1, new Base(2, 4, Team.ROUGE), sequence);
		Robot robot2 = new Robot(xRobot2, yRobot2, my_map, new Besace(), Direction.SOUTH, 1, 1, 1, 1, 100, 1, Team.BLEU,
				1, new Base(21, 8, Team.BLEU), sequence2);
		my_map.setEntity(xRobot1, yRobot1, robot);
		my_map.setEntity(xRobot2, yRobot2, robot2);
		my_map.setEntity(5, 13, new Obstacle(5, 13, my_map));
		listRobot.add(robot);// new Robot(2, 4, ma_map, new Besace(),
		listRobot.add(robot2); // Direction.SOUTH, 1, 1, 1, 1, 10, 1, 1,
								// Team.ROUGE,

		my_map.init(this);
		while (true) {
			System.out.println("First Position Robot 1 : ");
			int x = robot.getX();
			int y = robot.getY();
			System.out.println("x:" + x + "; y:" + y);
			System.out.println("First Position Robot 2 : ");
			x = robot2.getX();
			y = robot2.getY();
			System.out.println("x:" + x + "; y:" + y);
			int life = robot.getLife();
			System.out.println("Avant life : " + life);
			try {
				sequence2.execute(robot2);
				// sequence.execute(robot);
			} catch (NotDoableException e) {
				System.out.println("\"ERROR\"");
			}
			life = robot.getLife();
			System.out.println("Après life : " + life);
			System.out.println("Second Position Robot 1 : ");
			x = robot.getX();
			y = robot.getY();
			System.out.println("x:" + x + "; y:" + y);
			System.out.println("Second Position Robot 2 : ");
			x = robot2.getX();
			y = robot2.getY();
			System.out.println("x:" + x + "; y:" + y);
			life = robot.getLife();
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