package pickable;

import carte.Map;

public class PickClassicAck extends PickAble {

	public PickClassicAck(Integer x, Integer y, Map entityMap) {
		super(x, y, entityMap);
	}

	@Override
	public String toString() {
		return "AC : Attaque";
	}

}
