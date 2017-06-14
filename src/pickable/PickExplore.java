package pickable;

import carte.Map;

public class PickExplore extends PickAble {

	public PickExplore(int x, int y, Map entityMap) {
		super(x, y, entityMap);
	}

	@Override
	public String toString() {
		return "E : Explore";
	}

}
