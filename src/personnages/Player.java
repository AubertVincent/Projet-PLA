package personnages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entite.Direction;
import operateur.ClassicAck;
import operateur.Operator;

public class Player extends Character {

	public List<Robot> listRobot;
	public static Map<Operator, Integer> besace;

	public Player(int x, int y, Direction direction, int life, int vision, int attack, int range, int movePoints,
			int recall, int aP) {
		super(x, y, direction, life, vision, attack, range, movePoints, recall, aP);
		listRobot = new ArrayList<Robot>();
		besace = new HashMap<Operator, Integer>();
	}

	public void addRobot(Robot robot) {
		listRobot.add(robot);
	}

	public void addOperator(Operator op) {
		Integer nbr = besace.get(op);
		if (nbr == null) {
			besace.put(op, 1);
		} else {
			besace.put(op, nbr + 1);
		}
	}

	public void removeOperator(Operator op) {
		Integer nbr = besace.get(op);
		if (nbr == 1) {
			besace.remove(op);
		} else {
			besace.put(op, nbr - 1);
		}
	}

	public boolean isInBesace(Operator op) {
		return besace.get(op) != null;
	}

	public int nbrInBesace(Operator op) {
		return besace.get(op);
	}

	public List<Robot> getListRobot() {
		return listRobot;
	}

	// TODO
	public void CreateRobot() {

	}

	@Override
	public boolean isPlayer() {
		return true;
	}

	@Override
	public boolean isRobot() {
		return false;
	}

	public void setX(int x) {
		super.setX(x);
	}

	public void setY(int y) {
		super.setY(y);
	}

	public int getX() {
		return super.getX();
	}

	public int getY() {
		return super.getY();
	}


	// Tests main
	public static void main(String[] args) {
		Player joueur = new Player(5, 12, Direction.NORTH, 1, 1, 1, 1, 5, 1, 1);
		
		System.out.println("is player ? " + joueur.isPlayer());
		System.out.println("is Robot ? " + joueur.isRobot());
		joueur.addRobot(new Robot(0, 0, Direction.EAST, 1, 0, 1, 1, 1, 1, 0));
		joueur.addRobot(new Robot(1, 0, Direction.EAST, 1, 0, 1, 1, 1, 1, 0));
		joueur.addRobot(new Robot(2, 0, Direction.EAST, 1, 0, 1, 1, 1, 1, 0));
		joueur.addRobot(new Robot(3, 0, Direction.EAST, 1, 0, 1, 1, 1, 1, 0));
		System.out.println(joueur.getListRobot().size());
		System.out.println("Position avant : " + joueur.getX() + " " + joueur.getY());
		joueur.setX(10);
		joueur.setY(10);
		System.out.println("Position avant : " + joueur.getX() + " " + joueur.getY());
		ClassicAck test = new ClassicAck(4,5);
		joueur.addOperator(test);
		System.out.println("Is in the besace : ? " + joueur.isInBesace(test));
		
		
	}

	@Override
	public boolean isObstacle() {
		// TODO Auto-generated method stub
		return false;
	}

}
