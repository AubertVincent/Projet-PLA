package pickable;

import carte.Map;

public class PickRandomMove extends PickAble {

	/**
	 * Created a new random move pickAble
	 * 
	 * @param x
	 *            the x coordinate of the pickAble
	 * @param y
	 *            the y coordinate of the pickAble
	 * @param entityMap
	 *            the map used to set the pickAble
	 */
	public PickRandomMove(Integer x, Integer y, Map entityMap) {
		super(x, y, entityMap);
	}

	/**
	 * the text transformation of the current pickAble
	 */
	@Override
	public String toString() {
		return "RM : Mouvement Random";
	}
}
