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
