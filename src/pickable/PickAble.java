package pickable;

import carte.Map;
import entite.Entity;

public abstract class PickAble extends Entity {

	public PickAble(int x, int y, Map entityMap) {
		super(x, y, entityMap);
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

	public static PickAble randomPickable(int Pickable, int x, int y, Map myMap) {
		PickAble newOperator = null;
		switch (Pickable) {
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
}
