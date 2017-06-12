package pickable;

import carte.Coordinates;
import carte.Map;
import entite.Entity;

public abstract class PickAble extends Entity {

	public PickAble(Coordinates coord, Map entityMap) {
		super(coord, entityMap);
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

	public static PickAble randomPickable(int Pickable, Coordinates coord, Map myMap) {
		PickAble newOperator = null;
		switch (Pickable) {
		case 1:
			newOperator = new PickMoveDir(coord, myMap);
			break;
		case 2:
			newOperator = new PickClassicAck(coord, myMap);
			break;
		case 3:
			newOperator = new PickPriority(coord, myMap);
			break;
		case 4:
			newOperator = new PickRandomBar(coord, myMap);
			break;
		case 5:
			newOperator = new PickRandomMove(coord, myMap);
			break;
		case 6:
			newOperator = new PickRecall(coord, myMap);
			break;
		case 7:
			newOperator = new PickSuccession(coord, myMap);
			break;
		case 8:
			newOperator = new PickSuicideBomber(coord, myMap);
			break;
		case 9:
			newOperator = new PickTunnel(coord, myMap);
			break;
		}
		return newOperator;
	}

}
