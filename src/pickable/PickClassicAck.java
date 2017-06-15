package pickable;

import carte.Map;

public class PickClassicAck extends PickAble {

	/**
	 * Created a new classic attack pickAble
	 * 
	 * @param x
	 *            the x coordinate of the pickAble
	 * @param y
	 *            the y coordinate of the pickAble
	 * @param entityMap
	 *            the map used to set the pickAble
	 */
	public PickClassicAck(Integer x, Integer y, Map entityMap) {
		super(x, y, entityMap);
	}

	/**
	 * the text transformation of the current pickAble
	 */
	@Override
	public String toString() {
		return "AC : Attaque";
	}

}
