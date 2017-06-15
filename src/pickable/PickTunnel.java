package pickable;

import carte.Map;

public class PickTunnel extends PickAble {

	/**
	 * Created a new tunnel move pickAble
	 * 
	 * @param x
	 *            the x coordinate of the pickAble
	 * @param y
	 *            the y coordinate of the pickAble
	 * @param entityMap
	 *            the map used to set the pickAble
	 */
	public PickTunnel(Integer x, Integer y, Map entityMap) {
		super(x, y, entityMap);
	}

}
