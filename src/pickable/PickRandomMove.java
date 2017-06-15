package pickable;

import carte.Map;

public class PickRandomMove extends PickAble {

	public PickRandomMove(Integer x, Integer y, Map entityMap) {
		super(x, y, entityMap);
	}

	@Override
	public String toString() {
		return "RM : Mouvement Random";
	}
}
