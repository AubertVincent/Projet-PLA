package pickable;

import carte.Map;

public class PickMoveDir extends PickAble {

	public PickMoveDir(Integer x, Integer y, Map entityMap) {
		super(x, y, entityMap);
	}

	@Override
	public String toString() {
		return "MC : Mouvement";
	}
}
