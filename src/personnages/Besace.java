package personnages;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pickable.PickAble;
import pickable.PickClassicAck;
import pickable.PickMoveDir;
import pickable.PickPickUp;
import pickable.PickPriority;
import pickable.PickRandomBar;
import pickable.PickRandomMove;
import pickable.PickRecall;
import pickable.PickSuccession;
import pickable.PickSuicideBomber;
import pickable.PickTunnel;

public class Besace {

	private Map<Class<? extends PickAble>, Integer> besace;

	protected static List<Class<? extends PickAble>> possiblePickAbleList = new LinkedList<Class<? extends PickAble>>();
	static {

		possiblePickAbleList.add(PickClassicAck.class);
		possiblePickAbleList.add(PickSuicideBomber.class);
		possiblePickAbleList.add(PickTunnel.class);
		possiblePickAbleList.add(PickMoveDir.class);
		possiblePickAbleList.add(PickRecall.class);
		possiblePickAbleList.add(PickPickUp.class);
		possiblePickAbleList.add(PickSuccession.class);
		possiblePickAbleList.add(PickRandomBar.class);
		possiblePickAbleList.add(PickPriority.class);
		possiblePickAbleList.add(PickRandomMove.class);
	}

	public void initBesace() {
		for (Iterator<Class<? extends PickAble>> mapIter = possiblePickAbleList.iterator(); mapIter.hasNext();) {
			Class<? extends PickAble> currentClass;
			currentClass = mapIter.next();
			besace.put(currentClass, 0);
		}

	}

	public Besace() {
		super();
		besace = new HashMap<Class<? extends PickAble>, Integer>();
		this.initBesace();
	}

	public Map<Class<? extends PickAble>, Integer> get() {
		return besace;
	}

	public void set(Map<Class<? extends PickAble>, Integer> besace) {
		this.besace = besace;
	}

	// public void add(Class<? extends PickAble> myClass) {
	// int i;
	// for (Iterator<Class<? extends PickAble>> mapIter =
	// besace.keySet().iterator(); mapIter.hasNext();) {
	// Class<? extends PickAble> currentClass;
	// currentClass = mapIter.next();
	// if (myClass == currentClass) {
	// i = besace.get(currentClass);
	// besace.remove(currentClass);
	// besace.put(currentClass, i++);
	// }
	// }
	// }

	public void add(Class<? extends PickAble> myClass) {

		besace.put(myClass, besace.get(myClass) + 1);

	}

	public void remove(Class<? extends PickAble> myClass) {
		int i;
		for (Iterator<Class<? extends PickAble>> mapIter = besace.keySet().iterator(); mapIter.hasNext();) {
			Class<? extends PickAble> currentClass;
			currentClass = mapIter.next();
			if (myClass == currentClass) {
				i = besace.get(currentClass);
				besace.remove(currentClass);
				besace.put(currentClass, i--);
			}
		}
	}

	// Test
	public void print() {

		for (Iterator<Class<? extends PickAble>> mapIter = besace.keySet().iterator(); mapIter.hasNext();) {
			System.out.print(mapIter.getClass().toString() + " number : " + besace.get(mapIter.next()) + "\n");
		}
	}

}
