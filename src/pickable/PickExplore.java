package pickable;

import carte.Map;

public class PickExplore extends PickAble {

	// ↓ Constructor, update and render ↓

	public PickExplore(Integer x, Integer y, Map entityMap) {
		super(x, y, entityMap);
	}
	// End(Constructor, update and render)

	// ↓ Miscellaneous methods ↓

	@Override
	public String toString() {
		return "E : Explore";
	}

	// End(Miscellaneous methods)

}
