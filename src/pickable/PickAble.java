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

	public static String classToString(String classReceived) {

		System.out.println(classReceived);
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
}
