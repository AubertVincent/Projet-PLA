package personnages;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import pickable.PickAble;

public class Besace {

	private Map<Class<? extends PickAble>, Integer> besace;

	public void init() {
		for (Iterator<Class<? extends PickAble>> mapIter = PickAble.possiblePickAbleList.iterator(); mapIter
				.hasNext();) {
			Class<? extends PickAble> currentClass;
			currentClass = mapIter.next();
			besace.put(currentClass, 0);
		}

	}

	public Besace() {
		super();
		besace = new HashMap<Class<? extends PickAble>, Integer>();
		this.init();
	}

	public Map<Class<? extends PickAble>, Integer> get() {
		return besace;
	}

	public void set(Map<Class<? extends PickAble>, Integer> besace) {
		this.besace = besace;
	}

	public void add(Class<? extends PickAble> myClass) {

		besace.put(myClass, besace.get(myClass) + 1);

	}

	public void remove(Class<? extends PickAble> myClass) {

		besace.put(myClass, Math.max(0, besace.get(myClass) - 1));

	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	// Test
	// public void print() {

	// for (Iterator<Class<? extends PickAble>> mapIter =
	// besace.keySet().iterator(); mapIter.hasNext();) {
	// System.out.print(mapIter.toString() + " number : " +
	// besace.get(mapIter.next()));
	// }
	// }

}
