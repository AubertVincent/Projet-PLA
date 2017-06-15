package pickable;

import carte.Map;

public class PickRandomBar extends PickAble {

	/**
	 * Created a new random bar pickAble
	 * 
	 * @param x
	 *            the x coordinate of the pickAble
	 * @param y
	 *            the y coordinate of the pickAble
	 * @param entityMap
	 *            the map used to set the pickAble
	 */
	public PickRandomBar(Integer x, Integer y, Map entityMap) {
		super(x, y, entityMap);
	}

}
