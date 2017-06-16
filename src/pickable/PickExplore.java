package pickable;

import carte.Map;

public class PickExplore extends PickAble {

	/**
	 * Created a new classic explore pickAble
	 * 
	 * @param x
	 *            the x coordinate of the pickAble
	 * @param y
	 *            the y coordinate of the pickAble
	 * @param entityMap
	 *            the map used to set the pickAble
	 */
	// ↓ Constructor, update and render ↓

	public PickExplore(Integer x, Integer y, Map entityMap) {
		super(x, y, entityMap);
	}
	// End(Constructor, update and render)

	// ↓ Miscellaneous methods ↓

	/**
	 * the text transformation of the current pickAble
	 */
	@Override
	public String toString() {
		return "E : Explore";
	}

	// End(Miscellaneous methods)

}
