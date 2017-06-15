package pickable;

import carte.Map;

public class PickPriority extends PickAble {

	/**
	 * Created a new priority pickAble
	 * 
	 * @param x
	 *            the x coordinate of the pickAble
	 * @param y
	 *            the y coordinate of the pickAble
	 * @param entityMap
	 *            the map used to set the pickAble
	 */
	public PickPriority(Integer x, Integer y, Map entityMap) {
		super(x, y, entityMap);
	}

}
