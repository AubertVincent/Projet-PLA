package pickable;

import carte.Map;

public class PickMoveDir extends PickAble {

	public PickMoveDir(int x, int y, Map entityMap) {
		super(x, y, entityMap);
	}

	@Override
	public String toString() {
		return "MC : Mouvement";
	}
}
