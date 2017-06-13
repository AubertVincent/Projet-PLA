package personnages;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import operateur.Action;
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
import sequence.EmptyRootTree;
import sequence.IncompleteTree;
import sequence.Tree;
import sequence._IncompleteSequence;
import sequence._Sequence;

public class Besace {

	private Map<Class<? extends PickAble>, Integer> besace;

	public boolean containsKey(Class<? extends PickAble> c) {
		return besace.containsKey(c);
	}

	@Override
	public String toString() {
		String str = new String();
		for (Iterator<Class<? extends PickAble>> itr = besace.keySet().iterator(); itr.hasNext();) {
			Class<? extends PickAble> currentClass = itr.next();
			Integer currentInteger = besace.get(currentClass);
			str += "<" + currentClass.getSimpleName() + "," + currentInteger.toString() + ">,";
		}
		return str;
	}

	public Integer get(Class<? extends PickAble> c) {
		return besace.get(c);
	}

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

	public void init() {

		for (Iterator<Class<? extends PickAble>> mapIter = possiblePickAbleList.iterator(); mapIter.hasNext();) {
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

	public Besace(Besace givenBesace) {
		besace = new HashMap<Class<? extends PickAble>, Integer>();
		for (Iterator<Class<? extends PickAble>> itr = givenBesace.get().keySet().iterator(); itr.hasNext();) {
			Class<? extends PickAble> currentClass = itr.next();
			Integer currentInteger = givenBesace.get(currentClass);
			besace.put(currentClass, currentInteger);
		}

	}

	/*
	 * Return the entire besace
	 */
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

	public _IncompleteSequence correctSequence(_Sequence sequence) {

		Map<Class<? extends PickAble>, Integer> besaceCopy = new HashMap<Class<? extends PickAble>, Integer>(besace);
		_IncompleteSequence resultingSequence = null;
		// Base case
		if (sequence instanceof Action) {
			Action action = (Action) sequence;
			Class<? extends PickAble> actionCorrespondingPickable = action.getPickable();
			if (besaceCopy.containsKey(actionCorrespondingPickable)
					&& besaceCopy.get(actionCorrespondingPickable).intValue() > 0) {
				try {
					return (_IncompleteSequence) action.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			} else {
				return new EmptyLeaf();
			}
		}
		// Recursive case
		else if (sequence instanceof Tree) {
			Tree tree = (Tree) sequence;
			// If there is the root of the _Sequence with an integer > 0 (ie the
			// root is in the besace)
			if (besaceCopy.containsKey(tree.getOp()) && besaceCopy.get(tree.getOp()).intValue() > 0) {
				// Return op, recursion(left), recursion(right)
				return new IncompleteTree(tree.getOp(), this.correctSequence(tree.getLeft()),
						this.correctSequence(tree.getRight()));
			} else {
				// Wasn't able to find op in besace -> mismatch found -> Empty
				// root, make a recursive call nonetheless
				return new EmptyRootTree(this.correctSequence(tree.getLeft()), this.correctSequence(tree.getRight()));
			}
		} else {
			try {
				throw new Exception("Unimplemented _Sequence subclass " + sequence.getClass().getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultingSequence;
	}

	public void clear() {
		this.clear();
	}

}
