package pickable;

import carte.Coordinates;
import carte.Map;
import entite.Entity;

public abstract class PickAble extends Entity {

	public PickAble(Coordinates coordinates, Map entityMap) {
		super(coordinates, entityMap);
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

	public static PickAble randomPickable(int Pickable, Coordinates coordinates, Map myMap) {
		PickAble newOperator = null;
		switch (Pickable) {
		case 1:
			newOperator = new PickMoveDir(coordinates, myMap);
			break;
		case 2:
			newOperator = new PickClassicAck(coordinates, myMap);
			break;
		case 3:
			newOperator = new PickPriority(coordinates, myMap);
			break;
		case 4:
			newOperator = new PickRandomBar(coordinates, myMap);
			break;
		case 5:
			newOperator = new PickRandomMove(coordinates, myMap);
			break;
		case 6:
			newOperator = new PickRecall(coordinates, myMap);
			break;
		case 7:
			newOperator = new PickSuccession(coordinates, myMap);
			break;
		case 8:
			newOperator = new PickSuicideBomber(coordinates, myMap);
			break;
		case 9:
			newOperator = new PickTunnel(coordinates, myMap);
			break;
		}
		return newOperator;
	}

}
