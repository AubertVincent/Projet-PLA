package pickable;

import carte.Map;

public class PickRecall extends PickAble {

	public PickRecall(Integer x, Integer y, Map entityMap) {
		super(x, y, entityMap);
	}

	@Override
	public String toString() {
		return "MT : Tunnel";
	}
}
