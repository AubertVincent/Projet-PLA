package pickable;

import carte.Map;

public class PickRandomMove extends PickAble {

	public PickRandomMove(int x, int y, Map entityMap) {
		super(x, y, entityMap);
	}

	@Override
	public String toString() {
		return "RM : Mouvement Random";
	}
}