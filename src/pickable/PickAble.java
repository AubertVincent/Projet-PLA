package pickable;

import java.util.LinkedList;
import java.util.List;

import carte.Map;
import entite.Entity;
import gui.GUIPickAble;

public abstract class PickAble extends Entity {

	GUIPickAble guiPickAble;

	// ↓ Miscellaneous methods ↓

	public static List<Class<? extends PickAble>> possiblePickAbleList = new LinkedList<Class<? extends PickAble>>();
	static {

		possiblePickAbleList.add(PickClassicAck.class);
		possiblePickAbleList.add(PickSuicideBomber.class);
		possiblePickAbleList.add(PickTunnel.class);
		possiblePickAbleList.add(PickMoveDir.class);
		possiblePickAbleList.add(PickRecall.class);
		// possiblePickAbleList.add(PickPickUp.class);
		possiblePickAbleList.add(PickSuccession.class);
		possiblePickAbleList.add(PickRandomBar.class);
		possiblePickAbleList.add(PickPriority.class);
		possiblePickAbleList.add(PickRandomMove.class);

	}

	public PickAble(Integer x, Integer y, Map entityMap) {
		super(x, y, entityMap);
		guiPickAble = new GUIPickAble(this, entityMap.getGUI());
	}

	@Override
	public boolean isCharacter() {
		return false;
	}

	public boolean isPickAble() {
		return true;
	}

	@Override
	public boolean isObstacle() {
		return false;
	}

	public GUIPickAble getGUIPickAble() {
		return this.guiPickAble;
	}

	/**
	 * 
	 * @param classReceived
	 *            Name of content in the bag
	 * @return String that is displayed to the player
	 */
	public static String classToString(String classReceived) {
		String toString = classReceived;
		switch (classReceived) {
		case "PickMoveDir":
			toString = "MC : Mouvement Classique";
			break;
		case "PickRecall":
			toString = "MR : Mouvement Rappel";
			break;
		case "PickTunnel":
			toString = "MT : Mouvement Tunnel";
			break;
		case "PickRandomMove":
			toString = "RM : Mouvement Random";
			break;
		case "PickSuccession":
			toString = "; : Succession";
			break;
		case "PickRandomBar":
			toString = "/ : Random";
			break;
		case "PickPriority":
			toString = "> : Priorité";
			break;
		case "PickClassicAck":
			toString = "AC : Attaque Classique";
			break;
		case "PickSuicideBomber":
			toString = "AS : Attaque Suicide";
			break;
		}

		return toString;
	}

	public static PickAble randomPickable(int x, int y, Map myMap) {
		PickAble newOperator = null;
		int pickable = (int) ((int) 1 + (Math.random() * (9)));
		switch (pickable) {
		case 1:
			newOperator = new PickMoveDir(x, y, myMap);

			break;
		case 2:
			newOperator = new PickClassicAck(x, y, myMap);
			break;
		case 3:
			newOperator = new PickPriority(x, y, myMap);
			break;
		case 4:
			newOperator = new PickRandomBar(x, y, myMap);
			break;
		case 5:
			newOperator = new PickRandomMove(x, y, myMap);
			break;
		case 6:
			newOperator = new PickRecall(x, y, myMap);
			break;
		case 7:
			newOperator = new PickSuccession(x, y, myMap);
			break;
		case 8:
			newOperator = new PickSuicideBomber(x, y, myMap);
			break;
		case 9:
			newOperator = new PickTunnel(x, y, myMap);
			break;
		}
		return newOperator;
	}

	public static List<Class<? extends PickAble>> getPossiblePickAbleList() {
		return possiblePickAbleList;
	}

	// End(Miscellaneous methods)

}
