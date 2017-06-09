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

}
