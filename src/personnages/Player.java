package personnages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entite.Direction;
import operateur.Operator;

public class Player extends Caracter {

	public List<Robot> listRobot;
	public static Map<Operator, Integer> besace;

	public Player(int x, int y, Direction direction, int life, int vision, int attack, int range, int movePoints,
			int recall) {
		super(x, y, direction, life, vision, attack, range, movePoints, recall);
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

	// TODO Keyboard reaction
	public void WalkOn() {
		// TODO Permet d'avancer d'un pas
		super.movePoints--;
	}

}
