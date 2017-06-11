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

}
