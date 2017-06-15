package pickable;

import carte.Map;

public class PickMoveDir extends PickAble {

	/**
	 * Created a new classic move pickAble
	 * 
	 * @param x
	 *            the x coordinate of the pickAble
	 * @param y
	 *            the y coordinate of the pickAble
	 * @param entityMap
	 *            the map used to set the pickAble
	 */
	public PickMoveDir(Integer x, Integer y, Map entityMap) {
		super(x, y, entityMap);
	}

	/**
	 * the text transformation of the current pickAble
	 */
	@Override
	public String toString() {
		return "MC : Mouvement";
	}
}
